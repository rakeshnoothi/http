import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private StringBuilder httpHeadersBuilder = new StringBuilder(); 
    private StringBuilder responseBuilder = new StringBuilder();
    private String httpResponseVersion;
    private Map<String, String> httpResponseHeaders = new HashMap<>();

    public Response(String httpResponseVersion){
        this.httpResponseVersion = httpResponseVersion;
    }

    public void setHeader(String header, String value){
        httpResponseHeaders.put(header, value);
    }

    public String getResponse(int statusCode, String statusMessage, String body){
        // set any default values here before invoking buildHttpHeaders method.
        String contentLength;
        if(body != null){
            contentLength =  String.valueOf( body.getBytes(StandardCharsets.UTF_8).length);
            this.setHeader(HttpHeader.CONTENT_LENGTH, contentLength);
        }

        // build the http headers builder string.
        buildHttpHeaders();

        // build the resposne startline.
        String responseStartLine = httpResponseVersion + " " + statusCode + " " + statusMessage;
        // return the resposne
        return responseBuilder.append(responseStartLine)
                            .append("\r\n")
                            .append(this.httpHeadersBuilder)
                            .append("\r\n")
                            .append(body).toString(); 
    }

    public String getDefaultResponse(){
        this.setHeader(HttpHeader.SERVER, "test");
        this.setHeader(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        return this.getResponse(404, "Not Found", "<div>Page Not found</div>");
    }

    private void buildHttpHeaders(){
        this.httpResponseHeaders.forEach((key, value) -> {
            this.httpHeadersBuilder.append(key)
                        .append(": ")
                        .append(value)
                        .append("\r\n");
        });
    }
}
