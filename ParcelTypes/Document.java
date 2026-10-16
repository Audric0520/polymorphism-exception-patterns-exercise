package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Exception.InvalidDataInputException;
import Exception.InvalidParcelException;

public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    public static final int BASE_FEE = 50;

    private int numOfPages;

    public Document(String trackingNumber, String receiverName, double weight, int numOfPages,
            DeliveryService deliveryService) throws InvalidDataInputException, InvalidParcelException {
        super(trackingNumber, receiverName, weight, deliveryService);
        if (weight > MAX_WEIGHT) {
            throw new InvalidDataInputException("Weight cannot exceed " + MAX_WEIGHT + " kg.");
        }
        setNumOfPages(numOfPages);
    }

    public void setNumOfPages(int numOfPages) throws InvalidDataInputException {
        if (!(numOfPages >= 1 && numOfPages <= 100)) {
            throw new InvalidDataInputException("The number of pages must be between 1-100");
        }
        this.numOfPages = numOfPages;
    }

    @Override
    public String getType() {
        return "Document";
    }

    @Override
    public double getChargeableWeight() {
        return getWeight();
    }

    @Override
    public double getBaseFee() {
        return BASE_FEE;
    }

    @Override
    public String getAdditionalInformation() {
        return String.format("Number of Pages: %d\n", getNumOfPages());
    }

    public int getNumOfPages() {
        return numOfPages;
    }
}
