package ut2j.m1.iceld.sensors;

/* --- GPS : capteur de position avec bruit ---
 Objectifs :
 - Comprendre lecture capteur avec bruit
 - Gestion activation / panne simulée
*/

import java.util.Random;

public class GPS {
    private double lat; // latitude interne simulée
    private double lon; // longitude interne simulée
    private boolean active = true; // état actif/inactif
    private double noiseStdMeters = 0.05; // bruit par défaut en mètres (5 cm)

    // Initialiser le GPS avec position de départ
    public GPS(double initLat, double initLon) {
        this.lat = initLat;
        this.lon = initLon;
    }

    // Activer ou désactiver le GPS
    public synchronized void setActive(boolean a) {
        this.active = a;
    }

    // Vérifier si GPS actif
    public synchronized boolean isActive() {
        return this.active;
    }

    // Modifier écart type du bruit
    public synchronized void setNoiseStdMeters(double std) {
        this.noiseStdMeters = std;
    }

    // Lire position GPS avec bruit
    public synchronized double[] readPosition() {
        // vérifier GPS actif
        if (!this.active) {
            // Si GPS inactif, on peut retourner null ou une position invalide
            return null;
        }
        // générer bruit gaussien
        double noiseLatMeters = gaussian(0, noiseStdMeters);
        double noiseLonMeters = gaussian(0, noiseStdMeters);

        // convertir mètres -> degrés
        double noiseLatDeg = noiseLatMeters / 111320.0; // degré de latitude ≈ 111 320 mètres
        double metersPerDegLon = 111320.0 * Math.cos(Math.toRadians(lat));
        double noiseLonDeg = noiseLonMeters / metersPerDegLon;

        double noisyLat = this.lat + noiseLatDeg;
        double noisyLon = this.lon + noiseLonDeg;

        // retourner nouvelle position lat/lon
        return new double[] { noisyLat, noisyLon };
    }

    // Mettre à jour position interne (utile pour simulation)
    public synchronized void setPosition(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    // Générateur gaussien pour bruit
    private double gaussian(double mu, double sigma) {
        Random rand = new Random();
        return mu + sigma * rand.nextGaussian();
    }
}
