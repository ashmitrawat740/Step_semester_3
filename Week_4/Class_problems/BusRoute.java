public class BusRoute implements Comparable<BusRoute> {
    private final String routeCode;
    private final String routeName;
    private final int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 1);
    }

    public String getRouteCode() {
        return routeCode;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        int codeComparison = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeComparison != 0) {
            return codeComparison;
        }
        return Integer.compare(this.routeName.length(), other.routeName.length());
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return new BusRoute[0];
        BusRoute[] sorted = routes.clone();

        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(routes);
        for (BusRoute r : ranked) {
            System.out.print("\"" + r.getRouteCode() + "\" ");
        }
        System.out.println();
    }
}