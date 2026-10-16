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
        if (trackingNumber == null || !trackingNumber.matches("^ABC\\d{4}$")) {
            throw new InvalidDataInputException(
                    "Invalid Tracking Number. Tracking number must be 'ABC' followed by 4 digits.");
        }
        if (receiverName == null || !receiverName.trim().matches("^\\p{L}[\\p{L} .'-]*$")) {
            throw new InvalidDataInputException(
                    "Invalid Receiver Name. It must start with a letter and use only letters, spaces, hyphens, apostrophes or periods.");
        }
        if (!Double.isFinite(weight)) {
            throw new InvalidDataInputException("Invalid Weight.");
        }
        if (deliveryService == null) {
            throw new InvalidParcelException("A delivery service is required.");
        }

        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName.trim().replaceAll("\\s+", " ");
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

    public abstract String getAdditionalInformation();

    public abstract String getType();

    public abstract double getChargeableWeight();

    public abstract double getBaseFee();
}
