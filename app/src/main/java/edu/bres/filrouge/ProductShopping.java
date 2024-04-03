package edu.bres.filrouge;

import java.util.Map;

/**
 * La classe ProduitPanier représente un produit dans le panier de l'utilisateur.
 * Chaque produit possède un identifiant unique, un nom, une description, une valeur, et des images en basse et haute définition.
 * Le nom et la description peuvent dépendre de la langue sélectionnée dans les paramètres de l'application.
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 *
 */
public class ProductShopping {
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private int id;
    private boolean isFavorite;
    private Map<String, String> name; // Dépend de la langue des paramètres
    private Map<String, String>  description;  // Dépend de la langue des paramètres
    private float rating;
    private String pictureLowQuality;
    private String pictureHighQuality;
    private float price;


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

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPictureLowQuality() {
        return pictureLowQuality;
    }
    public void setPictureLowQuality(String pictureLowQuality) {
        this.pictureLowQuality = "http://edu.info06.net/onepiece"
                + "/pictures_ld/"
                + pictureLowQuality;
    }

    public String getPictureHighQuality() {
        return pictureHighQuality;
    }
    public void setPictureHighQuality(String pictureHighQuality) {
        this.pictureHighQuality = pictureHighQuality;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return getName() + "(" +  getRating() +  ")";
    }
}
