import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import util.HttpHeader;
import util.Logger;

public class HttpServer {
    private final int portNumber;
    private final Route route;
    private static final AtomicInteger activeConnections = new AtomicInteger(0);

    public HttpServer(int portNumber, Route route){
        this.portNumber = portNumber;
        this.route = route;
    }

    public void listen(){
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            ) {
                Logger.log("HttpServer", "Server listenting on port: " + portNumber);

                while(true){
                    // accept the connection from client made to the listening port number.
                    // halts the program until new connection is bound to the socket.
                    Socket clientSocket = serverSocket.accept();

                    // TODO: may be use thread pool here.
                    new Thread(() -> handleClient(clientSocket)).start();
                }
        } catch (IOException e) {
            Logger.log("HttpServer", "Error creating socket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket){
        try (
            PrintWriter socketOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            
            activeConnections.incrementAndGet();
            Logger.log("HttpServer", "Client connected to the server");
            Logger.log("HttpServer", "Active connections: " + getActiveConnections());

            Request request = new Request();
            Response response;
            
            // Change this to a better approach
            String startLine;
            while((startLine = socketInputStream.readLine()) != null){
                request.setStartLineParts(startLine.split(" ", 3));  
                
                // read the headers
                Map<String, String> requestHeaders = new HashMap<>();
                String header;
                while(!"".equals(header = socketInputStream.readLine())){
                    String[] pair = header.split(": ", 2);
                    requestHeaders.put(pair[0], pair[1]);
                }
                request.setHeaders(requestHeaders);

                // TODO: Extract the body here.

                // initialize the response object with defaults.
                response = new Response(request.getRequestHttpVersion());

                String responseMessage = null; 
                try {
                    // return the function provided for the specific route and method.
                    RouteFunction routeFunction = route.getRouteFunction(request.getRequestMethod(), request.getRequestUrl());

                    // Provide the user with request and response objects.
                    responseMessage = routeFunction.work(request, response);
                    Logger.log("HttpServer", "Response sent successfully");
                } catch (Exception e) {
                    responseMessage = response.getDefaultResponse();
                    Logger.log("HttpServer", "Responded with default response");
                }
                // flush the response to the client.
                socketOutputStream.print(responseMessage);
                socketOutputStream.flush();

                if("close".equalsIgnoreCase(requestHeaders.getOrDefault(HttpHeader.CONNECTION, "keep-alive")))break;
            }
        } catch (Exception e) {
            Logger.log("HttpServer", "Error handling client: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                clientSocket.close(); 
                activeConnections.decrementAndGet(); 
                Logger.log("HttpServer", "Connection closed. Active connections: " + getActiveConnections());
            } catch (IOException e) {
                Logger.log("HttpServer", "Error closing client socket: " + e.getMessage());
            }
        }
    }

    private int getActiveConnections(){
        return activeConnections.get();
    }
}
