final class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    private static int processedCount;

    // Static block
    static {
        processedCount = 0;
    }


    public DischargeSummary(String patientId,
                            String[] medicationCodes) {

        if (patientId == null ||
            medicationCodes == null) {

            throw new IllegalArgumentException(
                "Invalid data"
            );
        }

        for (int i = 0; i < medicationCodes.length; i++) {

            String code = medicationCodes[i];

            if (code == null ||
                !code.matches("MED-[A-Z]")) {

                throw new IllegalArgumentException(
                    "Invalid medication code"
                );
            }
        }

        this.patientId = patientId;

        // Defensive copy
        this.medicationCodes =
            medicationCodes.clone();
    }


    public String[] getMedicationCodes() {

        // Defensive copy
        return medicationCodes.clone();
    }


    public DischargeSummary withCorrectedMedication(
            int index,
            String newCode) {

        if (index < 0 ||
            index >= medicationCodes.length) {

            throw new IndexOutOfBoundsException();
        }

        if (newCode == null ||
            !newCode.matches("MED-[A-Z]")) {

            throw new IllegalArgumentException(
                "Invalid medication code"
            );
        }

        String[] copy =
            medicationCodes.clone();

        copy[index] = newCode;

        return new DischargeSummary(
            patientId,
            copy
        );
    }


    public static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int critical = 0;
        int routine = 0;

        for (int i = 0; i < summaries.length; i++) {

            if (summaries[i] == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summaries[i]
                    instanceof CriticalCareDischargeSummary) {

                critical++;

            } else {

                routine++;
            }
        }

        processedCount += processed;

        return processed + " processed | "
             + nullSkipped + " null skipped | "
             + critical + " critical-care | "
             + routine + " routine";
    }
}


class CriticalCareDischargeSummary
        extends DischargeSummary {

    private final int icuDays;


    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        if (icuDays < 0) {
            throw new IllegalArgumentException(
                "Invalid ICU days"
            );
        }

        this.icuDays = icuDays;
    }


    public int getIcuDays() {
        return icuDays;
    }
}


public class Main {

    public static void main(String[] args) {

        String[] codes = {
            "MED-A",
            "MED-B"
        };

        DischargeSummary d =
            new DischargeSummary(
                "MT2026-0142",
                codes
            );


        // Test defensive copy
        String[] copy =
            d.getMedicationCodes();

        copy[0] = "TAMPERED";

        System.out.println(
            d.getMedicationCodes()[0]
        );


        // Test with-style method
        DischargeSummary corrected =
            d.withCorrectedMedication(
                0,
                "MED-C"
            );

        System.out.println(
            corrected.getMedicationCodes()[0]
        );


        CriticalCareDischargeSummary icu =
            new CriticalCareDischargeSummary(
                "MT001",
                new String[]{"MED-X"},
                4
            );


        DischargeSummary routine =
            new DischargeSummary(
                "MT002",
                new String[]{"MED-Y"}
            );


        DischargeSummary[] batch = {
            icu,
            null,
            routine
        };


        System.out.println(
            DischargeSummary.processNightlyBatch(batch)
        );
    }
}