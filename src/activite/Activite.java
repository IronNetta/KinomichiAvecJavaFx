package activite;

import java.io.Serializable;

public class Activite implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nom;
    private int heuresStage;
    private boolean logement;
    private boolean repasSoir;
    private boolean estWeekend;

    public Activite() {
    }

    public Activite(String nom, int heuresStage, boolean logement, boolean repasSoir, boolean estWeekend) {
        this.nom = nom;
        this.heuresStage = heuresStage;
        this.logement = logement;
        this.repasSoir = repasSoir;
        this.estWeekend = estWeekend;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getHeuresStage() { return heuresStage; }
    public void setHeuresStage(int heuresStage) { this.heuresStage = heuresStage; }

    public boolean isLogement() { return logement; }
    public void setLogement(boolean logement) { this.logement = logement; }

    public boolean isRepasSoir() { return repasSoir; }
    public void setRepasSoir(boolean repasSoir) { this.repasSoir = repasSoir; }

    public boolean isEstWeekend() { return estWeekend; }
    public void setEstWeekend(boolean estWeekend) { this.estWeekend = estWeekend; }

    @Override
    public String toString() {
        return "Activite{" +
                "nom='" + nom + '\'' +
                ", heuresStage=" + heuresStage +
                ", logement=" + logement +
                ", repasSoir=" + repasSoir +
                ", estWeekend=" + estWeekend +
                '}';
    }
}

