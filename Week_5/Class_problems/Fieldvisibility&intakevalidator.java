class AccessRuleEngine {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS"))
                return "ALLOWED";
            else
                return "DENIED";
        }

        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (int i = 0; i < attempts.length; i++) {

            String result = classifyAccess(
                attempts[i][0],
                attempts[i][1]
            );

            if (result.equals("ALLOWED"))
                allowed++;
            else
                denied++;
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}


class PatientRecord {

    private String patientId;
    String wardCode;           
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId,
                         String wardCode,
                         double vitalsScore,
                         String facilityName) {

        String id = patientId.trim();

        if (id.length() == 0 || id.length() < 4) {
            throw new IllegalArgumentException(
                "Invalid patient ID"
            );
        }

        this.patientId = id;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}


public class Main {

    public static void main(String[] args) {

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "private", "SAME_CLASS"
            )
        );

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "default", "DIFFERENT_PACKAGE"
            )
        );

        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
            AccessRuleEngine.summarizeBatch(attempts)
        );

        PatientRecord p =
            new PatientRecord(
                "MT94", "W3", 98.2, "MediTrack Central"
            );
    }
}