class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }
}

class LibraryMember {
    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "City Library";
    static int memberCount = 0;

    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;

        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class stepweek34 {
    public static void main(String[] args) {
        BrokenLibraryMember a = new BrokenLibraryMember("Aditi", "LM-1001", 2);

        BrokenLibraryMember b = new BrokenLibraryMember("Rohan", "LM-1002", 1);

        System.out.println("Broken version:");
        System.out.println(a.name);
        System.out.println(b.name);
        System.out.println("\nFixed version:");

        LibraryMember m1 = new LibraryMember("Aditi", 2);

        LibraryMember m2 = new LibraryMember("Rohan", 1);

        m1.printMemberCard();
        m2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}