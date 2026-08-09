public enum CustomerTier{
    STANDARD (0,0),
    SILVER (100,0.1),
    GOLD (100,0.1),
    UNKNOWN(-1,-1);

    private final double monthlyFee;
    private final double interest;

    CustomerTier(double monthlyFee, double interest) {
        this.monthlyFee = monthlyFee;
        this.interest = interest;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public double getInterest() {
        return interest;
    }
}