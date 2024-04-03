package edu.bres.filrouge;


import android.content.Context;

/**
 * Interface pour écouter les évènements sur le nom du diplome
 *
 *  * @author [Bitoun, Bres, Wallner] - March 2024
 */

    public interface Clickable {
        void onClicItem(int itemIndex);
        void onRatingBarChange(int itemIndex, float value);
        Context getContext();
}
