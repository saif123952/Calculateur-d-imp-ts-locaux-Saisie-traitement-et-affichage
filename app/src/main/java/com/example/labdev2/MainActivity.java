package com.example.labdev2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Classe principale pour l'application de calcul des taxes d'habitation.
 * Ce module traite les données de surface, de pièces et d'équipements.
 */
public class MainActivity extends AppCompatActivity {

    // Champs de saisie utilisateur
    private EditText champSaisieSurface;
    private EditText champSaisieNbPieces;
    private CheckBox optionPiscineSelection;
    private TextView afficheurResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Liaison avec le fichier de mise en page
        setContentView(R.layout.activity_main);

        // Initialisation des composants graphiques
        lierComposantsGraphiques();

        Button boutonCalculer = findViewById(R.id.btn_run_calculation);
        boutonCalculer.setOnClickListener(view -> executerEstimationFiscale());
    }

    /**
     * Récupère les références des éléments définis dans le XML.
     */
    private void lierComposantsGraphiques() {
        champSaisieSurface = findViewById(R.id.edit_surface_m2);
        champSaisieNbPieces = findViewById(R.id.edit_nb_pieces);
        optionPiscineSelection = findViewById(R.id.cb_has_piscine);
        afficheurResultat = findViewById(R.id.label_resultat_estimation);
    }

    /**
     * Méthode principale effectuant le traitement des données.
     */
    private void executerEstimationFiscale() {
        String inputSurface = champSaisieSurface.getText().toString().trim();
        String inputPieces = champSaisieNbPieces.getText().toString().trim();

        // Vérification de la validité des champs
        if (inputSurface.isEmpty() || inputPieces.isEmpty()) {
            Toast.makeText(this, R.string.erreur_saisie, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double surfaceMetres = Double.parseDouble(inputSurface);
            int nombreDePieces = Integer.parseInt(inputPieces);
            boolean presencePiscine = optionPiscineSelection.isChecked();

            // Algorithme de calcul :
            // 2 DH par m² + 50 DH par pièce + majoration de 100 DH pour piscine
            double coutSurface = surfaceMetres * 2.0;
            double coutPieces = nombreDePieces * 50.0;
            double forfaitPiscine = presencePiscine ? 100.0 : 0.0;

            double totalFacture = coutSurface + coutPieces + forfaitPiscine;

            // Construction du message de sortie
            String renduFinal = getString(R.string.resultat_prefixe) 
                    + String.format(Locale.FRANCE, "%.2f", totalFacture) 
                    + getString(R.string.unite_monetaire);
            
            afficheurResultat.setText(renduFinal);

        } catch (NumberFormatException e) {
            afficheurResultat.setText(R.string.erreur_saisie);
        }
    }
}
