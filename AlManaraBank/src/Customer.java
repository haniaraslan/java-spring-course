import java.util.ArrayList;

public class Customer {
    private final String fullname;
    private final String nationalId;
    private final String id;
    private String phoneNumber;
    private CustomerTier customerTier;
    private static int idCounter = 1000;
    private ArrayList<BankAccount> accounts = new ArrayList<>();

    public Customer(String fullname, String nationalId, String phoneNumber, CustomerTier customerTier) {
        this.fullname = fullname;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.customerTier = customerTier;
        this.id = generateId();
    }

//    public static String generateId() {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 12; i++) {
//            sb.append((int)(Math.random() * 10));
//        }
//        return sb.toString();
//    }

    private static synchronized String generateId() {
        return String.valueOf(++idCounter);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerTier getCustomerTier() {
        return customerTier;
    }

    public void setCustomerTier(CustomerTier customerTier) {
        this.customerTier = customerTier;
    }

    public String getFullname() {
        return fullname;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getId() {
        return id;
    }

    public void printDetails() {
        System.out.println("Customer ID: " + id +
                "\nName: " + fullname +
                "\nNational ID: " + nationalId +
                "\nPhone: " + (phoneNumber == null ? "None" : phoneNumber) +
                "\nTier: " + customerTier +
                "\nAccounts Count: " + accounts.size());
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}
