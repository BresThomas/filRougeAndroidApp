package edu.bres.filrouge;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * Représente un produit avec ses caractéristiques telles que le nom, la description, le prix, la valeur et l'image.
 * Implémente l'interface ProduitInterface.
 * Cette classe est également Parcelable pour permettre le passage d'objets Produit entre les composants Android.
 */
public class Produit implements ProduitInterface {
    private final String name;
    private float value;
    private String description;
    private float price;
    private String picture;

    /**
     * Constructeur de la classe Produit.
     * @param name Le nom du produit.
     * @param description La description du produit.
     * @param picture L'URL de l'image du produit.
     * @param value La valeur du produit.
     * @param price Le prix du produit.
     */
    public Produit(String name, String description, String picture, float value, float price) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.value = value;
    }

    /**
     * Renvoie la description du produit.
     * @return La description du produit.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Définit le prix du produit.
     * @param price Le prix du produit.
     */
    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Renvoie le prix du produit.
     * @return Le prix du produit.
     */
    @Override
    public float getPrice() {
        return price;
    }

    /**
     * Renvoie le nom du produit avec la première lettre en majuscule.
     * @return Le nom du produit formaté.
     */
    @Override
    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Renvoie l'URL de l'image du produit.
     * @return L'URL de l'image du produit.
     */
    @Override
    public String getPicture() {
        return picture;
    }

    /**
     * Renvoie la valeur du produit.
     * @return La valeur du produit.
     */
    @Override
    public float getValue() {
        return value;
    }

    /**
     * Renvoie une représentation textuelle du produit.
     * @return Le nom du produit.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Définit la valeur du produit.
     * @param value La valeur du produit.
     */
    @Override
    public void setValue(float value) {
        this.value = value;
    }

    // Parcelable implementation
    
    /**
     * Constructeur utilisé pour la désérialisation d'un objet Produit depuis un Parcel.
     * @param in Le Parcel contenant les données sérialisées de l'objet Produit.
     */
    public Produit(Parcel in) {
        name = in.readString();
        value = in.readFloat();
        description = in.readString();
        price = in.readFloat();
        picture = in.readString();
    }

    /**
     * DescribeContents() n'est pas utilisé dans ce cas, donc la valeur renvoyée est 0.
     * @return 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Écriture de l'objet Produit dans un Parcel.
     * @param dest Le Parcel dans lequel écrire les données de l'objet Produit.
     * @param flags Les flags supplémentaires (non utilisés dans ce cas).
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(value);
        dest.writeString(description);
        dest.writeFloat(price);
        dest.writeString(picture);
    }

    /**
     * Créateur de l'objet Produit à partir d'un Parcel.
     */
    public static final Creator<Produit> CREATOR = new Creator<Produit>() {
        @Override
        public Produit createFromParcel(Parcel in) {
            return new Produit(in);
        }
        @Override
        public Produit[] newArray(int size) {
            return new Produit[size];
        }
    };

    /**
     * Renvoie le créateur Parcelable pour la classe Produit.
     * @return Le créateur Parcelable pour la classe Produit.
     */
    public static Parcelable.Creator<Produit> getCreator() {
        return CREATOR;
    }
}
