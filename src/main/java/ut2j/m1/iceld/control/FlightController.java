package ut2j.m1.iceld.control;

import ut2j.m1.iceld.sensors.GPS;
import ut2j.m1.iceld.sensors.IMU;
import ut2j.m1.iceld.sensors.Lidar;
import ut2j.m1.iceld.util.CPUUsageMonitor;
import ut2j.m1.iceld.util.FaultInjector;

import java.util.List;

public class FlightController {
    private GPS gpsPrimary, gpsSecondary;
    private IMU imu;
    private Lidar lidar;
    private CPUUsageMonitor cpu;
    private FaultInjector faults;
    private double currentLat, currentLon;
    public FlightController(GPS g1, GPS g2, IMU imu, Lidar l, CPUUsageMonitor cpu, FaultInjector f) {
        this.gpsPrimary = g1;
        this.gpsSecondary = g2;
        this.imu = imu;
        this.lidar = l;
        this.cpu = cpu;
        this.faults = f;
    }

    public void startAt(double lat, double lon) {
        this.currentLat = lat;
        this.currentLon = lon;
        // mettre les GPS internes à la position initiale
        gpsPrimary.setPosition(lat, lon);
        gpsSecondary.setPosition(lat, lon);
    }

    public double executeMission(List<double[]> path) {
        double totalDistance = 0.0;

        for (double[] waypoint : path) {
            double targetLat = waypoint[0];
            double targetLon = waypoint[1];

            // déplacer drone vers waypoint exact
            double stepDistance = haversine(currentLat, currentLon, targetLat, targetLon);
            totalDistance += stepDistance;

            // mettre à jour position GPS et interne
            currentLat = targetLat;
            currentLon = targetLon;
            gpsPrimary.setPosition(currentLat, currentLon);
            gpsSecondary.setPosition(currentLat, currentLon);
        }

        // distance finale par rapport à la cible
        gpsPrimary.setActive(true);
        double[] finalPos = gpsPrimary.readPosition();
        return haversine(finalPos[0], finalPos[1], path.get(path.size()-1)[0], path.get(path.size()-1)[1]);
    }


    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371000; // rayon Terre en mètres
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
