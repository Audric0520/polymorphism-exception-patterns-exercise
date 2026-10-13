public class SameDayDelivery implements DeliveryService {
    public static final double MAX_CHARGEABLE_WEIGHT = 10.0;

    @Override
    public String getName() {
        return "Same Day";
    }

    @Override
    public double computeFee(double baseFee) {
        return (baseFee * 2) + 100;
    }

    @Override
    public boolean isAllowed(Parcel parcel) {
        return parcel.getChargeableWeight() <= MAX_CHARGEABLE_WEIGHT;
    }

    @Override
    public String getRejectionMessage() {
        return "Same Day delivery is only for parcels up to 10 kg!";
    }
}
