package edu.bres.filrouge;

import android.app.Application;
import android.content.Context;

/**
 * La classe VenteApp est une sous-classe d'Application qui permet de stocker le contexte de l'application.
 * Elle est utilisée pour obtenir le contexte de l'application à partir de n'importe quelle classe dans l'application.
 */
public class VenteApp extends Application {
    private static Context context;

    /**
     * Méthode appelée lors de la création de l'application.
     * Elle initialise le contexte de l'application.
     */
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * Renvoie le contexte de l'application.
     *
     * @return Le contexte de l'application.
     */
    public static Context getContext() {
        return context;
    }
}
