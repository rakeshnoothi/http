public class Response {
    private StringBuilder httpHeadersBuilder = new StringBuilder(); 
    private StringBuilder responseBuilder = new StringBuilder();
    private String httpResponseVersion;

    public Response(String httpResponseVersion){
        this.httpResponseVersion = httpResponseVersion;
    }

    public void setHeader(String header, String value){
        this.httpHeadersBuilder.append(header)
                        .append(": ")
                        .append(value)
                        .append("\r\n");
        
    }

    public String getResponse(int statusCode, String statusMessage, String body){
        String responseStartLine = httpResponseVersion + " " + statusCode + " " + statusMessage;
        // String httpHeader = this.httpHeadersBuilder.toString();

        return responseBuilder.append(responseStartLine)
                            .append("\r\n")
                            .append(this.httpHeadersBuilder)
                            .append("\r\n")
                            .append(body).toString(); 
    }

    public String getDefaultResponse(){
        this.setHeader(HttpHeader.SERVER, "test");
        this.setHeader(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        return this.getResponse(404, "Not Found", null);
    }
}
