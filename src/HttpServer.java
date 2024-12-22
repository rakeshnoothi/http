import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import Exception.PageNotFoundException;
import util.HttpHeader;
import util.HttpMethod;
import util.Logger;

public class HttpServer {
    private final int portNumber;
    private final Route route;
    private static final AtomicInteger activeConnections = new AtomicInteger(0);
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public HttpServer(int portNumber, Route route){
        this.portNumber = portNumber;
        this.route = route;
    }

    public void listen(){
        try (
            ServerSocket serverSocket = new ServerSocket(this.portNumber);
        ){
            Logger.log("Server listenting on port: " + portNumber);
            
            while(true){
                // accept the connection from client made to the listening port number.
                // halts the program until new connection is bound to the socket.
                Socket clientSocket = serverSocket.accept();
                // clientSocket.setSoTimeout(10000);

                activeConnections.incrementAndGet();
                Logger.log("Client connected to the server");
                Logger.log("Active connections: " + this.getActiveConnections());

                threadPool.execute(() -> handleClient(clientSocket));
            }
        }catch(IOException ioException){
            Logger.log("Error creating socket");
        }
    }

    private void handleClient(Socket clientSocket){
        try (
            PrintWriter socketOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String startLine;
            while((startLine = socketInputStream.readLine()) != null && startLine.length() >= 0){
                Request request = new Request();
                
                String responseMessage = null; 

                Logger.log("StartLine: " + startLine);
                String startLinePair[] = startLine.split(" ", 3);
                if(startLinePair.length <= 0){
                    responseMessage = DefaultResponse.getBadRequestResponse();
                    flushResponse(socketOutputStream, responseMessage);
                    clearSocketInputStreamBuffer(socketInputStream);
                    Logger.log("Not valid startline semantics");
                    continue;
                }
                request.startLineParts = startLinePair;

                // read the headers
                Map<String, String> requestHeaders = parseRequestHeaders(socketInputStream);
                if(requestHeaders == null){
                    responseMessage = DefaultResponse.getBadRequestResponse();
                    flushResponse(socketOutputStream, responseMessage);
                    clearSocketInputStreamBuffer(socketInputStream);
                    continue;
                }
                request.headers = requestHeaders;

                // print request headers.
                requestHeaders.forEach((key, value) -> {
                    System.out.println(key + ": " + value);
                });

                // if close headers from client close the connection.
                if("close".equalsIgnoreCase(requestHeaders.getOrDefault(HttpHeader.CONNECTION, "keep-alive"))){
                    Logger.log("Close request received from browser");
                    break;
                };

                // check if the request has headers related to body.
                if (requestHeaders.containsKey(HttpHeader.CONTENT_TYPE) || requestHeaders.containsKey(HttpHeader.CONTENT_LENGTH)) {
                    // check if the request method is GET if it is then do not read the body.
                    if(HttpMethod.GET.equals(request.getRequestMethod())){
                        responseMessage = DefaultResponse.getBadRequestResponse();
                        Logger.log("Responded with Bad request");
                        this.flushResponse(socketOutputStream, responseMessage);
                        
                        // clear the input buffer if there is any data present.
                        this.clearSocketInputStreamBuffer(socketInputStream);
                        continue;
                    }else{
                        Logger.log("Request headers present to read");
                        request.body = HttpBodyParser.parseRequestBody(
                            socketInputStream, 
                            requestHeaders.get(HttpHeader.CONTENT_TYPE), 
                            requestHeaders.get(HttpHeader.CONTENT_LENGTH)
                        );
                    }
                }

                // initialize the response object with defaults.
                Response response = new Response();

                try {
                    // return the function provided for the specific route and method.
                    RouteFunction routeFunction = this.route.getRouteFunction(request.getRequestMethod(), request.getRequestUrl());

                    // Provide the user with request and response objects.
                    responseMessage = routeFunction.work(request, response);
                    Logger.log("Response sent successfully");

                }catch(PageNotFoundException pageNotFoundException){
                    responseMessage = DefaultResponse.getPageNotFoundResponse();
                    Logger.log("Responded with page not found");
                }catch (Exception e) {
                    responseMessage = DefaultResponse.getInternalServerErrorResponse();
                    Logger.log("Responded with internal server error");
                }
                // flush the response to the client.
                this.flushResponse(socketOutputStream, responseMessage);

                
            }
        }catch(SocketTimeoutException socketTimoutException){
            Logger.log("Socket timeout");
            // respond with timeout exception.
        }catch (Exception e) {
            Logger.log("Error handling client: " + e.getMessage());
        }finally {
            try {
                clientSocket.close(); 
                activeConnections.decrementAndGet(); 
                Logger.log("Connection closed");
                Logger.log("Active connections: " + this.getActiveConnections());
            } catch (IOException e) {
                Logger.log("Error closing client socket: " + e.getMessage());
            }
        }
    }
    
    private int getActiveConnections(){
        return activeConnections.get();
    }

    private void flushResponse(PrintWriter socketOutputStream, String responseMessage){
        socketOutputStream.print(responseMessage);
        socketOutputStream.flush();    
    }

    private void clearSocketInputStreamBuffer(BufferedReader socketInputStream){
        try {
            while(socketInputStream.ready()){
                socketInputStream.read();
            }
        } catch (IOException e) {
            Logger.log("Error clearing input buffer");
        }
    }

    private Map<String, String> parseRequestHeaders(BufferedReader socketInputStream){
        try {
            Map<String, String> requestHeaders = new HashMap<>();
            String header = null;

            while(!(header = socketInputStream.readLine()).isEmpty() && header != null){
                String[] pair = header.split(": ", 2);
                if(pair.length <= 0){
                    Logger.log("Invalid header semantics");
                    return null;
                }
                requestHeaders.put(pair[0], pair[1]);
            }
            return requestHeaders;
        } catch (IOException e) {
            Logger.log("Error with socketInputStream");
            return null;
        }
    }
}
