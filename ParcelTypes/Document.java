package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Exception.InvalidDataInputException;

public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    public static final int BASE_FEE = 50;

    private int numOfPages;

    public Document(String trackingNumber, String receiverName, double weight, int numOfPages,
            DeliveryService deliveryService) throws InvalidDataInputException {
        super(trackingNumber, receiverName, weight, deliveryService);
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

    public int getNumOfPages() {
        return numOfPages;
    }
}
