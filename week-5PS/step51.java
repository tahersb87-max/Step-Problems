class PatientRecord {
    private String patientId;
    String wardCode;
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode,
            double vitalsScore, String facilityName) {
        patientId = patientId.trim();

        if (patientId.length() < 4)
            throw new IllegalArgumentException("Invalid patient ID");

        this.patientId = patientId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}

public class step51 {

    static String classifyAccess(String modifier, String context) {
        if (modifier.equals("private"))
            return context.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";

        if (modifier.equals("default"))
            return context.equals("SAME_CLASS") ||
                    context.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

        if (modifier.equals("protected"))
            return context.equals("SAME_CLASS") ||
                    context.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

        if (modifier.equals("public"))
            return "ALLOWED";

        return "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0, denied = 0;

        for (String[] a : attempts) {
            if (classifyAccess(a[0], a[1]).equals("ALLOWED"))
                allowed++;
            else
                denied++;
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] a = {
                { "protected", "SAME_PACKAGE" },
                { "protected", "DIFFERENT_PACKAGE" },
                { "public", "DIFFERENT_PACKAGE" }
        };

        System.out.println(summarizeBatch(a));

        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}