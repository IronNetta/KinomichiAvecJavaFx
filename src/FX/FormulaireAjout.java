package FX;

import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

public class FormulaireAjout {

    public static void afficher(Stage parentStage, InscriptionController controller) {
        // Création de la fenêtre
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Ajouter une inscription");

        // Champs du formulaire
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");

        TextField txtClub = new TextField();
        txtClub.setPromptText("Club");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        CheckBox chkPaiement = new CheckBox("Paiement en cours");

        // Bouton de soumission
        Button btnSubmit = new Button("Ajouter");
        // Dans FX.FormulaireAjout, lorsqu'on soumet le formulaire
        btnSubmit.setOnAction(event -> {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String club = txtClub.getText();
            String email = txtEmail.getText();
            boolean paiement = chkPaiement.isSelected();

            if (nom.isEmpty() || prenom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nom et prénom doivent être remplis !");
                alert.showAndWait();
            } else {
                // Créez l'objet Personne et passez-le au contrôleur
                Personne nouvelEleve = new Personne(nom, prenom, club, email, paiement);
                controller.ajouterEleve(nouvelEleve);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève ajouté avec succès !");
                alert.showAndWait();

                // Fermer la fenêtre
                stage.close();
            }
        });
        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Ajouter une inscription"), txtNom, txtPrenom, txtClub, txtEmail, chkPaiement, btnSubmit);

        // Configuration de la scène
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
}
