package util;

public final class HttpContentType {

    // Prevent instantiation
    private HttpContentType() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Common content types
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_CSS = "text/css";
    public static final String TEXT_JAVASCRIPT = "text/javascript";

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_GIF = "image/gif";
    public static final String IMAGE_SVG_XML = "image/svg+xml";

    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_ZIP = "application/zip";

    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_OGG = "audio/ogg";

    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_WEBM = "video/webm";

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String APPLICATION_JAVASCRIPT = "application/javascript";

    // Charset variants
    public static final String TEXT_PLAIN_UTF8 = "text/plain; charset=UTF-8";
    public static final String TEXT_HTML_UTF8 = "text/html; charset=UTF-8";
    public static final String APPLICATION_JSON_UTF8 = "application/json; charset=UTF-8";

    // Add more content types as needed...
}
