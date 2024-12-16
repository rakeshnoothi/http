public class Response {
    private StringBuilder httpHeaders = new StringBuilder(); 

    public void setHeader(String header, String value){
        this.httpHeaders.append(header + ": "  + value + "\r\n");
    }

    public String getResponse(String httpVersion, int statusCode, String statusMessage, String body){
        String responseStartLine = httpVersion + " " + statusCode + " " + statusMessage;
        String httpHeader = this.httpHeaders.toString();

        return responseStartLine + "\r\n" + httpHeader + "\r\n" + body;
    }
}
