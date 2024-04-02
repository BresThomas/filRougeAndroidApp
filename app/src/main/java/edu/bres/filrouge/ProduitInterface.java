package edu.bres.filrouge;

import android.os.Parcelable;

/**
 * L'interface ProduitInterface représente un produit avec plusieurs attributs tels que son nom, sa description, son image, son prix et sa valeur.
 * Cette interface étend Parcelable pour permettre le passage d'objets de ce type entre les composants Android.
 */
public interface ProduitInterface extends Parcelable{
    String getDescription();
    String getName();
    String getPicture();
    float getPrice();
    float getValue();
    @Override
    String toString();
    void setValue(float value);

    void setPrice(float price);
}