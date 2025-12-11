Feature: Performance de calcul
  # Objectif : vérifier utilisation CPU lors de planification

  Scenario: Calcul de trajectoire sous seuil CPU
    Given le simulateur démarre avec charge simulée
    # Créer simulateur et configurer charge CPU initiale
    When la planification de trajectoire est lancée
    # Exécuter planification courte et mesurer CPU
    Then l'utilisation CPU mesurée doit rester < 5.0
    # Vérifier que CPU simulé < seuil
