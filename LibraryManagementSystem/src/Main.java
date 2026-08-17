import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Library library = fillLibrary();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    library.printAllItems();
                    break;
                case "2":
                    registerNewMember(library);
                    break;
                case "3":
                    borrowItem(library);
                    break;
                case "4":
                    returnItem(library);
                    break;
                case "5":
                    renewLoan(library);
                    break;
                case "6":
                    searchItem(library);
                    break;
                case "7":
                    viewItemsByStatus(library);
                    break;
                case "8":
                    payFine(library);
                    break;
                case "9":
                    library.printAllMembers();
                    break;
                case "10":
                    libraryReport(library);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unrecognised option. Please choose from the menu.");
            }
        }
        System.out.println("Goodbye.");
    }

    private static Library fillLibrary() {
        Library library = new Library(100, 100);

        library.registerItem(new Book("B001", "The Cairo", "Naguib Mahfouz", 1300));
        library.registerItem(new Book("B002", "Season of Migration to the North", "Tayeb Salih", 169));
        library.registerItem(new Magazine("M001", "Al-Ahram Weekly", "1245"));
        library.registerItem(new Magazine("M002", "National Geographic Egypt", "0087"));
        library.registerItem(new DVD("D001", "The Yacoubian Building", 172.5f));
        library.registerItem(new DVD("D002", "Cairo Station", 77.34f));

        library.registerMember(new Member("hania", "MEM001", MembershipType.STUDENT));
        library.registerMember(new Member("Youssef", "MEM002", MembershipType.STAFF));
        library.registerMember(new Member("Laila", "MEM003", MembershipType.PUBLIC));

        library.registerMember(new Member("Omar", "MEM004", MembershipType.PUBLIC, 45.00f));
        return library;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== " + LibraryItem.getLibraryName() + " Library Management System =====");
        System.out.println(" 1. View catalogue");
        System.out.println(" 2. Register member");
        System.out.println(" 3. Borrow item");
        System.out.println(" 4. Return item");
        System.out.println(" 5. Renew loan");
        System.out.println(" 6. Search item by ID");
        System.out.println(" 7. View items by status");
        System.out.println(" 8. Pay outstanding fines");
        System.out.println(" 9. View all members");
        System.out.println("10. Library report");
        System.out.println(" 0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void registerNewMember(Library library) {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Membership ID: ");
        String id = scanner.nextLine().trim();
        MembershipType type = readMembershipType();
        if (type == null) {
            return;
        }
        try {
            library.registerMember(new Member(name, id, type));
            System.out.println("Member registered: " + name + " (" + id + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static MembershipType readMembershipType() {
        System.out.print("Category (STUDENT / STAFF / PUBLIC): ");
        String raw = scanner.nextLine().trim().toUpperCase(Locale.US);
        try {
            return MembershipType.valueOf(raw);
        } catch (IllegalArgumentException e) {
            System.out.println("Unrecognised.");
            return null;
        }
    }

    private static void borrowItem(Library library) {
        System.out.print("Item ID: ");
        String itemId = scanner.nextLine().trim();
        System.out.print("Membership Name: ");
        String memberName = scanner.nextLine().trim();

        Helper.LendResult result = library.lendItem(itemId, memberName);
        switch (result) {
            case SUCCESS:
                LibraryItem item = library.findItemById(itemId);
                System.out.println("Loan confirmed. Loan period: " + item.getLoanPeriodDays() + " days.");
                break;
            case ITEM_NOT_FOUND:
                System.out.println("No item found with that ID.");
                break;
            case MEMBER_NOT_FOUND:
                System.out.println("No member found with that ID.");
                break;
            case ITEM_NOT_AVAILABLE:
                System.out.println("That item is not available to borrow.");
                break;
            case MEMBER_NOT_ELIGIBLE:
                System.out.println("Member is not eligible: holds three items already, or owes more than 100 EGP.");
                break;
        }
    }

    private static void returnItem(Library library) {
        System.out.print("Item ID: ");
        String itemId = scanner.nextLine().trim();
        System.out.print("Days overdue (0 if on time): ");
        int overdueDays;
        try {
            overdueDays = scanner.nextInt();

            if (overdueDays < 0) {
                System.out.println("Value cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a whole number.");
            return;
        }

        Helper.ReturnResult result = library.returnItem(itemId, overdueDays);
        switch (result) {
            case SUCCESS: {
                Helper.ReturnBreakdown b = library.getLastReturnBreakdown();
                System.out.println("Return processed.");
                System.out.printf("  Base fine (after waiver): %.2f EGP%n", b.baseFine);
                System.out.printf("  Waiver applied:           %.2f EGP%n", b.waiverAmount);
                System.out.printf("  Administrative charge:    %.2f EGP%n", b.administrativeCharge);
                System.out.printf("  Total charged:            %.2f EGP%n", b.totalCharged);
                System.out.printf("  New balance:               %.2f EGP%n", b.newBalance);
                break;
            }
            case ITEM_NOT_FOUND:
                System.out.println("No item found with that ID.");
                break;
            case NOT_ON_LOAN:
                System.out.println("That item is not currently on loan.");
                break;
            case NEGATIVE_DAYS:
                System.out.println("Days overdue cannot be negative.");
                break;
            case BORROWER_NOT_FOUND:
                System.out.println("Could not identify the borrowing member.");
                break;
        }
    }

    // ---------------------------------------------------------------
    // 5 — Renew loan
    // ---------------------------------------------------------------
    private static void renewLoan(Library library) {
        System.out.print("Item ID: ");
        String itemId = scanner.nextLine().trim();

        Helper.RenewResult result = library.renewItem(itemId);
        switch (result) {
            case SUCCESS:
                System.out.println("Renewed. Renewals remaining: " + library.getLastRenewalsRemaining());
                break;
            case ITEM_NOT_FOUND:
                System.out.println("No item found with that ID.");
                break;
            case NOT_RENEWABLE_TYPE:
                System.out.println("This item type cannot be renewed.");
                break;
            case RENEW_FAILED:
                System.out.println("Could not renew: the item is not on loan, or has reached its renewal limit.");
                break;
        }
    }
    private static void searchItem(Library library) {
        System.out.print("Item ID: ");
        String itemId = scanner.nextLine().trim();
        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            System.out.println("No item found with that ID.");
        } else {
            item.printDetails();
        }
    }

    private static void viewItemsByStatus(Library library) {
        System.out.print("Status (AVAILABLE / ON_LOAN / RESERVED / LOST): ");
        String raw = scanner.nextLine().trim().toUpperCase(Locale.US);
        ItemStatus status;
        try {
            status = ItemStatus.valueOf(raw);
        } catch (IllegalArgumentException e) {
            System.out.println("Unrecognised status.");
            return;
        }
        library.printItemsByState(status);
    }

    private static void payFine(Library library) {
        System.out.print("Member name: ");
        String memberName = scanner.nextLine().trim();
        Member member = library.findMemberByName(memberName);
        if (member == null) {
            System.out.println("No member found with that ID.");
            return;
        }
        System.out.print("Amount to pay (EGP): ");
        double amount = 0;
        try {
            amount = scanner.nextDouble();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a numeric amount.");
            return;
        }
        library.payFine(memberName, amount);
    }

    private static void libraryReport(Library library) {
        int projectionDays = 5;
        System.out.println("===== " + LibraryItem.getLibraryName() + " — Library Report =====");
        System.out.println("Catalogue size:            " + library.getCatalogueSize() + " / " + library.getCatalogueSize());
        System.out.println("Items Count:               " + LibraryItem.getItemsCount());
        System.out.println("Items on loan:             " + library.countItemsByState(ItemStatus.ON_LOAN));
        System.out.printf("Loan rate:                 %.1f%%%n", library.calculateLoanRate());
        System.out.printf("Total outstanding fines:   %.2f EGP%n", library.calculateAllMembersOutstandingBalance());
        System.out.printf("Projected fines (%d days late, before waivers): %.2f EGP%n",
                projectionDays, library.calculateProjectedFines(projectionDays));
    }

}