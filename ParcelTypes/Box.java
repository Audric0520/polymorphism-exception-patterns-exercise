package ParcelTypes;

import DeliveryStrategy.DeliveryService;
import Exception.InvalidDataInputException;
import Exception.InvalidParcelException;

public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    public static final double RATE_PER_KILO = 40.0;
    private static final int VOLUMETRIC_DIVISOR = 3500;

    private int length;
    private int width;
    private int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height,
            DeliveryService deliveryService) throws InvalidDataInputException, InvalidParcelException {
        this(trackingNumber, receiverName, weight, MAX_WEIGHT, length, width, height, deliveryService);
    }

    protected Box(String trackingNumber, String receiverName, double weight, double maxWeight,
            int length, int width, int height, DeliveryService service)
            throws InvalidDataInputException, InvalidParcelException {
        super(trackingNumber, receiverName, weight, service);

        if (weight > maxWeight) {
            throw new InvalidDataInputException("Weight cannot exceed " + maxWeight + " kg.");
        }
        setLength(length);
        setWidth(width);
        setHeight(height);
    }

    public void setLength(int length) throws InvalidDataInputException {
        if (!(length > 0)) {
            throw new InvalidDataInputException("Length must be greater than 0.");
        }
        this.length = length;
    }

    public void setWidth(int width) throws InvalidDataInputException {
        if (!(width > 0)) {
            throw new InvalidDataInputException("Width must be greater than 0.");
        }
        this.width = width;
    }

    public void setHeight(int height) throws InvalidDataInputException {
        if (!(height > 0)) {
            throw new InvalidDataInputException("Height must be greater than 0.");
        }
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
