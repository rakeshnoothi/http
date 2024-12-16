import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private StringBuilder httpHeadersBuilder = new StringBuilder(); 
    private StringBuilder responseBuilder = new StringBuilder();
    private final String httpResponseVersion;
    private Map<String, String> httpResponseHeaders = new HashMap<>();
    private static final Map<String, String> defaultResponseHeaders = new HashMap<>();

    static{
        Response.defaultResponseHeaders.put(HttpHeader.CONNECTION, "keep-alive");
        Response.defaultResponseHeaders.put(HttpHeader.SERVER, "Test/1.0 (windows)");
        Response.defaultResponseHeaders.put(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        Response.defaultResponseHeaders.put(HttpHeader.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
    }

    public Response(String httpResponseVersion){
        this.httpResponseVersion = httpResponseVersion;
    }

    public void setHeader(String header, String value){
        httpResponseHeaders.put(header, value);
    }

    public String getResponse(int statusCode, String statusMessage, String body){
        if(body != null){
            String contentLength =  String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);
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
        return this.getResponse(404, "Not Found", "<div>Page Not found</div>");
    }

    private void buildHttpHeaders(){
        this.httpResponseHeaders.forEach((key, value) -> {
            Response.defaultResponseHeaders.forEach((defaultKey, defaultValue) -> {
                if(defaultKey.equals(key)){
                    this.httpHeadersBuilder.append(key)
                        .append(": ")
                        .append(value)
                        .append("\r\n");
                }else{
                    this.httpHeadersBuilder.append(defaultKey)
                        .append(": ")
                        .append(defaultValue)
                        .append("\r\n");
                }
            });
            
        });
    }
}
