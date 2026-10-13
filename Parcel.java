public abstract class Parcel {
    private String trackingNumber;
    private String receiverName;
    private double weight;
    private Status status;
    private DeliveryService deliveryService;

    public Parcel(String trackingNumber, String receiverName, double weight, DeliveryService deliveryService) {
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
        this.status = status.PENDING;
        this.deliveryService = deliveryService;

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

    public double getDeliveryFee() {
        return deliveryService.computeFee(getBaseFee());
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public boolean isDelivered() {
        return status == Status.DELIVERED;
    }

    public void markAsDelivered() {
        this.status = Status.DELIVERED;
    }

    public abstract String getType();

    public abstract double getChargeableWeight();

    public abstract double getBaseFee();
}
