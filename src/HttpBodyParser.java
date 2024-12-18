import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import util.HttpContentType;
import util.Logger;

public class HttpBodyParser {

    public final static HttpRequestBody parseRequestBody(BufferedReader socketInputStream, String contentType, String contentLength){
        switch(contentType){
            // case HttpContentType.APPLICATION_JSON: {
            //     parseJson();
            //     break;
            // }
            case HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED:{
                HttpRequestBody body  = new HttpRequestBody();
                body.params = parseFormData(socketInputStream, Integer.parseInt(contentLength));
                return body;
            }
            default: {
                // parseText();
                return new HttpRequestBody();
            }
        }
    }

    // private static final void parseJson(){
        
    // }

    private static final Map<String, String> parseFormData(BufferedReader socketInputStream, int contentLength){
        try {
            Map<String, String> params = new HashMap<>();

            // read the chars according to the content length provided.
            char[] readChars = new char[contentLength];
            socketInputStream.read(readChars);
            String encodedString = new String(readChars);
            
            String[] pairs = encodedString.split("&");

            if(pairs.length <= 0){
                pairs = new String[1];
                pairs[0] = encodedString;
            }

            for(String pair : pairs){
                String[] keyValue = pair.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(key, value);
            }

            return params;
        } catch (IOException e) {
            Logger.log("HttpBodyParser", "Error occured parsing the body");
            e.printStackTrace();
            return null;
        }
    }
}
