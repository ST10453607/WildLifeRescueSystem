package wildliferescue;

import java.util.ArrayList;

public class RescueManager {
    private ArrayList<RescueCase> cases = new ArrayList<>();

    public boolean addRescueCase(RescueCase rc) {
      if (findById(rc.getRescueCaseId()) != null) {
          return false;
      }
      return cases.add(rc);
    }

    public RescueCase findById(String id) {
        for (RescueCase rc : cases) {
            if (rc.getRescueCaseId().equalsIgnoreCase(id)) return rc;
        }
        return null;
    }




    public ArrayList<RescueCase> getAllCases() {
        return cases;
    }


    public boolean updateRescueStatus(String id, String newStatus) {
        RescueCase rc = findById(id);
        if (rc != null) {
            if (newStatus.equalsIgnoreCase("In Progress")) {
                rc.startRescue();
            } else if (newStatus.equalsIgnoreCase("Completed")) {
                rc.completeRescue();
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    public double getTotalCost() {
        double sum = 0;
        for (RescueCase rc : cases) sum += rc.calculateTotalCost();
        return sum;
    }

    public void displayAll() {
        for (RescueCase rc : cases) {
            System.out.println(rc.generateSummary());
            rc.displaySpecificInfo();
            System.out.println("---");
        }
    }
    public void printReport() {
        if (cases.isEmpty()) {
            System.out.println("No rescue cases to report");
            return;
        }
        for (RescueCase rc : cases) {
            System.out.println("ID: " + rc.getRescueCaseId()
                    +  " | Type: " + rc.getRescueType()
                    + " | Species: " + rc.getSpecies()
                    + " | Location: " + rc.getRescueLocation()
                    + " | Ranger: " + rc.getAssignedRanger()
                    + " | Status: " + rc.getCurrentRescueStatus()
                    + " | Cost: R" + rc.calculateTotalCost());
        }
        System.out.println("Total rescue cases: " + cases.size());
        System.out.println("Total estimated rescue cost: R" + getTotalCost());
    }
}