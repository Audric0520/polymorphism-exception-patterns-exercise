package Utilities;

import java.util.ArrayList;

import Exception.InvalidParcelException;
import ParcelTypes.Parcel;

public class ParcelManagementSystem {
    private static final ParcelManagementSystem INSTANCE = new ParcelManagementSystem();
    private final ArrayList<Parcel> parcels = new ArrayList<>();

    private ParcelManagementSystem() {

    }

    public static ParcelManagementSystem getInstance() {
        return INSTANCE;
    }

    public void addParcel(Parcel parcel) throws InvalidParcelException {
        if (parcel == null) {
            throw new InvalidParcelException("Parcel cannot be null.");
        }
        if (findDuplicateParcel(parcel.getTrackingNumber())) {
            throw new InvalidParcelException("Parcel with this tracking number already exists.");
        }
        parcels.add(parcel);
    }

    public boolean findDuplicateParcel(String trackingNumber) {
        return findSpecificParcel(trackingNumber) != null;
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
