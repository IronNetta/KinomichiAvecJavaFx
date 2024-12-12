package FX;

import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class ListeEleve {
        public static void afficher(Stage parentStage, InscriptionController controller) {
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.setTitle("Liste des inscriptions");

            // Récupérer la liste des élèves
            List<Personne> eleves = controller.model.listerEleves();

            // Création d'une ListView pour afficher les élèves
            ListView<String> listView = new ListView<>();
            for (Personne eleve : eleves) {
                listView.getItems().add(eleve.toString()); // Vous pouvez personnaliser l'affichage avec eleve.getNom(), etc.
            }

            // Layout principal
            VBox root = new VBox(10);
            root.getChildren().addAll(new Label("Liste des élèves inscrits :"), listView);

            // Configuration de la scène
            Scene scene = new Scene(root, 300, 400);
            stage.setScene(scene);
            stage.show();
        }
    }