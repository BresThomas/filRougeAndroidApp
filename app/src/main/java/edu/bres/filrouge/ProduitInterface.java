package edu.bres.filrouge;

import android.os.Parcelable;
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