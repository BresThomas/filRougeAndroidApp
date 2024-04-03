package edu.bres.filrouge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adaptateur personnalisé pour afficher une liste de produits dans une ListView ou une GridView.
 * Cet adaptateur lie les données de type ProductShopping à une vue personnalisée pour chaque élément de la liste.
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class BascketAdapter extends BaseAdapter {

    private static final String TAG = "bres, bitoun, wallner ShoppingAdapter";
    private final List<ProductBascket> items;
    private final LayoutInflater mInflater;

    /**
     * Constructeur de l'adaptateur ShoppingAdapter.
     *
     * @param items   La liste des produits à afficher.
     * @param context Le contexte de l'application.
     */
    public BascketAdapter(List<ProductBascket> items, Context context) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem = convertView;

        // Réutilisation des layouts
        if (layoutItem == null) {
            layoutItem = mInflater.inflate(R.layout.activity_settings, parent, false);
        }

        ProductBascket currentItem = items.get(position);

        // Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.itemName);
        TextView price = layoutItem.findViewById(R.id.itemPrice);
        TextView description = layoutItem.findViewById(R.id.itemDescription);
        ImageView picture = layoutItem.findViewById(R.id.itemPicture);

        // Mise à jour des valeurs
        name.setText(currentItem.getName());
        description.setText(currentItem.getDescription());
        price.setText(String.valueOf(currentItem.getPrice()));

        // Utiliser Picasso pour charger l'image à partir de l'URL
        Picasso.get().load(currentItem.getPictureLowQuality()).into(picture);

        // On retourne l'item créé
        return layoutItem;
    }
}
