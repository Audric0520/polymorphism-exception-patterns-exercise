import java.util.ArrayList;

public class ParcelManagementSystem {
    private static final ParcelManagementSystem INSTANCE = new ParcelManagementSystem();
    private final ArrayList<Parcel> parcels = new ArrayList<>();

    private ParcelManagementSystem() {

    }

    public static ParcelManagementSystem getInstance() {
        return INSTANCE;
    }

    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public boolean findDuplicateParcel(String trackingNumber) {
        for (Parcel parcel : parcels) {
            if (parcel.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                return true;
            }
        }
        return false;
    }

    public Parcel findSpecificParcel(String trackingNumber) {
        for (Parcel parcel : parcels) {
            if (parcel.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                return parcel;
            }
        }
        return null;
    }

    public ArrayList<Parcel> getAllParcels() {
        return new ArrayList<>(parcels);
    }
}
