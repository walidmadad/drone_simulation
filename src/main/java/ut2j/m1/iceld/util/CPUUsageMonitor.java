package ut2j.m1.iceld.util;

import java.util.Random;

/* --- CPUUsageMonitor : mesure utilisation CPU ---
Objectifs :
 - Comprendre effet charge sur planification
 - Ajouter jitter pour réalisme
*/
public class CPUUsageMonitor {
    private double simulatedUsagePercent = 1.0;

    public synchronized void setSimulatedLoad(double percent) {
        // Vérifier que la valeur est comprise entre 0 et 100.
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;

        // Mettre à jour la variable simulatedUsagePercent.
        this.simulatedUsagePercent = percent;
    }

    public synchronized double measureUsage() {
        // Créer un générateur aléatoire (Random).
        Random rand = new Random();

        // Générer un bruit gaussien (jitter)
        double jitter = rand.nextGaussian() * 0.5; // bruit ~ ±0.5%

        // Ajouter ce bruit à simulatedUsagePercent.
        double usage = simulatedUsagePercent + jitter;

        // Retourner la valeur finale bornée entre 0 et 100.
        if (usage < 0) usage = 0;
        if (usage > 100) usage = 100;

        return usage;
    }
}
