interface Exportable {

    String exportData();

    // Shared mutable counter holder
    class Counter {
        static int totalExports = 0;
    }

    static int getTotalExports() {
        return Counter.totalExports;
    }

    static void exportAll(Exportable[] items) {

        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }
}


class ReportGenerator implements Exportable {

    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {

        Exportable.Counter.totalExports++;

        return "Exported report: " + reportName;
    }
}


class UserProfile implements Exportable {

    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {

        Exportable.Counter.totalExports++;

        return "Exported profile: " + username;
    }
}


public class Problem2 {

    public static void main(String[] args) {

        ReportGenerator r =
                new ReportGenerator("Sales Q1");

        UserProfile u =
                new UserProfile("jane_doe");

        // Individual exports
        System.out.println(r.exportData());
        System.out.println(u.exportData());

        // Mixed array
        Exportable[] items = {r, u};

        Exportable.exportAll(items);

        System.out.println(
                "Total exports: "
                + Exportable.getTotalExports()
        );
    }
}