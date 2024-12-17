import util.HttpContentType;
import util.HttpHeader;
import util.HttpMethod;

public class Main {
    public static void main(String[] args) {
        Route route = new Route();

        // Define routes
        route.defineRoute(HttpMethod.GET, "/", (request, response) -> {
            response.setHeader(HttpHeader.CONTENT_TYPE, HttpContentType.TEXT_HTML_UTF8);

            String responseMessage = response.getResponse(200, "successfull", "<h1>Hello</h1>");
            return responseMessage;
        });

        route.defineRoute(HttpMethod.GET, "/bro", (request, response) -> {
            String responseMessage = response.getResponse(200, "successfull", "<h1>zz</h1>");
            return responseMessage;
        });

        HttpServer socketServer = new HttpServer(5173, route);
        socketServer.listen();
    }
}
