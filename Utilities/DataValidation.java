package Utilities;

import java.util.Scanner;

import DeliveryStrategy.DeliveryService;
import DeliveryStrategy.ExpressDelivery;
import DeliveryStrategy.SameDayDelivery;
import DeliveryStrategy.StandardDelivery;

public class DataValidation {
    public static final Scanner input = new Scanner(System.in);

    public static int validateIntInput(String prompt) {
        boolean isRunning = true;
        int number = 0;
        String inputNumber = "";
        while (isRunning) {
            System.out.print(prompt);
            inputNumber = input.nextLine().trim();
            if (!inputNumber.matches("-?(0|[1-9]\\d*)")) {
                System.out.println("Invalid Input. Only input positive integers without leading zeros. Try Again.");
                continue;
            }
            try {
                number = Integer.parseInt(inputNumber);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Number is too large. Try Again.");
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static int validateNumberChoiceInput(String prompt, int min, int max) {
        boolean isRunning = true;
        int number = 0;
        while (isRunning) {
            number = validateIntInput(prompt);
            if (number >= min & number <= max) {
                isRunning = false;
            } else {
                System.out.printf("Invalid Input. Only input %d-%d without leading zeros. Try Again.\n", min, max);
            }
        }
        return number;
    }

    public static String validateStringInput(String prompt) {
        boolean isRunning = true;
        String stringInput = "";
        while (isRunning) {
            System.out.print(prompt);
            stringInput = input.nextLine().trim();
            if (stringInput.isBlank()) {
                System.out.println("Invalid Input. Input cannot be blank. Try Again.");
            } else {
                isRunning = false;
            }
        }
        return stringInput;
    }

    public static String validateReceiverNameInput(String prompt) {
        boolean isRunning = true;
        String name = "";
        while (isRunning) {
            name = DataValidation.validateStringInput(prompt);
            if (!name.matches("^[a-zA-Z ]+$")) {
                System.out.println("Invalid ReceiverName Input. Name must only contains letters. Try Again.");
                continue;
            } else {
                isRunning = false;
            }
        }
        return name;
    }

    public static double validateWeightInput(String prompt, double maxWeight) {
        boolean isRunning = true;
        double number = 0;
        String inputNumber = "";

        while (isRunning) {
            System.out.print(prompt);
            inputNumber = input.nextLine().trim();

            if (!inputNumber.matches("-?(0|[1-9]\\d*)(\\.\\d+)?")) {
                System.out.println("Invalid Input. Only input numbers without leading zeroes. Try Again.");
                continue;
            }
            number = Double.parseDouble(inputNumber);
            if (number < 0.1 || number > maxWeight) {
                System.out.printf("Invalid Weight Input. Weight must be between 0.1 - %.2f kg. Try Again.\n",
                        maxWeight);
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static int validateMeasurmentInput(String prompt, String typeOfMeasurement) {
        boolean isRunning = true;
        int measureNum = 0;
        while (isRunning) {
            measureNum = validateIntInput(prompt);
            if (measureNum > 0 & measureNum <= 100) {
                isRunning = false;
            } else {
                System.out.printf("Invalid %s input. Input should be between 1-100. Try Again.\n", typeOfMeasurement);
            }
        }
        return measureNum;
    }

    public static String validateTrackingNumberInput() {
        System.out.println("Tracking Number format: 'ABC' followed by 4 numbers (e.g. ABC0001)");
        boolean isRunning = true;
        String trackingNumber = "";
        while (isRunning) {
            trackingNumber = validateStringInput("Input Tracking Number: ");
            if (!trackingNumber.matches("^(?i)abc\\d{4}$")) {
                System.out.println(
                        "Invalid ID Input. Tracking Number must be 'ABC' followed by 4 numbers (e.g. ABC0927, ABC0520). Try Again.");
                continue;
            }
            isRunning = false;
        }
        return trackingNumber.toUpperCase();
    }

    public static DeliveryService validateDeliveryService() {
        System.out.println("Delivery services:");
        System.out.println("1. Standard");
        System.out.println("2. Express");
        System.out.println("3. Same Day");
        int choice = DataValidation.validateNumberChoiceInput("Select Delivery Service(1-3): ", 1, 3);
        return switch (choice) {
            case 1 -> new StandardDelivery();
            case 2 -> new ExpressDelivery();
            default -> new SameDayDelivery();
        };
    }
}