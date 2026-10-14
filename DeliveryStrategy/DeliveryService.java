package DeliveryStrategy;

import ParcelTypes.Parcel;

public interface DeliveryService {
    String getName();

    double computeFee(double baseFee);

    boolean isAllowed(Parcel parcel);

    String getRejectionMessage();
}