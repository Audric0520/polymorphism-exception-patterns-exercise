package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Utilities.DataValidation;

public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    public static final double RATE_PER_KILO = 40.0;
    private static final int VOLUMETRIC_DIVISOR = 3500;

    private int length;
    private int width;
    private int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height,
            DeliveryService deliveryService) {
        this(trackingNumber, receiverName, weight, MAX_WEIGHT, length, width, height, deliveryService);
    }

    protected Box(String trackingNumber, String receiverName, double weight, double maxWeight,
            int length, int width, int height, DeliveryService service) {
        super(trackingNumber, receiverName, weight, service);

        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double getVolumetricWeight() {
        return ((double) length * width * height) / VOLUMETRIC_DIVISOR;
    }

    @Override
    public String getType() {
        return "Box";
    }

    @Override
    public double getChargeableWeight() {
        return Math.ceil(Math.max(getWeight(), getVolumetricWeight()));
    }

    @Override
    public double getBaseFee() {
        return RATE_PER_KILO * getChargeableWeight();
    }
}
