import java.util.HashMap;
import java.util.Map;

public class Route {
    // The functions that were stored here are userDefined lambda functions.
    private final Map<String, RouteFunction> routes = new HashMap<>();

    // store the function for specific route and method in a map to retreive the function later.
    public void defineRoute(String method, String route, RouteFunction work){
        routes.put(method + " " + route,  work);
    }

    // retreive the function provided for specific route and method.
    public RouteFunction getRouteFunction(String method, String route){
        return routes.get(method + " " + route);
    }
}
