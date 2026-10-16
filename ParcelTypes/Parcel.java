package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Exception.InvalidDataInputException;
import Exception.InvalidParcelException;
import Utilities.Status;

public abstract class Parcel {
    private String trackingNumber;
    private String receiverName;
    private double weight;
    private Status status;
    private DeliveryService deliveryService;

    public Parcel(String trackingNumber, String receiverName, double weight, DeliveryService deliveryService)
            throws InvalidDataInputException, InvalidParcelException {
        if (trackingNumber == null || trackingNumber.isBlank()) {
            throw new InvalidDataInputException("Tracking number cannot be empty.");
        }
        if (receiverName == null || receiverName.isBlank()) {
            throw new InvalidDataInputException("Receiver name cannot be empty.");
        }
        if (!Double.isFinite(weight) || weight < 0.1) {
            throw new InvalidDataInputException("Weight must be at least 0.1 kg.");
        }
        if (deliveryService == null) {
            throw new InvalidParcelException("A delivery service is required.");
        }

        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName;
        this.weight = weight;
        this.status = Status.PENDING;
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

    public void setDeliveryService(DeliveryService deliveryService) throws InvalidParcelException {
        if (deliveryService == null) {
            throw new InvalidParcelException("A delivery service is required.");
        }
        if (!deliveryService.isAllowed(this)) {
            throw new InvalidParcelException(deliveryService.getRejectionMessage());
        }
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
