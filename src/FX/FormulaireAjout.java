package FX;

import activite.Activite;
import club.Club;
import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

public class FormulaireAjout {

    public static Stage afficher(Stage parentStage, InscriptionController controller) {
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

        ComboBox<String> comboClubs = new ComboBox<>();
        comboClubs.getItems().addAll(controller.getClubs().stream()
                .map(Club::getClubName).toList());
        comboClubs.setPromptText("Choisir un club");

        Button btnAjouterClub = new Button("Ajouter un nouveau club");
        btnAjouterClub.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter un Club");
            dialog.setHeaderText("Nouveau club");
            dialog.setContentText("Nom du club :");
            dialog.showAndWait().ifPresent(nomClub -> {
                if (controller.getClubs().stream().noneMatch(club -> club.getClubName().equalsIgnoreCase(nomClub))) {
                    controller.ajouterClub(nomClub);
                    comboClubs.getItems().add(nomClub);
                } else {
                    // Message si le club existe déjà
                    new Alert(Alert.AlertType.INFORMATION, "Le club existe déjà.").showAndWait();
                }
            });
        });

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        CheckBox chkPaiement = new CheckBox("Paiement en cours");

        // Ajout des activités
        ComboBox<String> comboActivites = new ComboBox<>();
        comboActivites.getItems().addAll(controller.getActivites().stream()
                .map(Activite::getNom).toList());
        comboActivites.setPromptText("Choisir une activité");

        Button btnAjouterActivite = new Button("Ajouter une nouvelle activité");
        btnAjouterActivite.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter une Activité");
            dialog.setHeaderText("Nouvelle activité");
            dialog.setContentText("Nom de l'activité :");
            dialog.showAndWait().ifPresent(nomActivite -> {
                if (controller.getActivites().stream().noneMatch(activite -> activite.getNom().equalsIgnoreCase(nomActivite))) {
                    Activite nouvelleActivite = new Activite(nomActivite, 0, false, false, false); // Initialisation basique
                    controller.ajouterActivite(nouvelleActivite);
                    comboActivites.getItems().add(nomActivite);
                } else {
                    // Message si l'activité existe déjà
                    new Alert(Alert.AlertType.INFORMATION, "L'activité existe déjà.").showAndWait();
                }
            });
        });

        // Bouton de soumission
        Button btnSubmit = new Button("Ajouter");
        btnSubmit.setOnAction(event -> {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String club = comboClubs.getValue();
            String email = txtEmail.getText();
            boolean paiement = chkPaiement.isSelected();
            String activiteSelectionnee = comboActivites.getValue();

            if (nom.isEmpty() || prenom.isEmpty() || club == null || email.isEmpty() || activiteSelectionnee == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Tous les champs doivent être remplis !");
                alert.showAndWait();
            } else {
                // Créez l'objet Personne et passez-le au contrôleur
                Personne nouvelEleve = new Personne(nom, prenom, club, email, paiement);

                // Ajouter l'activité sélectionnée à l'inscription de l'élève
                Activite activite = controller.rechercherActivite(activiteSelectionnee);
                if (activite != null) {
                    nouvelEleve.ajouterActivite(activite);
                }

                controller.ajouterEleve(nouvelEleve);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève ajouté avec succès !");
                alert.showAndWait();

                // Fermer la fenêtre
                stage.close();
            }
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Ajouter une inscription"),
                txtNom, txtPrenom, comboClubs, btnAjouterClub, txtEmail, chkPaiement,
                comboActivites, btnAjouterActivite, btnSubmit
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 350, 350);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}

