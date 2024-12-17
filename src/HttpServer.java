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

import util.HttpHeader;
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
            Logger.log("HttpServer", "Server listenting on port: " + portNumber);
            
            while(true){
                // accept the connection from client made to the listening port number.
                // halts the program until new connection is bound to the socket.
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(10000);

                activeConnections.incrementAndGet();
                Logger.log("HttpServer", "Client connected to the server");
                Logger.log("HttpServer", "Active connections: " + this.getActiveConnections());

                // TODO: may be use thread pool here.
                threadPool.execute(() -> handleClient(clientSocket));
            }
        }catch(IOException ioException){
            Logger.log("HttpServer", "Error creating socket");
        }
    }

    private void handleClient(Socket clientSocket){
        try (
            PrintWriter socketOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            Request request = new Request();
            Response response;
            
            String startLine;
            while((startLine = socketInputStream.readLine()) != null && startLine.length() >= 0){
                request.startLineParts = startLine.split(" ", 3);  
                
                // read the headers
                Map<String, String> requestHeaders = new HashMap<>();
                String header = null;
                while(!(header = socketInputStream.readLine()).isEmpty() && header != null){
                    String[] pair = header.split(": ", 2);
                    requestHeaders.put(pair[0], pair[1]);
                }
                request.headers = requestHeaders;

                if (requestHeaders.containsKey(HttpHeader.CONTENT_TYPE) || requestHeaders.containsKey(HttpHeader.CONTENT_LENGTH)) {
                    // HttpRequestBody<?> body = HttpBodyParser.parseRequestBody(
                    //     socketInputStream,
                    //     requestHeaders.get(HttpHeader.CONTENT_TYPE), 
                    //     requestHeaders.get(HttpHeader.CONTENT_LENGTH)
                    // );
                    // request.body = body;
                }

                // initialize the response object with defaults.
                response = new Response(request.getRequestHttpVersion());

                String responseMessage = null; 
                try {
                    // return the function provided for the specific route and method.
                    RouteFunction routeFunction = this.route.getRouteFunction(request.getRequestMethod(), request.getRequestUrl());

                    // Provide the user with request and response objects.
                    responseMessage = routeFunction.work(request, response);
                    Logger.log("HttpServer", "Response sent successfully" + this.getActiveConnections());
                } catch (Exception e) {
                    responseMessage = response.getDefaultResponse();
                    Logger.log("HttpServer", "Responded with default response");
                }
                // flush the response to the client.
                socketOutputStream.print(responseMessage);
                socketOutputStream.flush();

                if("close".equalsIgnoreCase(requestHeaders.getOrDefault(HttpHeader.CONNECTION, "keep-alive"))){
                    Logger.log("HttpServer", "Close request received from browser");
                    break;
                };
            }
        }catch(SocketTimeoutException socketTimoutException){
            Logger.log("HttpServer", "Socket timeout");
            // respond with timeout exception.
            
        }catch (Exception e) {
            Logger.log("HttpServer", "Error handling client: " + e.getMessage());
        }finally {
            try {
                clientSocket.close(); 
                activeConnections.decrementAndGet(); 
                Logger.log("HttpServer", "Connection closed");
                Logger.log("HttpServer", "Active connections: " + this.getActiveConnections());
            } catch (IOException e) {
                Logger.log("HttpServer", "Error closing client socket: " + e.getMessage());
            }
        }
    }
    
    private int getActiveConnections(){
        return activeConnections.get();
    }

    
}
