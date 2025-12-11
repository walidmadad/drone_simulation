# DroneSimulator

## Description
Ce projet simule le comportement d’un drone en utilisant différents capteurs (GPS, IMU, Lidar) et un planificateur de mission.  
Il inclut des scénarios de test automatisés pour vérifier la **qualité**, la **fiabilité**, la **performance**, et l’**utilité** du système.

Les tests sont définis avec **Cucumber (Gherkin)** et implémentés en Java avec **JUnit 5**.

---

## Prérequis
- Java 11 ou supérieur
- Maven 3.6 ou supérieur
- IDE compatible Java (IntelliJ, Eclipse, VS Code)
- Terminal pour exécuter Maven ou IDE pour lancer les tests


## Compilation du projet
Si vous avez déjà le projet sur votre machine :

1. Ouvrir un terminal dans le dossier racine du projet (où se trouve le `pom.xml`).
2. Compiler le projet :
```bash
mvn clean compile
```
3. Installer les dépendances :
```bash
mvn install
```

Exécution des tests

Pour lancer tous les tests Cucumber :
```bash
mvn test
```

Pour exécuter un fichier feature spécifique :

Les résultats des tests seront affichés dans la console et peuvent générer un rapport HTML si configuré dans Maven.



