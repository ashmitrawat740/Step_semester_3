class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;


    // No-argument constructor
    public LibraryMember() {
        this(null, null);
    }


    // Name-only constructor
    public LibraryMember(String name) {
        this(null, name);
    }


    // ID + name constructor
    public LibraryMember(String membershipId,
                         String name) {

        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }


    // Getter
    public String getMembershipId() {
        return membershipId;
    }


    // Write-once setter
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    // Boolean JavaBean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }


    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }


    // Write-only property
    public void setSecurityAnswer(String answer) {

        if (answer != null) {

            // Deterministic one-way transformation
            this.securityAnswer =
                Integer.toString(answer.hashCode());
        }
    }
}


public class Main {

    public static void main(String[] args) {

        LibraryMember m1 =
            new LibraryMember("Priya Nair");

        System.out.println(
            m1.getMembershipId()
        );


        LibraryMember m2 =
            new LibraryMember(
                "LIB-8841",
                "Priya Nair"
            );

        System.out.println(
            m2.getMembershipId()
        );


        LibraryMember m3 =
            new LibraryMember();

        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000");

        System.out.println(
            m3.getMembershipId()
        );


        m3.setPremiumMember(true);

        System.out.println(
            m3.isPremiumMember()
        );


        m3.setSecurityAnswer("blue");
    }
}