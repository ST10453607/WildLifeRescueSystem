package wildliferescue;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static RescueManager manager = new RescueManager();

    public static void main(String[] args) {
        int choice = 0;
        while (choice != 6) {
            printMenu();
            choice = readMenuChoice();
            switch (choice) {
                case 1:
                    createRescueCase();
                    break;
                case 2:
                    searchRescueCase();
                    break;
                case 3:
                    updateRescueStatus();
                    break;
                case 4:
                    for (RescueCase rc : manager.getAllCases()) {
                        System.out.println(rc.getRescueCaseId() + " | " + rc.getSpecies() + " | " + rc.getCurrentRescueStatus());
                        rc.displaySpecificInfo();
                        System.out.println("---");
                    }
                    break;
                case 5:
                    manager.printReport();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n Wildlife Rescue System");
        System.out.println("1. Create Rescue Case");
        System.out.println("2. Search Rescue Case by ID");
        System.out.println("3. Update Rescue status");
        System.out.println("4. View all Rescue cases");
        System.out.println("5. Print full report");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be empty.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) return value;
                else System.out.println("Value must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter y or n.");
        }
    }

    private static int readMenuChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= 6) return choice;
            else { System.out.println("Invalid choice"); return -1; }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice");
            return -1;
        }
    }

    private static void createRescueCase() {
        System.out.println("1. Injured Animal");
        System.out.println("2. Orphaned Animal");
        System.out.println("3. Endangered Species");
        int type = readPositiveInt("Select rescue type (1-3): ");
        if (type < 1 || type > 3) { System.out.println("Invalid type."); return; }

        String id = readNonBlank("Enter Rescue Case ID: ");
        if (manager.findById(id) != null) {
            System.out.println("Error: Rescue Case ID already exists.");
            return;
        }

        String name = readNonBlank("Animal Name: ");
        String species = readNonBlank("Species: ");
        String location = readNonBlank("Rescue Location: ");
        String ranger = readNonBlank("Assigned Ranger: ");
        int days = readPositiveInt("Number of Rescue Days: ");
        double dailyCost = readPositiveDouble("Enter daily cost: ");
        String status = "Pending";

        RescueCase newCase = null;
        if (type == 1) {
            String injury = readNonBlank("Injury Description: ");
            double treatCost = readPositiveDouble("Treatment Cost: ");
            boolean surgery = readYesNo("Requires Surgery?");
            newCase = new InjuredAnimalRescue(id, name, species, location, ranger, days, dailyCost, status, injury, treatCost, surgery);
        } else if (type == 2) {
            int age = readPositiveInt("Age in months: ");
            double feed = readPositiveDouble("Feeding Cost (R): ");
            boolean foster = readYesNo("Needs Foster?");
            newCase = new OrphanedAnimalRescue(id, name, species, location, ranger, days, dailyCost, status, age, feed, foster);
        } else {
            String threat = readNonBlank("Conversation Classification: ");
            double fee = readPositiveDouble("Security Cost (R): ");
            boolean permit = readYesNo("Specalist team required?");
            newCase = new EndangeredSpeciesRescue(id, name, species, location, ranger, days, dailyCost, status, threat, fee, permit);
        }
        if (manager.addRescueCase(newCase)) {
            System.out.println("Rescue Case added!.");
        } else {
            System.out.println("Error: Rescue Case ID already exists.");
        }
    }

    private static void searchRescueCase() {
        String id = readNonBlank("Enter ID to search: ");
        RescueCase rc = manager.findById(id);
        if (rc != null) {
            System.out.println(rc.generateSummary());
            rc.displaySpecificInfo();
        } else {
            System.out.println("Not found.");
        }
    }

    private static void updateRescueStatus() {
        String id = readNonBlank("Enter ID to update: ");
        System.out.println("1. Start rescue");
        System.out.println("2. Complete rescue");
        int option = readPositiveInt("Choose (1-2) ");
        String newStatus;
        if (option == 1) {
            newStatus = "In Progress";
        } else if (option == 2) {
            newStatus = "Complete";
        } else {
            System.out.println("Invalid option.");
            return;
        }
        if (manager.updateRescueStatus(id, newStatus)) {
            System.out.println("Status updated!.");
        } else {
            System.out.println("ID not found.");
        }
    }
}