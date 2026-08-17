
import java.util.List;

public class Library {
    private LibraryItem[] catalogue;
    private int catalogueSize;
    private Member[] members;
    private int memberCount;

    private Helper.ReturnBreakdown lastReturnBreakdown;

    public int getCatalogueSize() {
        return catalogueSize;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public Library(int catalogueSize, int memberCount) {
        this.catalogueSize = 0;
        this.memberCount = 0;
        catalogue = new LibraryItem[catalogueSize];
        members = new Member[memberCount];
    }

    public LibraryItem findItemById(String id) {
        if (catalogue.length == 0) {
            System.out.println("Catalogue is empty!");
            return null;
        }
        if (id == null) {
            System.out.println("item not registered!");
            return null;
        }
        for (int i = 0; i < catalogueSize; i++) {
            if (catalogue[i].getItemId().equals(id)) {
                return catalogue[i];
            }
        }
        return null;
    }

    public Member findMemberByName(String name) {
        if (members.length == 0) {
            System.out.println("members list is empty!");
            return null;
        }
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getName().equals(name)) {
                return members[i];
            }
        }
        return null;
    }

    public void registerItem(LibraryItem newItem) {
        if (catalogueSize >= catalogue.length) {
            System.out.println("cannot add new item, Catalogue size full!");
            return;
        }
        if (findItemById(newItem.getItemId()) != null) {
            System.out.println("Item already registered!");
            return;
        }
        catalogue[catalogueSize++] = newItem;
    }

    public void registerMember(Member newMember) {
        if (memberCount >= members.length) {
            System.out.println("cannot add new member, Members maximum capacity reached!");
        }
        if (findMemberByName(newMember.getName()) != null) {
            System.out.println("Member is already registered!");
        }
        members[memberCount++] = newMember;
    }

    public void printAllItems() {
        if (catalogue.length == 0) {
            System.out.println("No Items are registered");
            return;
        }
        for (int i = 0; i < catalogueSize; i++) {
            catalogue[i].printDetails();
        }
    }

    public void printItemsByState(ItemStatus status) {
        int count = 0;
        if (catalogue.length == 0) {
            System.out.println("No Items are registered");
            return;
        }
        for (int i = 0; i < catalogueSize; i++) {
            if (catalogue[i].getStatus() == status) {
                catalogue[i].printDetails();
                count++;
            }
        }
        if (count == 0) System.out.println("No items with this state");
    }

    public void getAllOwedItemsByMemberId(String memberName) {
        int count = 0;
        if (findMemberByName(memberName) == null) {
            System.out.println("No Member with this name is registered!");
            return;
        }
        for (int i = 0; i < catalogueSize; i++) {
            if (catalogue[i].getBorrowerName().equals(memberName)) {
                catalogue[i].printDetails();
                count++;
            }
        }
        if (count == 0) System.out.println("No items owed by this member");
    }

    public void printAllMembers() {
        if (members.length == 0) {
            System.out.println("No members are registered");
            return;
        }
        for (int i = 0; i < memberCount; i++) {
            System.out.println("Member name: " + members[i].getName() + " Id : " + members[i].getId() + " owes: \n");
            getAllOwedItemsByMemberId(members[i].getId());
        }
    }

    public int countItemsByState(ItemStatus status) {
        int count = 0;
        for (int i = 0; i < catalogueSize; i++) {
            if (catalogue[i].getStatus() == status)
                count++;
        }
        return count;
    }

    public double calculateAllMembersOutstandingBalance() {
        double totalBalance = 0.0;
        for (int i = 0; i < memberCount; i++) {
            totalBalance += members[i].getOwedBalance();
        }
        return totalBalance;
    }

    public double calculateLoanRate() {
        if (catalogueSize == 0) {
            return 0.0;
        }
        return (countItemsByState(ItemStatus.ON_LOAN) * 100.0) / catalogueSize;
    }

    public double calculateProjectedFines(int overdueDays) {
        double totalProjected = 0.0;
        for (int i = 0; i < catalogueSize; i++) {
            if (catalogue[i].getStatus() == ItemStatus.ON_LOAN) {
                totalProjected += catalogue[i].calculateLateFine(overdueDays);
            }
        }
        return totalProjected;
    }

    public Helper.LendResult lendItem(String itemId, String memberName) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            return Helper.LendResult.ITEM_NOT_FOUND;
        }

        Member member = findMemberByName(memberName);
        if (member == null) {
            return Helper.LendResult.MEMBER_NOT_FOUND;
        }
        if (item.getStatus() != ItemStatus.AVAILABLE) {
            return Helper.LendResult.ITEM_NOT_AVAILABLE;
        }
        if (member.canBorrow()) {
            return Helper.LendResult.MEMBER_NOT_ELIGIBLE;
        }
        member.recordBorrow();
        item.lendItem(member.getName());
        return Helper.LendResult.SUCCESS;
    }

    public void printRecipt(LibraryItem item, Member member, double fine) {
        System.out.println("Item name: " + item.getTitle() + " type: " +
                item.getCategoryName() + " Borrower Name: " + member.getName() + " fine: " + fine);
    }

    public Helper.ReturnResult returnItem(String itemId, int overdueDays) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            lastReturnBreakdown = null;
            return Helper.ReturnResult.ITEM_NOT_FOUND;
        }
        if (item.getStatus() != ItemStatus.ON_LOAN) {
            lastReturnBreakdown = null;
            return Helper.ReturnResult.NOT_ON_LOAN;
        }
        if (overdueDays < 0) {
            lastReturnBreakdown = null;
            return Helper.ReturnResult.NEGATIVE_DAYS;
        }
        Member member = findMemberByName(item.getBorrowerName());

        if (member == null) {
            lastReturnBreakdown = null;
            return Helper.ReturnResult.BORROWER_NOT_FOUND;
        }

        double baseFine = 0.0;
        double waiverAmount = 0.0;
        double adminCharge = 0.0;

        if (overdueDays > 0) {
            double rawFine = item.calculateLateFine(overdueDays);
            waiverAmount = rawFine * member.getType().getWavier();
            baseFine = rawFine - waiverAmount;
            adminCharge = LibraryItem.getAdministrativeCharge();
        }
        double total = baseFine + adminCharge;
        member.chargeFine(total);
        member.recordReturn();
        printRecipt(item, member, total);
        item.resetLoan();
        lastReturnBreakdown = new Helper.ReturnBreakdown(baseFine, waiverAmount, adminCharge, total, member.getOwedBalance());
        return Helper.ReturnResult.SUCCESS;
    }

    public Helper.ReturnBreakdown getLastReturnBreakdown() {
        return lastReturnBreakdown;
    }

    private int lastRenewalsRemaining;

    public Helper.RenewResult renewItem(String itemId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            return Helper.RenewResult.ITEM_NOT_FOUND;
        }
        if (!(item instanceof Renewable)) {
            return Helper.RenewResult.NOT_RENEWABLE_TYPE;
        }
        Renewable renewable = (Renewable) item;
        renewable.renewLoan();
        lastRenewalsRemaining = renewable.getRenewalLimit() - item.getRenewableCount();
        return Helper.RenewResult.SUCCESS;
    }

    public int getLastRenewalsRemaining() {
        return lastRenewalsRemaining;
    }

    public void payFine(String memberName, double amount) {
        Member member = findMemberByName(memberName);
        if (member == null) {
            return;
        }
        member.payFine(amount);
    }
}
