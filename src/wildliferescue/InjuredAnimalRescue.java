package wildliferescue;

import wildliferescue.RescueCase;

public class InjuredAnimalRescue extends RescueCase {
    private String injuryType;
    private double treatmentCost;
    private boolean emergencySurgery;

    public InjuredAnimalRescue(String id, String name, String species, String location, String ranger, int days, double dailyCost, String status, String injuryType, double treatmentCost, boolean surgery) {
        super(id, name, species, location, ranger, days, dailyCost, status);
        this.injuryType = injuryType;
        this.treatmentCost = treatmentCost;
        this.emergencySurgery = surgery;
    }
    @Override public double calculateTotalCost()
    { return calculateBaseCost() + treatmentCost + (emergencySurgery ? 5000 : 0); }
    //Priority rule: surgery cases are life-threatening and time-sensitive, so High; Otherwise medium.
    @Override public String determineRescuePriority() { return emergencySurgery ? "High" : "Medium"; }
    @Override public String getRescueType() { return "Injured Animal"; }
    @Override public void displaySpecificInfo() {
        System.out.println("Injury Type: " + injuryType);
        System.out.println("Treatment Cost: " + treatmentCost);
        System.out.println("Emergency Surgery: " + emergencySurgery);
    }
}