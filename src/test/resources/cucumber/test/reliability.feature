Feature: Fiabilité et basculement
  # Objectif : tester basculement automatique vers GPS secondaire

  Scenario: Basculement GPS secondaire lors d'une panne
    Given le GPS principal est actif
    # Créer simulateur standard
    And la mission est démarrée
    # Préparer mission
    When une panne GPS est injectée
    # Injecter faute GPS_OFF et désactiver GPS primaire
    Then le GPS secondaire doit être actif et la mission continuer
    # Exécuter mission et vérifier GPS secondaire actif et distance raisonnable
