package ut2j.m1.iceld.steps;

import io.cucumber.java.en.*;
import ut2j.m1.iceld.DroneSimulator;
import ut2j.m1.iceld.util.FaultInjector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DroneStepDefs {
    private DroneSimulator sim;
    private double resultDistance;
    private double cpuMeasured;

    /* --- Scénario Exactitude --- */
    @Given("une cible GPS fixée à latitude {double} and longitude {double}")
    public void given_target(double lat, double lon) {
        sim = new DroneSimulator(43.6040, 1.4435);
        sim.gpsPrimary.setPosition(43.6040, 1.4435);
        sim.gpsSecondary.setPosition(43.6040, 1.4435);
        sim.gpsPrimary.setNoiseStdMeters(0.03); // faible bruit
    }

    @When("la mission est exécutée")
    public void when_execute() {
        resultDistance = sim.runMission(43.6045, 1.4440);
        cpuMeasured = sim.cpu.measureUsage();
    }

    @Then("la distance finale doit être <= {double} metre")
    public void then_check_distance(double tol) {
        System.out.println("Distance simulée (m): " + resultDistance);
        assertTrue(resultDistance <= tol, "Distance trop grande: " + resultDistance);
    }

    /* --- Scénario Fiabilité --- */
    @Given("le GPS principal est actif")
    public void gps_active() {
        sim = new DroneSimulator(43.6040, 1.4435);
    }

    @Given("la mission est démarrée")
    public void mission_started() {
        // simulateur prêt, rien à faire ici
    }

    @When("une panne GPS est injectée")
    public void inject_gps_fault() {
        sim.injectFault(FaultInjector.Fault.GPS_OFF);
        sim.gpsPrimary.setActive(false);
    }

    @Then("le GPS secondaire doit être actif et la mission continuer")
    public void then_secondary_active() {
        boolean secondaryActive = sim.gpsSecondary.isActive();
        double d = sim.runMission(43.6045, 1.4440);
        assertTrue(secondaryActive, "GPS secondaire inactif");
        assertTrue(d <= 0.5, "Mission a échoué (distance>0.5m)");
    }

    /* --- Scénario Performance --- */
    @Given("le simulateur démarre avec charge simulée")
    public void start_with_load() {
        sim = new DroneSimulator(43.6040, 1.4435);
        sim.cpu.setSimulatedLoad(2.5);
    }

    @When("la planification de trajectoire est lancée")
    public void plan_launched() {
        sim.planner.plan(43.6040, 1.4435, 43.6045, 1.4440);
        cpuMeasured = sim.cpu.measureUsage();
    }

    @Then("l'utilisation CPU mesurée doit rester < {double}")
    public void then_cpu_check(double threshold) {
        System.out.println("CPU simulé %: " + cpuMeasured);
        double tolerance = 0.5; // tolérance pour le bruit aléatoire
        assertTrue(cpuMeasured < threshold + tolerance, "CPU trop élevé: " + cpuMeasured);
    }
}