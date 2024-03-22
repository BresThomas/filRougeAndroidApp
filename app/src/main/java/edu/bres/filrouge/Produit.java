package edu.bres.filrouge;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
public class Produit implements ProduitInterface {
    private final String name;
    private float value;
    private String description;
    private boolean isFavorite;
    private String picture;
    public Produit(String name, String description, String picture) {
        this.name = name;
        this.isFavorite = false;
        this.picture = picture;
        this.description = description;
        value = (name.charAt(0) / name.length() / 3) / 2.0f;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    @Override
    public boolean isFavorite() {
        return isFavorite;
    }
    @Override
    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    @Override
    public String getPicture() {
        return picture;
    }
    @Override
    public float getValue() {
        return value;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public void setValue(float value) {
        this.value = value;
    }
    // Parcelable implementation
    public Produit(Parcel in) {
        name = in.readString();
        value = in.readFloat();
        description = in.readString();
        isFavorite = in.readByte() != 0;
        picture = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(value);
        dest.writeString(description);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(picture);
    }
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
    public static Parcelable.Creator<Produit> getCreator() {
        return CREATOR;
    }
}