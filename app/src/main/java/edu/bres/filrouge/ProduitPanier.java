package edu.bres.filrouge;

import android.util.Log;

import java.util.Map;

/**
 * La classe ProduitPanier représente un produit dans le panier de l'utilisateur.
 * Chaque produit possède un identifiant unique, un nom, une description, une valeur, et des images en basse et haute définition.
 * Le nom et la description peuvent dépendre de la langue sélectionnée dans les paramètres de l'application.
 */
public class ProduitPanier {
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private int id;
    private boolean isFavorite;
    private Map<String, String> name; // Dépend de la langue des paramètres
    private Map<String, String>  description;  // Dépend de la langue des paramètres
    private float value;
    private String pictureLowDefinition;
    private String pictureHighDefinition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public String getName() {
        return name.get(VenteApp.getContext().getString(R.string.LANGUAGE));
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public String getDescription() {
        return description.get(VenteApp.getContext().getString(R.string.LANGUAGE));
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public String getPictureLowDefinition() {
        return pictureLowDefinition;
    }
    public void setPictureLowDefinition(String pictureLowDefinition) {
        this.pictureLowDefinition = "http://edu.info06.net/onepiece"
                + "/pictures_ld/"
                + pictureLowDefinition;
    }

    public String getPictureHighDefinition() {
        return pictureHighDefinition;
    }
    public void setPictureHighDefinition(String pictureHighDefinition) {
        this.pictureHighDefinition = pictureHighDefinition;
    }

    @Override
    public String toString(){
        return getName() + "(" +  getValue() +  ")";
    }
}
