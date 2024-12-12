package FX;

import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class FormulaireModification {
    public static void afficher(Stage parentStage, InscriptionController controller) {
        // Vérifiez que le contrôleur est valide
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le contrôleur est null. Impossible de continuer.");
            alert.showAndWait();
            return;
        }

        // Création de la fenêtre
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Modifier une inscription");

        // Récupérer la liste des élèves pour sélection
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève à modifier.");
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

        // Pré-remplir les champs lorsqu'un élève est sélectionné
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

        // Bouton de soumission
        Button btnSubmit = new Button("Modifier");
        btnSubmit.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un élève à modifier.");
                alert.showAndWait();
                return;
            }

            // Mise à jour des informations de l'élève
            eleveSelectionne.setNom(txtNom.getText());
            eleveSelectionne.setPrenom(txtPrenom.getText());
            eleveSelectionne.setClub(txtClub.getText());
            eleveSelectionne.setMail(txtEmail.getText());
            eleveSelectionne.setPayemmentEnCours(chkPaiement.isSelected());

            // Sauvegarde via le contrôleur
            controller.model.sauvegarder();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève modifié avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre
            stage.close();
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Modifier une inscription"),
                comboBox,
                txtNom,
                txtPrenom,
                txtClub,
                txtEmail,
                chkPaiement,
                btnSubmit
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();
    }
}