package util;
public abstract class HttpHeader {

    
    private HttpHeader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Standard Request Headers
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONNECTION = "Connection";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String COOKIE = "Cookie";
    public static final String HOST = "Host";
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String ORIGIN = "Origin";
    public static final String REFERER = "Referer";
    public static final String USER_AGENT = "User-Agent";

    // Standard Response Headers
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String AGE = "Age";
    public static final String ALLOW = "Allow";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String CONTENT_LOCATION = "Content-Location";
    public static final String CONTENT_RANGE = "Content-Range";
    public static final String DATE = "Date";
    public static final String ETAG = "ETag";
    public static final String EXPIRES = "Expires";
    public static final String LAST_MODIFIED = "Last-Modified";
    public static final String LINK = "Link";
    public static final String LOCATION = "Location";
    public static final String RETRY_AFTER = "Retry-After";
    public static final String SERVER = "Server";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String VARY = "Vary";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    // Custom Headers (Common in APIs)
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String X_XSS_PROTECTION = "X-XSS-Protection";
    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    public static final String X_POWERED_BY = "X-Powered-By";
    public static final String X_RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";
    public static final String X_RATE_LIMIT_RESET = "X-RateLimit-Reset";
}
