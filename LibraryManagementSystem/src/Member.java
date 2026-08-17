public class Member {
    private static final int MAX_ITEMS_HELD = 3;
    private static final double MAX_BALANCE_TO_BORROW = 100.00;
    private String name;
    private final String id;
    private final MembershipType type;
    private double owedBalance;
    private int heldItemsCount;

    public Member(String name, String id, MembershipType type, float owedBalance) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.heldItemsCount = 0;
        this.owedBalance = owedBalance;
    }

    public Member(String name, String id, MembershipType type) {
        this(name, id, type, 0);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public MembershipType getType() {
        return type;
    }

    public double getOwedBalance() {
        return owedBalance;
    }

    public int getHeldItemsCount() {
        return heldItemsCount;
    }

    public void chargeFine(double fine) {
        if (fine < 0) throw new IllegalArgumentException("Fine Cannot be less than 0!");
        this.owedBalance += fine;
    }

    public void payFine(double fine) {
        if (fine < 0) throw new IllegalArgumentException("FAILED! - Fine Cannot be less than 0!");
        if (fine > owedBalance)
            throw new IllegalArgumentException("FAILED! - Fine paid is greater than the owed balance!");
        this.owedBalance -= fine;
    }

    public void additem(int itemsCount) {
        this.heldItemsCount += itemsCount;
    }

    public boolean canBorrow() {
        return (this.heldItemsCount > MAX_ITEMS_HELD || this.owedBalance > MAX_BALANCE_TO_BORROW);
    }

    public void recordBorrow() {
        this.heldItemsCount++;
    }

    public void recordReturn() {
        if (this.heldItemsCount == 0)
            throw new IllegalArgumentException("FAILED! - No currently held items t bve returned!");
        this.heldItemsCount--;
    }

}
