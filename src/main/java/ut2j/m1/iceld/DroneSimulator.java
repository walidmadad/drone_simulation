package ut2j.m1.iceld;

import ut2j.m1.iceld.sensors.GPS;
import ut2j.m1.iceld.sensors.IMU;
import ut2j.m1.iceld.sensors.Lidar;
import ut2j.m1.iceld.util.CPUUsageMonitor;
import ut2j.m1.iceld.util.FaultInjector;
import ut2j.m1.iceld.control.MissionPlanner;
import ut2j.m1.iceld.control.FlightController;
import java.util.List;

/* --- DroneSimulator : orchestrateur ---
Objectifs :
 - Instancier tous les modules
 - Fournir API simplifiée pour tests et scénarios Cucumber
*/
public class DroneSimulator {
    public GPS gpsPrimary, gpsSecondary;
    public IMU imu;
    public Lidar lidar;
    public CPUUsageMonitor cpu;
    public FaultInjector faults;
    public MissionPlanner planner;
    public FlightController controller;

    public DroneSimulator(double startLat, double startLon) {
        // Créer instances capteurs, faults, planner, controller
        gpsPrimary = new GPS(startLat, startLon);
        gpsSecondary = new GPS(startLat, startLon);
        imu = new IMU();
        lidar = new Lidar();
        cpu = new CPUUsageMonitor();
        faults = new FaultInjector();
        planner = new MissionPlanner(cpu);
        controller = new FlightController(gpsPrimary, gpsSecondary, imu, lidar, cpu, faults);

        // Initialiser controller à position de départ
        controller.startAt(startLat, startLon);
    }

    public double runMission(double destLat, double destLon) {
        double[] startPos = gpsPrimary.readPosition();

        // fallback vers GPS secondaire si primaire inactif
        if (startPos == null) {
            startPos = gpsSecondary.readPosition();
            if (startPos == null) {
                throw new IllegalStateException("Aucun GPS actif disponible");
            }
        }

        List<double[]> path = planner.plan(startPos[0], startPos[1], destLat, destLon);
        double totalDistance = controller.executeMission(path);

        return totalDistance;
    }


    public void injectFault(FaultInjector.Fault f) {
        faults.inject(f);
    }

    public void clearFault(FaultInjector.Fault f) {
        faults.clear(f);
    }
}
