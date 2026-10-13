public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    public static final int BASE_FEE = 50;

    private int numOfPages;

    public Document(String trackingNumber, String receiverName, int weight, int numOfPages) {
        super(trackingNumber, receiverName, weight);
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
