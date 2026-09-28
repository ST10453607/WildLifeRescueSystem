package wildliferescue;

import wildliferescue.RescueCase;

public class OrphanedAnimalRescue extends RescueCase {
    private int estimatedAgeMonths;
    private double feedingCost;
    private boolean fosterCareRequired;

    public OrphanedAnimalRescue(String id, String name, String species, String location, String ranger, int days, double dailyCost, String status, int ageMonths, double feedingCost, boolean foster) {
        super(id, name, species, location, ranger, days, dailyCost, status);
        this.estimatedAgeMonths = ageMonths;
        this.feedingCost = feedingCost;
        this.fosterCareRequired = foster;
    }
    @Override public double calculateTotalCost() {
        return calculateBaseCost() + feedingCost + (fosterCareRequired ? 2500 : 0); }
    //Animals under 6 months cannot survive without care, so High; otherwise medium.
    @Override public String determineRescuePriority() { return estimatedAgeMonths < 6 ? "High" : "Medium"; }
    @Override public String getRescueType() { return "Orphaned Animal"; }
    @Override public void displaySpecificInfo() {
        System.out.println("Age in Months: " + estimatedAgeMonths);
        System.out.println("Feeding Cost: " + feedingCost);
        System.out.println("Foster Care Required: " + fosterCareRequired);
    }
}