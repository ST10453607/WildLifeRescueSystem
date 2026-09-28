package wildliferescue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RescueTests {

    private RescueManager manager;
    private InjuredAnimalRescue injured;
    private OrphanedAnimalRescue orphaned;
    private EndangeredSpeciesRescue endangered;

    @BeforeEach
    void setUp() {
        manager = new RescueManager();
        // base cost = 2 days x R100 = R200
        injured = new InjuredAnimalRescue("R001", "Rex", "Leopard", "Kruger", "Sam",
                2, 100.0, "Pending", "Broken leg", 500.0, true);
        // base cost = 3 days x R100 = R300
        orphaned = new OrphanedAnimalRescue("R002", "Bella", "Rhino", "Addo", "Thabo",
                3, 100.0, "Pending", 4, 200.0, true);
        // base cost = 2 days x R100 = R200
        endangered = new EndangeredSpeciesRescue("R003", "Zola", "Pangolin", "Limpopo", "Naledi",
                2, 100.0, "Pending", "Critically Endangered", 1000.0, true);
    }

    // ---------- COST CALCULATIONS ----------

    @Test
    void injuredCostWithSurgery() {
        // 200 base + 500 treatment + 5000 surgery = 5700
        assertEquals(5700.0, injured.calculateTotalCost(), 0.001);
    }

    @Test
    void injuredCostWithoutSurgery() {
        InjuredAnimalRescue noSurgery = new InjuredAnimalRescue("R004", "Max", "Lion", "Kruger", "Sam",
                2, 100.0, "Pending", "Sprain", 500.0, false);
        // 200 base + 500 treatment = 700
        assertEquals(700.0, noSurgery.calculateTotalCost(), 0.001);
    }

    @Test
    void orphanedCostWithFoster() {
        // 300 base + 200 feeding + 2500 foster = 3000
        assertEquals(3000.0, orphaned.calculateTotalCost(), 0.001);
    }

    @Test
    void orphanedCostWithoutFoster() {
        OrphanedAnimalRescue noFoster = new OrphanedAnimalRescue("R005", "Tia", "Zebra", "Addo", "Thabo",
                3, 100.0, "Pending", 4, 200.0, false);
        // 300 base + 200 feeding = 500
        assertEquals(500.0, noFoster.calculateTotalCost(), 0.001);
    }

    @Test
    void endangeredCostWithSpecialistTeam() {
        // 200 base + 1000 security + 8000 specialist = 9200
        assertEquals(9200.0, endangered.calculateTotalCost(), 0.001);
    }
    @Test
    void endangeredCostWithoutSpecialistTeam() {
        EndangeredSpeciesRescue noTeam = new EndangeredSpeciesRescue("R006", "Kito", "Pangolin", "Limpopo", "Naledi",
                2, 100.0, "Pending", "Endangered", 1000.0, false);
        // 200 base + 1000 security = 1200
        assertEquals(1200.0, noTeam.calculateTotalCost(), 0.001);
    }

    // ---------- PRIORITY CALCULATIONS ----------

    @Test
    void injuredWithSurgeryIsHighPriority() {
        assertEquals("High", injured.determineRescuePriority());
    }

    @Test
    void injuredWithoutSurgeryIsMediumPriority() {
        InjuredAnimalRescue noSurgery = new InjuredAnimalRescue("R004", "Max", "Lion", "Kruger", "Sam",
                2, 100.0, "Pending", "Sprain", 500.0, false);
        assertEquals("Medium", noSurgery.determineRescuePriority());
    }

    @Test
    void orphanedUnderSixMonthsIsHighPriority() {
        assertEquals("High", orphaned.determineRescuePriority());
    }

    @Test
    void orphanedOverSixMonthsIsMediumPriority() {
        OrphanedAnimalRescue older = new OrphanedAnimalRescue("R005", "Tia", "Zebra", "Addo", "Thabo",
                3, 100.0, "Pending", 10, 200.0, false);
        assertEquals("Medium", older.determineRescuePriority());
    }

    @Test
    void endangeredWithSpecialistTeamIsCritical() {
        assertEquals("Critical", endangered.determineRescuePriority());
    }

    @Test
    void endangeredWithoutSpecialistTeamIsHigh() {
        EndangeredSpeciesRescue noTeam = new EndangeredSpeciesRescue("R006", "Kito", "Pangolin", "Limpopo", "Naledi",
                2, 100.0, "Pending", "Endangered", 1000.0, false);
        assertEquals("High", noTeam.determineRescuePriority());
    }

    // ---------- STATUS UPDATES ----------

    @Test
    void newCaseStartsAsPending() {
        assertEquals("Pending", injured.getCurrentRescueStatus());
    }

    @Test
    void startRescueSetsInProgress() {
        injured.startRescue();
        assertEquals("In Progress", injured.getCurrentRescueStatus());
    }

    @Test
    void completeRescueSetsCompleted() {
        injured.completeRescue();
        assertEquals("Completed", injured.getCurrentRescueStatus());
    }

    @Test
    void managerUpdatesStatusOfExistingCase() {
        manager.addRescueCase(injured);
        assertTrue(manager.updateRescueStatus("R001", "In Progress"));
        assertEquals("In Progress", injured.getCurrentRescueStatus());
    }

    @Test
    void managerRejectsStatusUpdateForUnknownId() {
        assertFalse(manager.updateRescueStatus("R999", "Completed"));
    }

    @Test
    void managerRejectsInvalidStatusText() {
        manager.addRescueCase(injured);
        assertFalse(manager.updateRescueStatus("R001", "banana"));
        assertEquals("Pending", injured.getCurrentRescueStatus());
    }

    // ---------- SEARCHING ----------

    @Test
    void findsExistingCaseById() {
        manager.addRescueCase(injured);
        RescueCase found = manager.findById("R001");
        assertNotNull(found);
        assertEquals("R001", found.getRescueCaseId());
    }

    @Test
    void searchIgnoresUpperAndLowerCase() {
        manager.addRescueCase(injured);
        assertNotNull(manager.findById("r001"));
    }

    @Test
    void searchForUnknownIdReturnsNull() {
        manager.addRescueCase(injured);
        assertNull(manager.findById("R999"));
    }

    // ---------- DUPLICATE IDs ----------

    @Test
    void duplicateIdIsRejected() {
        assertTrue(manager.addRescueCase(injured));
        InjuredAnimalRescue duplicate = new InjuredAnimalRescue("R001", "Other", "Cheetah", "Kruger", "Sam",
                1, 50.0, "Pending", "Cut", 100.0, false);
        assertFalse(manager.addRescueCase(duplicate));
        assertEquals(1, manager.getAllCases().size());
    }
}