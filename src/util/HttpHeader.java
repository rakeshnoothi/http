package util;

public abstract class HttpHeader {

    private HttpHeader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Standard Request Headers
    public static final String ACCEPT = "accept";
    public static final String ACCEPT_CHARSET = "accept-charset";
    public static final String ACCEPT_ENCODING = "accept-encoding";
    public static final String ACCEPT_LANGUAGE = "accept-language";
    public static final String AUTHORIZATION = "authorization";
    public static final String CACHE_CONTROL = "cache-control";
    public static final String CONNECTION = "connection";
    public static final String CONTENT_LENGTH = "content-length";
    public static final String CONTENT_TYPE = "content-type";
    public static final String COOKIE = "cookie";
    public static final String HOST = "host";
    public static final String IF_MODIFIED_SINCE = "if-modified-since";
    public static final String ORIGIN = "origin";
    public static final String REFERER = "referer";
    public static final String USER_AGENT = "user-agent";

    // Standard Response Headers
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "access-control-allow-origin";
    public static final String AGE = "age";
    public static final String ALLOW = "allow";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String CONTENT_ENCODING = "content-encoding";
    public static final String CONTENT_LANGUAGE = "content-language";
    public static final String CONTENT_LOCATION = "content-location";
    public static final String CONTENT_RANGE = "content-range";
    public static final String DATE = "date";
    public static final String ETAG = "etag";
    public static final String EXPIRES = "expires";
    public static final String LAST_MODIFIED = "last-modified";
    public static final String LINK = "link";
    public static final String LOCATION = "location";
    public static final String RETRY_AFTER = "retry-after";
    public static final String SERVER = "server";
    public static final String SET_COOKIE = "set-cookie";
    public static final String TRANSFER_ENCODING = "transfer-encoding";
    public static final String VARY = "vary";
    public static final String WWW_AUTHENTICATE = "www-authenticate";

    // Custom Headers (Common in APIs)
    public static final String X_REQUEST_ID = "x-request-id";
    public static final String X_FORWARDED_FOR = "x-forwarded-for";
    public static final String X_FORWARDED_PROTO = "x-forwarded-proto";
    public static final String X_FRAME_OPTIONS = "x-frame-options";
    public static final String X_XSS_PROTECTION = "x-xss-protection";
    public static final String X_CONTENT_TYPE_OPTIONS = "x-content-type-options";
    public static final String X_POWERED_BY = "x-powered-by";
    public static final String X_RATE_LIMIT_REMAINING = "x-ratelimit-remaining";
    public static final String X_RATE_LIMIT_RESET = "x-ratelimit-reset";
}
