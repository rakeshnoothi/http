public class DefaultResponse {
    private static final Response response = new Response();

    public static String getPageNotFoundResponse(){
        return response.getResponse(404, "Not Found", null);
    }

    public static String getBadRequestResponse(){
        return response.getResponse(400, "Bad Request", null);
    }

    public static String getInternalServerErrorResponse(){
        return response.getResponse(500, "Internal Server Error", null);
    }
}
