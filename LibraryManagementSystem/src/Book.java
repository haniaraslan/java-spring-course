public class Book extends LibraryItem implements Renewable {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double DAILY_RATE = 5.00;
    private static final int RENEWAL_LIMIT = 2;
    private String author;
    private int pageCount;

    Book(String id, String title, String author, int pageCount) {
        super(id, title);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public double calculateLateFine(int daysOverdue) {
        if (daysOverdue <= 0) {
            return 0.0;
        }
        return daysOverdue * DAILY_RATE;
    }

    @Override
    public int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getCategoryName() {
        return "Book";
    }

    public int getRenewalLimit() {
        return RENEWAL_LIMIT;
    }

    public void renewLoan() {
        if (getRenewableCount() >= RENEWAL_LIMIT)
            System.out.println("FAILED! - This Book reached maximum number of renewals!");
        if (getStatus() != ItemStatus.ON_LOAN)
            System.out.println("FAILED! - This Book is not Lended!");
        recordRenewal();
    }


}
