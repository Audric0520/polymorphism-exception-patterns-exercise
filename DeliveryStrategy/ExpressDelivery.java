package DeliveryStrategy;

import ParcelTypes.Parcel;

public class ExpressDelivery implements DeliveryService {
    @Override
    public String getName() {
        return "Express";
    }

    @Override
    public double computeFee(double baseFee) {
        return baseFee * 1.5;
    }

    @Override
    public boolean isAllowed(Parcel parcel) {
        return true;
    }

    @Override
    public String getRejectionMessage() {
        return "";
    }
}
