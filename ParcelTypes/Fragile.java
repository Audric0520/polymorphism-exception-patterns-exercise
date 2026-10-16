package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Exception.InvalidDataInputException;
import Exception.InvalidParcelException;

public class Fragile extends Box {
    public static final double MAX_WEIGHT = 30.0;
    public static final int HANDLING_FEE = 100;

    private String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight, int length, int width, int height,
            String handlingNote,
            DeliveryService deliveryService) throws InvalidDataInputException, InvalidParcelException {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT, length, width, height, deliveryService);
        setHandlingNote(handlingNote);
    }

    public void setHandlingNote(String handlingNote) throws InvalidDataInputException {
        if (handlingNote == null || handlingNote.isBlank()) {
            throw new InvalidDataInputException("Handling Note cannot be empty.");
        }
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

    @Override
    public String getAdditionalInformation() {
        return String.format("Length: %d\nWidth: %d\nHeight: %d\nHandling Note: %s\n",
                getLength(), getWidth(), getHeight(), getHandlingNote());
    }
}
