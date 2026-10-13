public abstract class Parcel {
    private String trackingNumber;
    private String receiverName;
    private int weight;
    private Status status;

    public Parcel(String trackingNumber, String receiverName, int weight) {
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
        this.status = status.PENDING;

    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getWeight() {
        return weight;
    }

    public Status getStatus() {
        return status;
    }

    public abstract String getType();

    public abstract double getChargeableWeight();

    public abstract double getBaseFee();
}
