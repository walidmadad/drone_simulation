Feature: Exactitude du positionnement
  # Objectif : vérifier que le drone atteint la cible avec précision <= 0.2 m
  Scenario: Drone atteint la cible GPS dans la tolérance de 20cm
    Given une cible GPS fixée à latitude 43.6045 and longitude 1.4440
    # Initialiser simulateur proche de la cible et configurer GPS principal avec, faible bruit
    When la mission est exécutée
    # Exécuter mission et récupérer distance finale
    Then la distance finale doit être <= 0.2 metre
    # Vérifier distance finale mesurée par le simulateur