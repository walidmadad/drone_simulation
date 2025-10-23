package ut2j.m1.iceld.control;

import ut2j.m1.iceld.util.CPUUsageMonitor;

import java.util.ArrayList;
import java.util.List;

/* --- MissionPlanner : planifie trajectoire ---
Objectifs :
- Créer liste de waypoints
- Simuler charge CPU
*/

public class MissionPlanner {
    private CPUUsageMonitor cpu;
    public MissionPlanner(CPUUsageMonitor cpu) {
        this.cpu = cpu;
    }
    public java.util.List<double[]> plan(double startLat, double startLon, double destLat, double destLon) {
        // augmenter temporairement CPU
        cpu.setSimulatedLoad(80.0);

        // créer liste waypoints départ -> intermédiaire -> destination
        List<double[]> waypoints = new ArrayList<>();

        // waypoint de départ
        waypoints.add(new double[] { startLat, startLon });

        // point intermédiaire (milieu de la trajectoire)
        double midLat = (startLat + destLat) / 2.0;
        double midLon = (startLon + destLon) / 2.0;
        waypoints.add(new double[] { midLat, midLon });

        // destination
        waypoints.add(new double[] { destLat, destLon });

        // réduire CPU
        cpu.setSimulatedLoad(5.0);
        return waypoints;
    }
}
