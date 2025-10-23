package ut2j.m1.iceld.sensors;

import java.util.Random;

/* --- IMU : attitude du drone (roll, pitch, yaw) ---
Objectifs :
 - Comprendre capteur orientation
 - Simuler bruit et activation/désactivation
*/
public class IMU {
    private boolean active = true;
    public synchronized void setActive(boolean a) {
        this.active = a;
    }
    public synchronized boolean isActive() {
        return this.active;
    }

    public synchronized double[] readAttitude() {
        if (!this.active) {
            throw new IllegalStateException("IMU désactivée !");
        }

        // Générer une attitude fictive avec un peu de bruit
        double roll  = gaussian(0, 0.5);  // ±0.5° de bruit
        double pitch = gaussian(0, 0.5);
        double yaw   = gaussian(0, 0.5);

        return new double[] { roll, pitch, yaw };
    }
    private double gaussian(double mu, double sigma) {
        Random rand = new Random();
        return mu + sigma * rand.nextGaussian();
    }
}
