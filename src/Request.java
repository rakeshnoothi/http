import java.util.Map;

public class Request {
    String[] startLineParts; 
    Map<String, String> headers;
    HttpRequestBody body = new HttpRequestBody();

    public String getRequestMethod(){
        return this.startLineParts[0];
    }

    public String getRequestUrl(){
        return this.startLineParts[1];
    }

    public String getRequestHttpVersion(){
        return this.startLineParts[2];
    }

    public String getRequestHeader(String header){
        return headers.get(header);
    }
}
