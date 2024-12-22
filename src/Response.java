import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import util.HttpHeader;

public class Response {
    private Map<String, String> httpResponseHeaders = new HashMap<>();

    public Response(){
        // initialize with default headers.
        this.httpResponseHeaders.put(HttpHeader.CONNECTION, "keep-alive");
        this.httpResponseHeaders.put(HttpHeader.SERVER, "Test/1.0 (windows)");
        this.httpResponseHeaders.put(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        this.httpResponseHeaders.put(HttpHeader.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
    }

    public void setHeader(String header, String value){
        this.httpResponseHeaders.put(header, value);
    }

    public String getResponse(int statusCode, String statusMessage, String body){
        StringBuilder responseBuilder = new StringBuilder();
        if(body != null){
            // get the length of body.
            String contentLength =  String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);
            this.setHeader(HttpHeader.CONTENT_LENGTH, contentLength);
        }else{
            this.setHeader(HttpHeader.CONTENT_LENGTH, "0");
        }

        // build the http headers builder string.
        StringBuilder builtHttpHeaders = buildHttpHeaders();

        // return the resposne
        responseBuilder.append("HTTP/1.1")
                            .append(" ")
                            .append(statusCode)
                            .append(" ")
                            .append(statusMessage)
                            .append("\r\n")
                            .append(builtHttpHeaders)
                            .append("\r\n");
                            

        if(body != null){
            responseBuilder.append(body).toString(); 
        }

        return responseBuilder.toString();
    }

    private StringBuilder buildHttpHeaders(){
        StringBuilder httpHeadersBuilder = new StringBuilder();
        this.httpResponseHeaders.forEach((key, value) -> {
            httpHeadersBuilder.append(key)
                                    .append(": ")
                                    .append(value)
                                    .append("\r\n");
        });
        return httpHeadersBuilder;
    }
}
