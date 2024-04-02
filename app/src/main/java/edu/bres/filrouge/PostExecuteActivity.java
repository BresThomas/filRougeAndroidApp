package edu.bres.filrouge;

import java.util.List;

/**
 * Interface pour les activités exécutant du code après l'achèvement d'une tâche asynchrone.
 * @param <T> Le type d'élément sur lequel la tâche asynchrone agit.
 */

public interface PostExecuteActivity<T> {
    void onPostExecute(List<T> itemList);
    void runOnUiThread(Runnable runable);
}
