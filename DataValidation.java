import java.util.Scanner;

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

    public static String validateCategoryInput(String prompt) {
        String category = "";
        boolean validCategory = false;
        do {
            category = DataValidation.validateStringInput(prompt);
            if (!(category.equalsIgnoreCase("clothing") || category.equalsIgnoreCase("electronics")
                    || category.equalsIgnoreCase("entertainment"))) {
                System.out.printf("Category '%s' does not exist!\n", category);
                continue;
            }
            validCategory = true;
        } while (!validCategory);
        return category.toLowerCase();
    }

    /*
     * public static String validateIDInput(String category) {
     * String prefix = getCategoryPrefix(category);
     * System.out.
     * printf("ID format: '%s' followed by 6 numbers (e.g. %s123456, %s122526)\n",
     * prefix, prefix,
     * prefix.toLowerCase());
     * boolean isRunning = true;
     * String id = "";
     * while (isRunning) {
     * id = validateStringInput("Input ID: ");
     * if (!id.matches("(?i)" + prefix + "\\d{6}")) {
     * System.out.printf(
     * "Invalid ID Input. ID must be '%s' followed by 6 numbers (e.g. %s052007, %s092702). Try Again.\n"
     * ,
     * prefix, prefix, prefix.toLowerCase());
     * continue;
     * }
     * isRunning = false;
     * }
     * return id.toUpperCase();
     * }
     */
    public static int validateQuantityInput(String prompt, boolean forUpdating) {
        boolean isRunning = true;
        int number = 0;
        while (isRunning) {
            number = validateIntInput(prompt);
            if (forUpdating && (number < 0 || number > 1000)) {
                System.out.println("Invalid Quantity Input. Quantity must be between 0 - 1000. Try Again.");
                continue;
            } else if (!forUpdating && (number <= 0 || number > 1000)) {
                System.out.println("Invalid Quantity Input. Quantity must be between 1 - 1000. Try Again.");
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static String validateFieldChoice(String prompt) {
        int choice = validateNumberChoiceInput(prompt, 1, 2);
        return (choice == 1) ? "quantity" : "price";
    }

    public static boolean validateSortOrderChoice(String prompt) {
        int choice = validateNumberChoiceInput(prompt, 1, 2);
        return choice == 1;
    }
}