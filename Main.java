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
        System.out.println("-".repeat(40));
        System.out.println("Thank you for using the program.\nExiting...");
        System.out.println("-".repeat(40));
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
        String receiverName = DataValidation.validateReceiverNameInput("Enter Receiver Name: ").trim();
        double minWeight = switch (parcelType) {
            case 1 -> Document.MIN_WEIGHT;
            case 2 -> Box.MIN_WEIGHT;
            default -> Fragile.MIN_WEIGHT;
        };
        double maxWeight = switch (parcelType) {
            case 1 -> Document.MAX_WEIGHT;
            case 2 -> Box.MAX_WEIGHT;
            default -> Fragile.MAX_WEIGHT;
        };
        double weight = DataValidation.validateWeightInput(
                String.format("Enter weight in kg (min: %.2fkg, max: %.2fkg): ", minWeight, maxWeight), minWeight,
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
                    int length = DataValidation.validateMeasurementInput("Enter Length", "length");
                    int width = DataValidation.validateMeasurementInput("Enter Width", "width");
                    int height = DataValidation.validateMeasurementInput("Enter Height", "height");
                    yield new Box(trackingNumber, receiverName, weight, length, width, height, initial);
                }
                case 3 -> {
                    int length = DataValidation.validateMeasurementInput("Enter Length", "length");
                    int width = DataValidation.validateMeasurementInput("Enter Width", "width");
                    int height = DataValidation.validateMeasurementInput("Enter Height", "height");
                    String handlingNote = DataValidation.validateStringInput("Enter Handling Note: ");
                    yield new Fragile(trackingNumber, receiverName, weight, length, width, height, handlingNote,
                            initial);
                }
                default -> throw new IllegalStateException("Unexpected parcel type: " + parcelType);
            };
        } catch (InvalidDataInputException | InvalidParcelException e) {
            System.out.println("Cannot create parcel." + e.getMessage());
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
            System.out.println("Cannot set delivery service: " + e.getMessage());
            return;
        }

        try {
            pms.addParcel(parcel);
        } catch (InvalidParcelException e) {
            System.out.println("Cannot add parcel: " + e.getMessage());
            return;
        }
        displayParcel(parcel);
        System.out.println("=".repeat(60));
        System.out.printf("Parcel added successfully! Delivery Fee: Php %,.2f\n", parcel.getDeliveryFee());
        System.out.println("=".repeat(60));
    }

    public static void viewAllParcels() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("-".repeat(40));
            System.out.println("There are no parcels to display.");
            System.out.println("-".repeat(40));
            return;
        }
        String displayFormat = "%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n";
        System.out.println("\nALL PARCELS");
        System.out.println("-".repeat(140));
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "TRACKING NUMBER", "PARCEL TYPE",
                "RECEIVER NAME", "CHARGEABLE WEIGHT", "DELIVERY SERVICE", "DELIVERY FEE", "STATUS");
        System.out.println("-".repeat(140));
        for (Parcel parcel : parcels) {
            String receiverName = parcel.getReceiverName();
            if (receiverName.length() > 20) {
                receiverName = receiverName.substring(0, 17) + "...";
            }
            System.out.printf(displayFormat, parcel.getTrackingNumber(), parcel.getType(), receiverName,
                    String.format("%.2f kg", parcel.getChargeableWeight()), parcel.getDeliveryService().getName(),
                    String.format("Php %,.2f", parcel.getDeliveryFee()), parcel.getStatus().toString());
        }
    }

    public static void changeDeliveryService() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("-".repeat(40));
            System.out.println("There are no parcels to change delivery service.");
            System.out.println("-".repeat(40));
            return;
        }
        String trackingNumber = DataValidation.validateTrackingNumberInput();
        Parcel specificParcel = pms.findSpecificParcel(trackingNumber);

        if (specificParcel == null) {
            System.out.println("=".repeat(20));
            System.out.println("Parcel not found!");
            System.out.println("=".repeat(20));
            return;
        }
        if (specificParcel.isDelivered()) {
            System.out.println("=".repeat(20));
            System.out.println("Parcel already delivered!");
            System.out.println("=".repeat(20));
            return;
        }
        DeliveryService newService = DataValidation.validateDeliveryService();
        if (newService.getName().equals(specificParcel.getDeliveryService().getName())) {
            System.out.printf("Parcel already uses %s delivery. No changes made.%n",
                    specificParcel.getDeliveryService().getName());
            return;
        }
        try {
            specificParcel.setDeliveryService(newService);
        } catch (InvalidParcelException e) {
            System.out.println(e.getMessage());
            System.out.println("No changes made for delivery service.");
            return;
        }
        System.out.println("=".repeat(60));
        System.out.printf("Delivery service changed! New fee: Php %,.2f%n", specificParcel.getDeliveryFee());
        System.out.println("=".repeat(60));
    }

    public static void markAsDelivered() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("-".repeat(40));
            System.out.println("There are no parcels to mark as delivered.");
            System.out.println("-".repeat(40));
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

    public static void displayParcel(Parcel parcel) {
        System.out.println("-".repeat(40));
        System.out.println("PARCEL INFORMATION");
        System.out.println("-".repeat(40));
        System.out.println("Tracking Number: " + parcel.getTrackingNumber());
        System.out.println("Parcel Type: " + parcel.getType());
        System.out.println("Receiver Name: " + parcel.getReceiverName());
        System.out.printf("Chargeable Weight: %.2f kg%n", parcel.getChargeableWeight());
        System.out.println("Delivery Service: " + parcel.getDeliveryService().getName());
        System.out.print("\nAdditional Information: \n" + parcel.getAdditionalInformation());
    }
}
