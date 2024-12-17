import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.HttpContentType;

public class HttpBodyParser {

    public final static HttpRequestBody<?> parseRequestBody(BufferedReader socketInputStream, String contentType, String contentLength){
        switch(contentType){
            // case HttpContentType.APPLICATION_JSON: {
            //     parseJson();
            //     break;
            // }
            case HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED:{
                return parseForm(socketInputStream);
            }
            default: {
                return parseText();
            }
        }
    }

    // private static final void parseJson(){
        
    // }

    private static final HttpRequestBody<Map<String, String>> parseForm(BufferedReader socketInputStream){
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        Map<String, String> params = new HashMap<>();

        String key = null;
        String value = null;
        try {
            char ch = ' ';
            //TODO: write this better to handle edge cases.
            while((ch = (char) socketInputStream.read()) != -1){
                keyBuilder.append(ch);
                if(ch == '='){
                    key = keyBuilder.toString();
                }else if(ch == '&'){
                    value = valueBuilder.toString();
                    params.put(key, value);
                }
            }
            HttpRequestBody<Map<String, String>> body = new HttpRequestBody<>(params);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final HttpRequestBody<String> parseText(){
        return new HttpRequestBody<String>("he");
    }
}
