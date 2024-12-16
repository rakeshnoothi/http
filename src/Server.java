public class Server {
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        Route route = new Route();

        // Define a routes
        route.defineRoute(HttpMethod.GET, "/", (request, response) -> {
            response.setHeader(HttpHeader.CONTENT_LENGTH, "6");
            response.setHeader(HttpHeader.SERVER, "test");
            response.setHeader(HttpHeader.CONNECTION, "keep-alive");
            response.setHeader(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");

            String responseMessage = response.getResponse(200, "successfull", "<h1> Hello </h1>");
            System.out.println("Response message: " + responseMessage);

            return responseMessage;
        });

        HttpServer socketServer = new HttpServer(portNumber, route);
        socketServer.listen();
    }
}
