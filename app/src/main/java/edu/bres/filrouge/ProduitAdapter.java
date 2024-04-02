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
 * @see ProduitInterface
 * @see Clickable
 */
public class ProduitAdapter extends BaseAdapter {

    private static final String TAG = "Bres, Bitoun, Wallner";
    private final List<ProduitInterface> items;
    private final LayoutInflater mInflater;
    private Clickable callBackActivity;

    /**
     * Constructeur de l'adaptateur ProduitAdapter.
     * 
     * @param items La liste des produits à afficher.
     * @param callBackActivity L'activité appelante qui implémente l'interface Clickable.
     * @param context Le contexte de l'application.
     */
    public ProduitAdapter(List<ProduitInterface> items, Clickable callBackActivity, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        //(1) : Réutilisation des layouts (lorsque c'est possible)
        layoutItem = (convertView == null
                ? mInflater.inflate(R.layout.list_item_produit, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.titreProduit);
        TextView price = layoutItem.findViewById(R.id.produitPrice);
        TextView grade = layoutItem.findViewById(R.id.value);
        ImageView picture = layoutItem.findViewById(R.id.imageProduit);

        //(3) : Mise à jour des valeurs
        name.setText(items.get(position).getName());
        price.setText(String.format(Locale.getDefault(), "%.2f €", items.get(position).getPrice())); // Mettez à jour le TextView avec le prix
        grade.setText((new DecimalFormat("##.##")).format(items.get(position).getValue()));
        Picasso.get().load(items.get(position).getPicture()).into(picture);

        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener(click -> callBackActivity.onClicItem(position));

        //On retourne l'item créé.
        return layoutItem;
    }
}
