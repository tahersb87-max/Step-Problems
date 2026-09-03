class BookIssue {
    String title;
    String borrowerName;
    int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {
        if (daysOverdue > 0)
            return daysOverdue * 5;
        else
            return 0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    static double totalFineCollected(BookIssue[] issues) {
        double total = 0;

        for (BookIssue book : issues)
            total = total + book.fineAmount();

        return total;
    }
}

public class stepweek31 {

    public static void main(String[] args) {
        BookIssue[] issues = {
                new BookIssue("Clean Code", "A", 18),
                new BookIssue("Effective Java", "B", 5),
                new BookIssue("Refactoring", "C", 0),
                new BookIssue("DSA Handbook", "D", 21),
                new BookIssue("Design Patterns", "E", 9)
        };

        for (BookIssue book : issues) {
            if (book.isSeverelyOverdue())
                System.out.println(book.title + " - " +
                        book.daysOverdue + " days - Severely overdue");
            else
                System.out.println(book.title + " - " +
                        book.daysOverdue + " days - OK");
        }

        System.out.println("Total fine collected: Rs " +
                BookIssue.totalFineCollected(issues));
    }
}