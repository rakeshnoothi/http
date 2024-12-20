import java.util.Map;

public class HttpRequestBody{
    Map<String, String> params;

    public String getParam(String key){
        if(params == null) return null;
        return this.params.getOrDefault(key, null);
    }
}
