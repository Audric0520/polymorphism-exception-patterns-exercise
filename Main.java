import java.util.ArrayList;

public class Main {
    private static final ParcelManagementSystem pms = ParcelManagementSystem.getInstance();

    public static void main(String[] args) {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("--Parcel Delivery System--");
            System.out.println("1. Add Parcel");
            System.out.println("2. View All Parcels");
            System.out.println("3. Change Delivery Service");
            System.out.println("4. Mark as Delivered");
            System.out.println("5. Exit Program");
            System.out.print("Choice: ");
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
        System.out.println("Parcel types:");
        System.out.println("1. Document");
        System.out.println("2. Box");
        System.out.println("3. Fragile");
        int parcelType = DataValidation.validateNumberChoiceInput("Select parcel type: ", 1, 3);
        boolean duplicateExists = true;
        String trackingNumber = "";
        do {
            trackingNumber = DataValidation.validateTrackingNumberInput();
            duplicateExists = pms.findDuplicateParcel(trackingNumber);
            if (duplicateExists) {
                System.out.printf("Parcel with Tracking number %s already exists. Try Again\n", trackingNumber);
            }
        } while (duplicateExists);
        String receiverName = DataValidation.validateReceiverNameInput("Enter Receiving Name: ");

        double maxWeight = switch (parcelType) {// TODO ADD NEW MAX WEIGHT
            case 1 -> Document.MAX_WEIGHT;
            default -> 10;
        };
        double weight = DataValidation.validateWeightInput("Enter weight in kg(max: " + maxWeight + "kg): ", maxWeight);

        Parcel parcel = new Document(null, null, 0, 0, null); // TODO REVIEW THIS
        DeliveryService ds = new StandardDelivery(); // placeholder until the user picks
        if (parcelType == 1) {
            int pages = DataValidation.validateNumberChoiceInput("Number of pages (1-100): ", 1, 100);
            parcel = new Document(trackingNumber, receiverName, weight, pages, ds);
        }

        DeliveryService service = DataValidation.validateDeliveryService();
        parcel.setDeliveryService(service);
        pms.addParcel(parcel);
        System.out.println("Parcel Added Successfully.");// TODO ADJUST MESSAGE BASED ON WHAT WAS ASKED
    }

    public static void viewAllParcels() {// TODO IMPROVE DISPLAY
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to display.");
            return;
        }
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "TRACKING NUMBER", "PARCEL TYPE",
                "NAME OF RECEIVER", "CHARGABLE WEIGHT", "SERVICE", "DELIVERY FEE", "STATUS");
        for (Parcel parcel : parcels) {
            System.out.println(parcel.getTrackingNumber());
            System.out.println(parcel.getType());
            System.out.println(parcel.getReceiverName());
            System.out.println(parcel.getChargeableWeight());
            System.out.println(parcel.getDeliveryService().getName());
            System.out.println(parcel.getDeliveryFee());
            System.out.println(parcel.getStatus().toString());
        }
    }

    public static void changeDeliveryService() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to change delivery service.");
            return;
        }
    }

    public static void markAsDelivered() {
        ArrayList<Parcel> parcels = pms.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("There are no parcels to mark as delivered.");
            return;
        }
        String trackingNumber = DataValidation.validateTrackingNumberInput();
        Parcel specificParcel = pms.findSpecificParcel(trackingNumber);

        if (specificParcel == null) {
            System.out.println("Parcel not found!");
        } else if (specificParcel.isDelivered()) {
            System.out.println("Parcel was already delivered.");
        } else {
            specificParcel.markAsDelivered();
            System.out.println("Parcel delivered successfully!");
        }
    }
}
