import java.sql.Struct;

public class Magazine extends LibraryItem implements Renewable {

    private final String issueNumber;
    private static final int LOAN_PERIOD_DAYS = 7;
    private static final int DAILY_RATE = 3;
    private  static final int MAXIMUM_FINE = 30;
    private  static final int RENEWAL_LIMIT = 1;


    Magazine(String id, String title, String issueNumber) {
        super(id, title);
        this.issueNumber = issueNumber;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    public int getRenewalLimit() {
        return RENEWAL_LIMIT;
    }

    public String getCategoryName() {
        return "Magazine";
    }

    public void renewLoan() {
        if (getRenewableCount() >= RENEWAL_LIMIT)
            System.out.println("FAILED! - This magazine reached maximum number of renewals!");
        if(getStatus() != ItemStatus.ON_LOAN)
            System.out.println("FAILED! - This magazine is not Lended!");
       recordRenewal();
    }

    @Override
    public double calculateLateFine(int daysOverdue) {
        if (daysOverdue <= 0) {
            return 0.0;
        }
        double fine = daysOverdue * DAILY_RATE;
        return Math.min(fine, MAXIMUM_FINE);
    }

}
