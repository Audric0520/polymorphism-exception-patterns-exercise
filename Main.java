public class Main {
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
        String trackingNumber = DataValidation.validateStringInput("Enter tracking Number: ");

        double maxWeight = switch (parcelType) {// TODO ADD NEW MAX WEIGHT
            case 1 -> Document.MAX_WEIGHT;
            default -> 10;
        };
        double weight = DataValidation.validateWeightInput("Enter weight in kg(max: " + maxWeight + "kg): ", maxWeight);
    }

    public static void viewAllParcels() {

    }

    public static void changeDeliveryService() {

    }

    public static void markAsDelivered() {

    }
}
