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

import java.text.DecimalFormat;
import java.util.List;

public class ProduitAdapter extends BaseAdapter {

    private static final String TAG = "Bres, Bitoun, Wallner";
    private final List<ProduitInterface> items;
    private final LayoutInflater mInflater;
    private Clickable callBackActivity;

    public ProduitAdapter(List<ProduitInterface> items, Clickable callBackActivity, Context context) {
        this.items = items;
        this.callBackActivity = callBackActivity;
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
        TextView name = layoutItem.findViewById(R.id.titreProduit);
        TextView grade = layoutItem.findViewById(R.id.descriptionProduit);
        ImageView picture = layoutItem.findViewById(R.id.imageProduit);

        //(3) : Mise à jour des valeurs
        name.setText(items.get(position).getName());
        grade.setText((new DecimalFormat("##.##")).format(items.get(position).getValue()));
        //picture.setImageResource(items.get(position).getPicture());
        Picasso.get().load(items.get(position).getPicture()).into(picture);

        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener(click -> callBackActivity.onClicItem(position));

        //On retourne l'item créé.
        return layoutItem;
    }
}
