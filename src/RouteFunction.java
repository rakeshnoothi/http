@FunctionalInterface
public interface RouteFunction {
    String work(Request request, Response response);
}
