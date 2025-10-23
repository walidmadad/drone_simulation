package ut2j.m1.iceld.sensors;

/* --- Lidar : distance au plus proche obstacle ---
Objectifs :
 - Comprendre détection obstacles
 - Gérer capteur actif/inactif
*/
public class Lidar {
    private boolean active = true;

    public synchronized void setActive(boolean a) {
        this.active = a;
    }

    public synchronized boolean isActive() {
        return this.active;
    }
    public synchronized double readNearestObstacle() {
        // Commentaire : Double.POSITIVE_INFINITY si aucun obstacle
        if (!this.active) {
            throw new IllegalStateException("Lidar désactivé !");
        }

        // Générer une distance aléatoire entre 0.2 m et 10 m (par exemple)
        double distance = Math.random() * 10.0; // 0.0 à 10.0 m

        // Simuler qu’il n’y a pas toujours un obstacle (10 % de chance)
        if (Math.random() < 0.1) {
            return Double.POSITIVE_INFINITY; // aucun obstacle détecté
        }

        return distance;
    }
}
