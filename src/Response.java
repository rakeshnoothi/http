import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import util.HttpHeader;

public class Response {
    private StringBuilder httpHeadersBuilder = new StringBuilder(); 
    private StringBuilder responseBuilder = new StringBuilder();
    private final String httpResponseVersion;
    private Map<String, String> httpResponseHeaders = new HashMap<>();

    public Response(String httpResponseVersion){
        this.httpResponseVersion = httpResponseVersion;

        // initialize with default headers.
        this.httpResponseHeaders.put(HttpHeader.CONNECTION, "keep-alive");
        this.httpResponseHeaders.put(HttpHeader.SERVER, "Test/1.0 (windows)");
        this.httpResponseHeaders.put(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        this.httpResponseHeaders.put(HttpHeader.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
    }

    public void setHeader(String header, String value){
        httpResponseHeaders.put(header, value);
    }

    public String getResponse(int statusCode, String statusMessage, String body){
        if(body != null){
            // get the length of body.
            String contentLength =  String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);
            this.setHeader(HttpHeader.CONTENT_LENGTH, contentLength);
        }

        // build the http headers builder string.
        buildHttpHeaders();

        // return the resposne
        return responseBuilder.append(httpResponseVersion)
                            .append(" ")
                            .append(statusCode)
                            .append(" ")
                            .append(statusMessage)
                            .append("\r\n")
                            .append(this.httpHeadersBuilder)
                            .append("\r\n")
                            .append(body).toString(); 
    }

    public String getDefaultResponse(){
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
