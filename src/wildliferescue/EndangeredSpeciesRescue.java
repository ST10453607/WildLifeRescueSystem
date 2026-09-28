package wildliferescue;



public class EndangeredSpeciesRescue extends RescueCase {
    private String conservationClass;
    private double securityCost;
    private boolean specialistTeam;

    public EndangeredSpeciesRescue(String id, String name, String species, String location, String ranger, int days, double dailyCost, String status, String consClass, double securityCost, boolean specialist) {
        super(id, name, species, location, ranger, days, dailyCost, status);
        this.conservationClass = consClass;
        this.securityCost = securityCost;
        this.specialistTeam = specialist;
    }
    @Override public double calculateTotalCost()
    { return calculateBaseCost() + securityCost + (specialistTeam ? 8000 : 0); }

    @Override public String determineRescuePriority() { return "High"; }
    @Override public String getRescueType() { return "Endangered Species"; }
    @Override public void displaySpecificInfo() {
        System.out.println("Conservation Class: " + conservationClass);
        System.out.println("Security Cost: " + securityCost);
        System.out.println("Specialist Team: " + specialistTeam);
    }
}