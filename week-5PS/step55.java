final class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    static {
        System.out.println("Discharge system initialized");
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (medicationCodes == null)
            throw new IllegalArgumentException();

        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]"))
                throw new IllegalArgumentException("Invalid medication code");
        }

        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(
            int index, String newCode) {

        if (!newCode.matches("MED-[A-Z]"))
            throw new IllegalArgumentException("Invalid medication code");

        String[] copy = medicationCodes.clone();
        copy[index] = newCode;

        return new DischargeSummary(patientId, copy);
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}

public class step55 {

    static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int skipped = 0;
        int critical = 0;
        int routine = 0;

        for (DischargeSummary s : summaries) {

            if (s == null) {
                skipped++;
                continue;
            }

            processed++;

            if (s instanceof CriticalCareDischargeSummary)
                critical++;
            else
                routine++;
        }

        return processed + " processed | " +
                skipped + " null skipped | " +
                critical + " critical-care | " +
                routine + " routine";
    }

    public static void main(String[] args) {

        DischargeSummary d = new DischargeSummary(
                "MT001",
                new String[] { "MED-A", "MED-B" });

        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";

        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummary[] batch = {
                new step55.CriticalCareDischargeSummary(
                        "MT001",
                        new String[] { "MED-X" },
                        4),
                null,
                new step55.DischargeSummary(
                        "MT002",
                        new String[] { "MED-Y" })
        };

        System.out.println(processNightlyBatch(batch));
    }
}