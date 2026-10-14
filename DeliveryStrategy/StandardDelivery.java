package DeliveryStrategy;

import ParcelTypes.Parcel;

public class StandardDelivery implements DeliveryService {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public double computeFee(double baseFee) {
        return baseFee;
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
