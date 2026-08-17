public class Helper {
    public static class ReturnBreakdown {
        public final double baseFine;
        public final double waiverAmount;
        public final double administrativeCharge;
        public final double totalCharged;
        public final double newBalance;

        ReturnBreakdown(double baseFine, double waiverAmount, double administrativeCharge,
                        double totalCharged, double newBalance) {
            this.baseFine = baseFine;
            this.waiverAmount = waiverAmount;
            this.administrativeCharge = administrativeCharge;
            this.totalCharged = totalCharged;
            this.newBalance = newBalance;
        }
    }

    public enum LendResult {SUCCESS, ITEM_NOT_FOUND, MEMBER_NOT_FOUND, ITEM_NOT_AVAILABLE, MEMBER_NOT_ELIGIBLE}
    public enum ReturnResult { SUCCESS, ITEM_NOT_FOUND, NOT_ON_LOAN, NEGATIVE_DAYS, BORROWER_NOT_FOUND }
    public enum RenewResult { SUCCESS, ITEM_NOT_FOUND, NOT_RENEWABLE_TYPE, RENEW_FAILED }

}
