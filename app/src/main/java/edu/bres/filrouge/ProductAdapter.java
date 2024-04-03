package edu.bres.filrouge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adaptateur personnalisé pour afficher une liste de produits dans une ListView ou une GridView.
 * Cet adaptateur lie les données de type ProduitInterface à une vue personnalisée pour chaque élément de la liste.
 * Il utilise Picasso pour charger les images des produits à partir de leur URL.
 * L'activité appelante doit implémenter l'interface Clickable pour gérer les clics sur les éléments de la liste.
 * 
 * @see ProductInterface
 * @see Clickable
 */
public class ProductAdapter extends BaseAdapter {

    private static final String TAG = "Bres, Bitoun, Wallner";
    private final List<ProductInterface> items;
    private final LayoutInflater mInflater;
    private Clickable callBackActivity;

    /**
     * Constructeur de l'adaptateur ProduitAdapter.
     * 
     * @param items La liste des produits à afficher.
     * @param callBackActivity L'activité appelante qui implémente l'interface Clickable.
     * @param context Le contexte de l'application.
     */
    public ProductAdapter(List<ProductInterface> items, Clickable callBackActivity, Context context) {
        this.items = items;
        this.callBackActivity = callBackActivity;
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
    public View getView(int position, View view, ViewGroup parent) {
        View layoutItem;

        // Réutilisation des layouts 
        layoutItem = (view == null
                ? mInflater.inflate(R.layout.list_item_produit, parent, false)
                : view);

        // Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.itemTitle);
        TextView price = layoutItem.findViewById(R.id.itemPrice);
        TextView rating = layoutItem.findViewById(R.id.noteRatting);
        ImageView picture = layoutItem.findViewById(R.id.itemPicture);

        // Mise à jour des valeurs
        name.setText(items.get(position).getName());
        price.setText(String.format(Locale.getDefault(), "%.2f €", items.get(position).getPrice()));
        rating.setText((new DecimalFormat("##.##")).format(items.get(position).getRating()));
        Picasso.get().load(items.get(position).getPicture()).into(picture);

        // écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener(click -> callBackActivity.onClicItem(position));

        //On retourne l'item créé.
        return layoutItem;
    }
}
