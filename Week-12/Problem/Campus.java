class CampusPin {
    public static String banner = "Campus";
    public String name = "General Pin";

    public String getDetails() {
        return "Details: " + name;
    }
}

class HostelPin extends CampusPin {
    public static String banner = "Hostel";
    public String name = "Hostel Pin";

    @Override
    public String getDetails() {
        return "Details: " + name;
    }
}

public class CampusMapDemo {
    public static void main(String[] args) {
        CampusPin p = new HostelPin();

        System.out.println("banner: " + p.banner + " (decided by reference type)");
        System.out.println("name: " + p.name + " (decided by reference type)");
        System.out.println(p.getDetails() + " (decided by object type)");
    }
}