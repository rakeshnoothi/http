import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private int portNumber;
    private Request request = new Request();
    private Response response = new Response();
    private Route route;

    public HttpServer(int portNumber, Route route){
        this.portNumber = portNumber;
        this.route = route;
    }

    public void initiate(){
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            // accept the connection from client made to the listening port number.
            Socket clientSocket = serverSocket.accept();
            PrintWriter socketOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            Logger.log("SocketServer", "Client connected to the server");

            // TODO: Make the server to listen for requests continuously.
            // Change this to a better approach
            String startLine;
            while((startLine = socketInputStream.readLine()) != null){
                // read the start line.
                this.request.setStartLineParts(startLine.split(" ", 3));  
                
                // read the headers
                Map<String, String> headers = new HashMap<>();
                String header;
                while(!"".equals(header = socketInputStream.readLine())){
                    String[] pair = header.split(": ", 2);
                    headers.put(pair[0], pair[1]);
                }
                this.request.setHeaders(headers);

                // TODO: Extract the body here.
                

                // return the function provided for the specific route and method.
                RouteFunction routeFunction = route.getRouteFunction(this.request.getRequestMethod(), this.request.getRequestUrl());
                
                // Provide the user with request and response objects.
                String responseMessage = routeFunction.work(this.request, this.response);

                // flush the response to the client.
                socketOutputStream.println(responseMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
