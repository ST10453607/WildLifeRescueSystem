package wildliferescue;

public abstract class RescueCase implements RescueOperations {
    private String rescueCaseId;
    private String animalName;
    private String species;
    private String rescueLocation;
    private String assignedRanger;
    private int numberOfRescueDays;
    private double dailyCareCost;
    private String currentRescueStatus;

    public RescueCase(String rescueCaseId, String animalName, String species, String rescueLocation, String assignedRanger, int numberOfRescueDays, double dailyCareCost, String currentRescueStatus) {
        this.rescueCaseId = rescueCaseId;
        this.animalName = animalName;
        this.species = species;
        this.rescueLocation = rescueLocation;
        this.assignedRanger = assignedRanger;
        this.numberOfRescueDays = numberOfRescueDays;
        this.dailyCareCost = dailyCareCost;
        this.currentRescueStatus = currentRescueStatus;
    }

    public String getRescueCaseId() {
        return rescueCaseId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getSpecies() {
        return species;
    }

    public String getRescueLocation() {
        return rescueLocation;
    }

    public String getAssignedRanger() {
        return assignedRanger;
    }

    public int getNumberOfRescueDays() {
        return numberOfRescueDays;
    }

    public double getDailyCareCost() {
        return dailyCareCost;
    }

    public String getCurrentRescueStatus() {
        return currentRescueStatus;
    }

    public void setCurrentRescueStatus(String s) {
        this.currentRescueStatus = s;
    }

    protected double calculateBaseCost() {
        return dailyCareCost * numberOfRescueDays;
    }

    @Override
    public void startRescue() {
        this.currentRescueStatus = "In Progress";
    }

    @Override
    public void completeRescue() {
        this.currentRescueStatus = "Completed";
    }

    @Override
    public String generateSummary() {
        return "Rescue Case ID: " + rescueCaseId + "\nRescue Type: " + getRescueType() + "\nSpecies: " + species + "\nAssigned Ranger: " + assignedRanger + "\nRescue Priority: " + determineRescuePriority() + "\nCurrent Status: " + currentRescueStatus + "\nTotal Rescue Cost: R" + calculateTotalCost();
    }
    public abstract double calculateTotalCost();
    public abstract String determineRescuePriority();
    public abstract String getRescueType();
    public abstract void displaySpecificInfo();
}