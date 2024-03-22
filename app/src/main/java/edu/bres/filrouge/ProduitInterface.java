package edu.bres.filrouge;

import android.os.Parcelable;
public interface ProduitInterface extends Parcelable{
    String getDescription();
    void setFavorite(boolean favorite);
    boolean isFavorite();
    String getName();
    String getPicture();
    float getValue();
    @Override
    String toString();
    void setValue(float value);
}