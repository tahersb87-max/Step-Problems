class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPin;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (patientId == null)
            patientId = id;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\d{4,6}"))
            lockerPin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class step54 {
    public static void main(String[] args) {

        PatientProfile p = new PatientProfile("MT2026-0142", "Arjun Iyer");

        System.out.println(p.getPatientId());

        PatientProfile p2 = new PatientProfile();

        p2.setPatientId("MT2026-0142");
        p2.setPatientId("HACKED-0000");

        System.out.println(p2.getPatientId());
    }
}