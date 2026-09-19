final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    static {
        System.out.println("Circulation ledger initialized");
    }

    public LoanReceipt(String memberId, String[] bookIds) {

        if (bookIds == null || bookIds.length > 20) {
            throw new IllegalArgumentException("Invalid book IDs");
        }

        String[] copy = new String[bookIds.length];

        for (int i = 0; i < bookIds.length; i++) {

            String id = bookIds[i];

            if (!isValidBookId(id)) {
                throw new IllegalArgumentException("Invalid book ID");
            }

            copy[i] = id;
        }

        this.memberId = memberId;
        this.bookIds = copy;
    }

    private static boolean isValidBookId(String id) {

        if (id == null || id.length() != 6) {
            return false;
        }

        if (!id.startsWith("BK-")) {
            return false;
        }

        for (int i = 3; i < 6; i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 ||
                index >= bookIds.length ||
                !isValidBookId(newId)) {

            throw new IllegalArgumentException("Invalid correction");
        }

        String[] corrected = bookIds.clone();
        corrected[index] = newId;

        return new LoanReceipt(memberId, corrected);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

public class step55 {

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (int i = 0; i < receipts.length; i++) {

            if (receipts[i] == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipts[i] instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " +
                nullSkipped + " null skipped | " +
                referenceOnly + " reference-only | " +
                regular + " regular";
    }

    public static void main(String[] args) {

        try {
            LoanReceipt r = new LoanReceipt(
                    "LIB-8841",
                    new String[] { "BK-100", "bad" });
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LoanReceipt r = new LoanReceipt(
                "LIB-8841",
                new String[] { "BK-100", "BK-101" });

        String[] ids = r.getBookIds();
        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        LoanReceipt[] receipts = {
                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[] { "BK-200" },
                        "Reading Room 3"),
                null,
                new LoanReceipt(
                        "LIB-002",
                        new String[] { "BK-201" })
        };

        System.out.println(
                processNightlyCirculation(receipts));
    }
}