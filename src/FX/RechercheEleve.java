package FX;

import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class RechercheEleve {
    public static void afficher(Stage parentStage, InscriptionController controller) {
        // Vérifiez que le contrôleur est valide
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le contrôleur est null. Impossible de continuer.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Supprimer un élève inscrit");

        // Récupérer la liste des élèves
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève à supprimer.");
            alert.showAndWait();
            return;
        }

        // Liste déroulante pour sélectionner un élève
        ComboBox<Personne> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(eleves);
        comboBox.setPromptText("Sélectionnez un élève");

        // Champs de modification
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");

        TextField txtClub = new TextField();
        txtClub.setPromptText("Club");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        CheckBox chkPaiement = new CheckBox("Paiement en cours");

        comboBox.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne != null) {
                txtNom.setText(eleveSelectionne.getNom());
                txtPrenom.setText(eleveSelectionne.getPrenom());
                txtClub.setText(eleveSelectionne.getClub());
                txtEmail.setText(eleveSelectionne.getMail());
                chkPaiement.setSelected(eleveSelectionne.isPayemmentEnCours());
            }
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Charcher un élève"),
                comboBox,
                txtNom,
                txtPrenom,
                txtClub,
                txtEmail,
                chkPaiement
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
