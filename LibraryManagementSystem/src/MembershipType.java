public enum MembershipType {
    STUDENT(0.25f),
    STAFF(0.1f),
    PUBLIC(0);
    private double wavier;

    MembershipType(float wavier) {
        this.wavier = wavier;
    }

    public double getWavier() {
        return wavier;
    }
}
