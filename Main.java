import java.util.ArrayList;

import DeliveryStrategy.DeliveryService;
import DeliveryStrategy.StandardDelivery;
import Exception.InvalidDataInputException;
import Exception.InvalidParcelException;
import ParcelTypes.Box;
import ParcelTypes.Document;
import ParcelTypes.Fragile;
import ParcelTypes.Parcel;
import Utilities.DataValidation;
import Utilities.ParcelManagementSystem;

public class Main {
    private static final ParcelManagementSystem pms = ParcelManagementSystem.getInstance();

    public static void main(String[] args) {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("---------- Parcel Delivery System ----------");
            System.out.println("[1] Add Parcel");
            System.out.println("[2] View All Parcels");
            System.out.println("[3] Change Delivery Service");
            System.out.println("[4] Mark as Delivered");
            System.out.println("[5] Exit Program");
            int choice = DataValidation.validateNumberChoiceInput("Choice(1-5): ", 1, 5);
            switch (choice) {
                case 1 -> addParcel();
                case 2 -> viewAllParcels();
                case 3 -> changeDeliveryService();
                case 4 -> markAsDelivered();
                case 5 -> isRunning = false;
            }
        }
    }

    public static void addParcel() {
        System.out.println("=".repeat(20));
        System.out.println("Parcel types:");
        System.out.println("[1] Document");
        System.out.println("[2] Box");
        System.out.println("[3] Fragile");
        int parcelType = DataValidation.validateNumberChoiceInput("Select parcel type(1-3): ", 1, 3);
        boolean duplicateExists = true;
        String trackingNumber = "";
        do {
            trackingNumber = DataValidation.validateTrackingNumberInput();
            duplicateExists = pms.findDuplicateParcel(trackingNumber);
            if (duplicateExists) {
                System.out.printf("Parcel with Tracking number %s already exists. Try Again\n", trackingNumber);
            }
        } while (duplicateExists);
        String receiverName = DataValidation.validateReceiverNameInput("Enter Receiving Name: ").trim();

        double maxWeight = switch (parcelType) {// TODO ADD NEW MAX WEIGHT
            case 1 -> Document.MAX_WEIGHT;
            case 2 -> Box.MAX_WEIGHT;
            case 3 -> Fragile.MAX_WEIGHT;
            default -> 10;
        };
        double weight = DataValidation.validateWeightInput("Enter weight in kg(min: 0.1kg, max: " + maxWeight + "kg): ",
                maxWeight);

        Parcel parcel;
        DeliveryService initial = new StandardDelivery();
        try {
            parcel = switch (parcelType) {
                case 1 -> {
                    int pages = DataValidation.validateNumberChoiceInput("Number of pages (1-100): ", 1, 100);
                    yield new Document(trackingNumber, receiverName, weight, pages, initial);
                }
                case 2 -> {
                    int length = DataValidation.validateMeasurmentInput("Enter Length", "length");
                    int width = DataValidation.validateMeasurmentInput("Enter Width", "width");
                    int height = DataValidation.validateMeasurmentInput("Enter Height", "height");
                    yield new Box(trackingNumber, receiverName, weight, length, width, height, initial);
                }
                case 3 -> {
                    int length = DataValidation.validateMeasurmentInput("Enter Length", "length");
                    int width = DataValidation.validateMeasurmentInput("Enter Width", "width");
                    int height = DataValidation.validateMeasurmentInput("Enter Height", "height");
                    String handlingNote = DataValidation.validateStringInput("Enter Handling Note: ");
                    yield new Fragile(trackingNumber, receiverName, weight, length, width, height, handlingNote,
                            initial);
                }
                default -> throw new IllegalStateException("Unexpected parcel type: " + parcelType);
            };
        } catch (InvalidDataInputException e) {
            System.out.println("Cannot create parcel: " + e.getMessage());
            return;
        }
        boolean deliveryServiceIsAllowed;
        DeliveryService ds;

        do {
            ds = DataValidation.validateDeliveryService();
            deliveryServiceIsAllowed = ds.isAllowed(parcel);

            if (!deliveryServiceIsAllowed) {
                System.out.println(ds.getRejectionMessage());
            }
        } while (!deliveryServiceIsAllowed);

        try {
            parcel.setDeliveryService(ds);
        } catch (InvalidParcelException e) {
            System.out.println("Invalid delivery service for the parcel.");
        }

        try {
            pms.addParcel(parcel);
        } catch (InvalidParcelException e) {
            System.out.println("Cannot add parcel: " + e.getMessage());
            return;
        }
        System.out.println("=".repeat(60));
        System.out.printf("Parcel added successfully! Delivery Fee: Php %,.2f\n", parcel.getDeliveryFee());
        System.out.println("=".repeat(60));
    }

    public static void viewAllParcels() {// TODO IMPROVE DISPLAY
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to display.");
            return;
        }
        String displayFormat = "%-20s %-20s %-20s %-20.2f %-20s %-,20.2f %-20s\n";
        System.out.println("\nALL PARCELS");
        System.out.println("-".repeat(140));
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "TRACKING NUMBER", "PARCEL TYPE",
                "NAME OF RECEIVER", "CHARGABLE WEIGHT", "SERVICE", "DELIVERY FEE", "STATUS");
        System.out.println("-".repeat(140));
        for (Parcel parcel : parcels) {
            String receiverName = parcel.getReceiverName();
            if (receiverName.length() > 20) {
                receiverName = receiverName.substring(0, 17) + "...";
            }
            System.out.printf(displayFormat, parcel.getTrackingNumber(), parcel.getType(), receiverName,
                    parcel.getChargeableWeight(), parcel.getDeliveryService().getName(), parcel.getDeliveryFee(),
                    parcel.getStatus().toString());
        }
    }

    public static void changeDeliveryService() { // TODO REVIEW AND ADJUST OUTPUT DISPLAYS
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to change delivery service.");
            return;
        }
        String trackingNumber = DataValidation.validateTrackingNumberInput();
        Parcel specificParcel = pms.findSpecificParcel(trackingNumber);

        if (specificParcel == null) {
            System.out.println("Parcel not found!");
            return;
        }
        if (specificParcel.isDelivered()) {
            System.out.println("Parcel already delivered!");
            return;
        }
        DeliveryService newService;
        boolean deliveryServiceIsAllowed = false;

        do {
            newService = DataValidation.validateDeliveryService();
            if (newService.getClass() == specificParcel.getDeliveryService().getClass()) {
                System.out.printf("Parcel already has a Delivery Service of %s. Choose again.\n",
                        specificParcel.getDeliveryService().getName());
                continue;
            }
            deliveryServiceIsAllowed = newService.isAllowed(specificParcel);

            if (!deliveryServiceIsAllowed) {
                System.out.println(newService.getRejectionMessage());
            }
        } while (!deliveryServiceIsAllowed);
        try {
            specificParcel.setDeliveryService(newService);
        } catch (InvalidParcelException e) {
            System.out.println("Invalid delivery service for the parcel.");
        }
        System.out.println("=".repeat(60));
        System.out.printf("Delivery service changed! New fee: Php %,.2f\n", specificParcel.getDeliveryFee());
        System.out.println("=".repeat(60));
    }

    public static void markAsDelivered() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to mark as delivered.");
            return;
        }
        String trackingNumber = DataValidation.validateTrackingNumberInput();
        Parcel specificParcel = pms.findSpecificParcel(trackingNumber);
        System.out.println("=".repeat(20));
        if (specificParcel == null) {
            System.out.println("Parcel not found!");
        } else if (specificParcel.isDelivered()) {
            System.out.println("Parcel was already delivered.");
        } else {
            specificParcel.markAsDelivered();
            System.out.println("Parcel delivered successfully!");
        }
        System.out.println("=".repeat(20));
    }
}
