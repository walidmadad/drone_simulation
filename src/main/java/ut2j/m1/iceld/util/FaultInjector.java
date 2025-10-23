package ut2j.m1.iceld.util;

/* --- FaultInjector : injecte des fautes pour tests ---
Objectifs :
 - Tester basculement capteurs
 - Vérifier robustesse
*/

public class FaultInjector {
    public enum Fault { GPS_OFF, GPS_NOISE_HIGH, IMU_OFF }
    private java.util.Set<Fault> active = new java.util.HashSet<>();
    public synchronized void inject(Fault f) {
        active.add(f);
    }
    public synchronized void clear(Fault f) {
        active.remove(f);
    }
    public synchronized boolean isInjected(Fault f) {
        return active.contains(f);
    }
}
