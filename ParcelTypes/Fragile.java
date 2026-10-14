package ParcelTypes;

import DeliveryStrategy.DeliveryService;

public class Fragile extends Box {
    public static final double MAX_WEIGHT = 30.0;
    public static final int HANDLING_FEE = 100;

    private String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight, int length, int width, int height,
            String handlingNote,
            DeliveryService deliveryService) {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT, length, width, height, deliveryService);
        this.handlingNote = handlingNote.trim();
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    @Override
    public double getBaseFee() {
        return super.getBaseFee() + HANDLING_FEE;
    }

    @Override
    public String getType() {
        return "Fragile";
    }
}
