import FX.FormulaireAjout;
import FX.FormulaireModification;
import FX.FormulaireSuppression;
import FX.ListeEleve;
import activite.Activite;
import activite.ActiviteController;
import activite.ActiviteView;
import inscription.Inscription;
import inscription.InscriptionController;
import inscription.InscriptionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    Inscription model = new Inscription();
    InscriptionView view = new InscriptionView();
    ActiviteView activiteView = new ActiviteView();
    Activite actviteModel = new Activite();

    ActiviteController activiteController = new ActiviteController(actviteModel, activiteView);
    InscriptionController controller = new InscriptionController(model, view, activiteController);



    @Override
    public void start(Stage primaryStage) {
            // Boutons pour les fonctionnalités
            Button btnAjouter = new Button("Ajouter une inscription");
            Button btnLister = new Button("Lister les inscriptions");
            Button btnModifier = new Button("Modifier une inscription");
            Button btnSupprimer = new Button("Supprimer une inscription");

            // Actions des boutons
            btnAjouter.setOnAction(event -> ouvrirFormulaireAjout(primaryStage));
            btnLister.setOnAction(event -> ouvrirListeInscriptions(primaryStage));
            btnModifier.setOnAction(event -> ouvrirModificationInscription(primaryStage));
            btnSupprimer.setOnAction(event -> ouvrirSuppressionInscription(primaryStage));

            // Layout principal
            VBox root = new VBox(10); // Espacement de 10px entre les éléments
            root.getChildren().addAll(btnAjouter, btnLister, btnModifier, btnSupprimer);

            // Création de la scène
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Gestion des Inscriptions");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void ouvrirFormulaireAjout(Stage parentStage) {
            FormulaireAjout.afficher(parentStage,controller);
            System.out.println("Ouvrir le formulaire pour ajouter une inscription.");
        }

        private void ouvrirListeInscriptions(Stage parentStage) {
            model.charger();
            ListeEleve.afficher(parentStage,controller);
            System.out.println("Ouvrir le formulaire pour lister les inscription.");

        }

        private void ouvrirModificationInscription(Stage parentStage) {
            // Logique pour ouvrir la fenêtre de modification
            FormulaireModification.afficher(parentStage,controller);
            System.out.println("Ouvrir le formulaire pour modifier une inscription.");
        }

        private void ouvrirSuppressionInscription(Stage parentStage) {
            // Logique pour ouvrir la fenêtre de suppression
            FormulaireSuppression.afficher(parentStage,controller);
            System.out.println("Ouvrir le formulaire pour supprimer une inscription.");
        }

        public static void main(String[] args) {
            launch(args);
        }
    }