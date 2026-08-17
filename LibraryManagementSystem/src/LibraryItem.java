public abstract class LibraryItem {
    private final String itemId;
    private final String title;
    private ItemStatus status;
    private String borrowerName;
    private int renewableCount;

    private static final String LIBRARY_NAME = "Bayt Al Hekma";
    private static final double ADMINISTRATIVE_CHARGE = 10.00;
    private static int itemsCount = 0;

    public LibraryItem(String id, String title) {
        this.itemId = id;
        this.title = title;
        this.status = ItemStatus.AVAILABLE;
        this.borrowerName = null;
        this.renewableCount = 0;
        itemsCount++;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getRenewableCount() {
        return renewableCount;
    }

    public static int getItemsCount() {
        return itemsCount;
    }

    public static String getLibraryName() {
        return LIBRARY_NAME;
    }

    public static double getAdministrativeCharge() {
        return ADMINISTRATIVE_CHARGE;
    }

    public void reportProperties() {
        //TODO print all info
    }

    public void setState(ItemStatus status) {
        this.status = status;
    }

    public void lendItem(String borrowerName) {
        if (this.status != ItemStatus.AVAILABLE) throw new IllegalStateException("FAILED! - This item is unavailable!");
        this.status = ItemStatus.ON_LOAN;
        this.borrowerName = borrowerName;
    }

    public final void resetLoan() {
        this.status = ItemStatus.AVAILABLE;
        this.borrowerName = null;
        this.renewableCount = 0;
    }

    public void recordRenewal() {
        renewableCount++;
    }

    public abstract double calculateLateFine(int overdueDays);

    public abstract int getLoanPeriodDays();

    public abstract String getCategoryName();

    public final void printDetails() {
        System.out.println(
                "Id: " + getItemId() +
                        " \tCategory: " + getCategoryName() +
                        " \ttitle: " + getTitle() +
                        " \tstatus: " + getStatus() +
                        " \tborrower name: " + (getBorrowerName() == null ? "---" : getBorrowerName()) +
                        " \tloan period: " + getLoanPeriodDays() +
                        " \toverdue fine: " + calculateLateFine(1)
        );
    }

}
