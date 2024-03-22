package edu.bres.filrouge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.bres.filrouge.MainActivity;
import edu.bres.filrouge.Produit;

public class ProduitAdapter extends BaseAdapter {

    private static final String TAG = "Bres, Bitoun, Wallner";
    private final List<ProduitInterface> items;
    private String url;
    private final LayoutInflater mInflater;
    public ProduitAdapter(List<ProduitInterface> items, Context context) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return items.size();
    }
    public Object getItem(int position) {
        return items.get(position);
    }
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
        TextView titre = layoutItem.findViewById(R.id.titreProduit);
        TextView description = layoutItem.findViewById(R.id.descriptionProduit);
        ImageView picture = layoutItem.findViewById(R.id.imageProduit);
        //(3) : Mise à jour des valeurs
        titre.setText(items.get(position).getName());
        description.setText(items.get(position).getName());
        //picture.setImageResource(items.get(position).getPicture());

        Log.d(TAG, "image" + items.get(position).getPicture());
        Picasso.get().load(items.get(position).getPicture()).into(picture);

        //(4) écouteur sur chaque élément de l'adapter
        //On retourne l'item créé.
        return layoutItem;
    }
}