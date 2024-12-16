import java.util.Map;

public class Request {
    private String[] startLineParts; 
    private Map<String, String> headers;

    public String[] getStartLineParts() {
        return startLineParts;
    }

    public void setStartLineParts(String[] startLineParts) {
        this.startLineParts = startLineParts;
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRequestMethod(){
        return this.startLineParts[0];
    }

    public String getRequestUrl(){
        return this.startLineParts[1];
    }

    public String getRequestHttpVersion(){
        return this.startLineParts[2];
    }
}
