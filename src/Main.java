import FX.*;
import activite.Activite;
import activite.ActiviteController;
import activite.ActiviteView;
import inscription.Inscription;
import inscription.InscriptionController;
import inscription.InscriptionView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class Main extends Application {
    Inscription model = new Inscription();
    InscriptionView view = new InscriptionView();
    ActiviteView activiteView = new ActiviteView();
    Activite actviteModel = new Activite();
    ListView<String> listView = new ListView<>();

    ActiviteController activiteController = new ActiviteController(actviteModel, activiteView);
    InscriptionController controller = new InscriptionController(model, view, activiteController);



    @Override
    public void start(Stage primaryStage) {
        model.charger();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setTitle("Liste des inscriptions");

        List<Personne> eleves = controller.model.listerEleves();
        listView.getItems().clear();
        for (Personne eleve : eleves) {
            listView.getItems().add(eleve.toString());
        }
        VBox rootList = new VBox(10); // Espacement de 10px entre les éléments
        rootList.getChildren().addAll(listView);


            // Boutons pour les fonctionnalités
            Button btnAjouter = new Button("Ajouter une inscription");
            Button btnChercher = new Button("Rechercher un élève par son nom");
            Button btnModifier = new Button("Modifier une inscription");
            Button btnSupprimer = new Button("Supprimer une inscription");
            Button btnFermer = new Button("Quitter");

            // Actions des boutons
            btnAjouter.setOnAction(event -> ouvrirFormulaireAjout(primaryStage));
            btnChercher.setOnAction(event -> ouvrirRechercheEleve(primaryStage));
            btnModifier.setOnAction(event -> ouvrirModificationInscription(primaryStage));
            btnSupprimer.setOnAction(event -> ouvrirSuppressionInscription(primaryStage));
            btnFermer.setOnAction(event -> primaryStage.close());

            // Layout principal
            HBox rootInscription = new HBox(10); // Espacement de 10px entre les éléments
            rootInscription.getChildren().addAll(btnAjouter, btnChercher, btnModifier, btnSupprimer, btnFermer);

            Button btnActivite = new Button("Gerer une activité");
            btnActivite.setOnAction(event -> ouvrirSuppressionInscription(primaryStage));
            HBox rootActivite = new HBox(10);
            rootActivite.getChildren().addAll(btnActivite);

            VBox rootPrincipal = new VBox(10);
            rootPrincipal.getChildren().addAll(rootList, rootInscription, rootActivite);
            // Création de la scène
            Scene scene = new Scene(rootPrincipal, 900, 700);
            primaryStage.setTitle("Gestion des Inscriptions");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

    private void ouvrirFormulaireAjout(Stage parentStage) {
        Stage formulaireStage = FormulaireAjout.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            model.charger(); // Recharger les données du modèle
            mettreAJourListe(); // Rafraîchir l'interface utilisateur
        });
        System.out.println("Ouvrir le formulaire pour ajouter une inscription.");
    }


    private void ouvrirRechercheEleve(Stage parentStage) {
            RechercheEleve.afficher(parentStage,controller);
            System.out.println("Ouvrir le formulaire de recherche.");
        }

        private void ouvrirModificationInscription(Stage parentStage) {
            Stage formulaireStage = FormulaireModification.afficher(parentStage,controller);
            formulaireStage.setOnHidden(event -> {
                model.charger();
                mettreAJourListe();
            });
            System.out.println("Ouvrir le formulaire pour modifier une inscription.");
        }

        private void ouvrirSuppressionInscription(Stage parentStage) {
            Stage formulaireStage = FormulaireSuppression.afficher(parentStage,controller);
            formulaireStage.setOnHidden(event -> {
                model.charger();
                mettreAJourListe();
            });
            System.out.println("Ouvrir le formulaire pour supprimer une inscription.");
        }

        private void mettreAJourListe() {
            List<Personne> eleves = controller.model.listerEleves();
            listView.getItems().clear();
            for (Personne eleve : eleves) {
                listView.getItems().add(eleve.toString());
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }