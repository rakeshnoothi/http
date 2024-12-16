public abstract class HttpMethod {

    // Prevent instantiation
    private HttpMethod() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // HTTP Methods
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";
    public static final String HEAD = "HEAD";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";
    public static final String CONNECT = "CONNECT";
}
