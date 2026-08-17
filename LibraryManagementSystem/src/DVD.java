public class DVD extends LibraryItem {

    private static final int LOAN_PERIOD_DAYS = 3;
    private static final double DAILY_RATE = 15.00;
    private final float runtime;

    DVD(String id, String title, float runtime) {
        super(id, title);
        this.runtime = runtime;
    }

    public float getRuntime() {
        return runtime;
    }

    @Override
    public int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getCategoryName() {
        return "DVD";
    }

    public double calculateLateFine(int daysOverdue) {
        if (daysOverdue <= 0) {
            return 0.0;
        }
        return daysOverdue * DAILY_RATE;
    }

}
