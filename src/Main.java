import FX.FormulaireAjout;
import FX.FormulaireModification;
import FX.FormulaireSuppression;
import FX.RechercheEleve;
import activite.Activite;
import activite.ActiviteController;
import activite.ActiviteView;
import inscription.Inscription;
import inscription.InscriptionController;
import inscription.InscriptionView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import personne.Personne;

import java.util.Objects;

public class Main extends Application {
    Inscription model = new Inscription();
    InscriptionView view = new InscriptionView();
    ActiviteView activiteView = new ActiviteView();
    Activite activiteModel = new Activite();
    TableView<Personne> tableView = new TableView<>();

    ActiviteController activiteController = new ActiviteController(activiteModel, activiteView);
    InscriptionController controller = new InscriptionController(model, view, activiteController);

    @Override
    public void start(Stage primaryStage) {
        model.charger();

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Kinomichi_Logo.gif")));
        primaryStage.getIcons().add(logo);

        // Configuration des colonnes du tableau
        TableColumn<Personne, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Personne, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Personne, Integer> clubCol = new TableColumn<>("Club");
        clubCol.setCellValueFactory(new PropertyValueFactory<>("club"));

        TableColumn<Personne, Integer> mailCol = new TableColumn<>("Mail");
        mailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));

        TableColumn<Personne, Integer> PayementCol = new TableColumn<>("Payement En Cours");
        PayementCol.setCellValueFactory(new PropertyValueFactory<>("payemmentEnCours"));

        TableColumn<Personne, Integer> activitesCol = new TableColumn<>("Activites");
        activitesCol.setCellValueFactory(new PropertyValueFactory<>("activites"));

        tableView.getColumns().addAll(nomCol, prenomCol, clubCol,mailCol,PayementCol,activitesCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Remplir le tableau avec les données
        mettreAJourTable();

        VBox rootTable = new VBox(10);
        rootTable.getChildren().add(tableView);

        // Boutons pour les fonctionnalités
        Button btnAjouter = new Button("Ajouter une inscription");
        Button btnChercher = new Button("Rechercher un élève par son nom");
        Button btnModifier = new Button("Modifier une inscription");
        Button btnSupprimer = new Button("Supprimer une inscription");

        btnAjouter.setOnAction(event -> ouvrirFormulaireAjout(primaryStage));
        btnChercher.setOnAction(event -> ouvrirRechercheEleve(primaryStage));
        btnModifier.setOnAction(event -> ouvrirModificationInscription(primaryStage));
        btnSupprimer.setOnAction(event -> ouvrirSuppressionInscription(primaryStage));

        HBox rootButtons = new HBox(10);
        rootButtons.getChildren().addAll(btnAjouter, btnChercher, btnModifier, btnSupprimer);

        VBox rootPrincipal = new VBox(10);
        rootPrincipal.getChildren().addAll(rootTable, rootButtons);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(rootPrincipal, screenBounds.getWidth()-50, screenBounds.getHeight()-50);
        primaryStage.setTitle("Gestion des Inscriptions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mettreAJourTable() {
        ObservableList<Personne> personnes = FXCollections.observableArrayList(controller.model.listerEleves());
        tableView.setItems(personnes);
    }

    private void ouvrirFormulaireAjout(Stage parentStage) {
        Stage formulaireStage = FormulaireAjout.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            model.charger(); // Recharger les données du modèle
            mettreAJourTable(); // Rafraîchir le tableau
        });
    }

    private void ouvrirRechercheEleve(Stage parentStage) {
        RechercheEleve.afficher(parentStage, controller);
    }

    private void ouvrirModificationInscription(Stage parentStage) {
        Stage formulaireStage = FormulaireModification.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirSuppressionInscription(Stage parentStage) {
        Stage formulaireStage = FormulaireSuppression.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            model.charger();
            mettreAJourTable();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
