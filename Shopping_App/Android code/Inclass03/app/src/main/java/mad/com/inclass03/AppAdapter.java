package mad.com.inclass03;

/***
 * Name: Sridevi Rachakulla
 * Assignment: InClass6
 * **/

import android.widget.ArrayAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class AppAdapter extends ArrayAdapter<Products> {
    List<Products> mbjects;
    Context mcontext;
    int res;
    public AppAdapter(MainActivity context, int resource, List<Products> objects) {
        super(context, resource, objects);
        mbjects=objects;
        mcontext=context;
        res=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflate= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflate.inflate(res,parent,false);
        }
        Products c=mbjects.get(position);
        TextView txtAppTitle=(TextView) convertView.findViewById(R.id.txtProductName);
        txtAppTitle.setText(c.getName());
        TextView txtDevName=(TextView) convertView.findViewById(R.id.txtDiscount);
        txtDevName.setText(c.getDiscount());
        TextView txtCategory=(TextView) convertView.findViewById(R.id.txtPrice);
        txtCategory.setText(c.getPrice());
        TextView txtPrice=(TextView) convertView.findViewById(R.id.txtRegion);
        txtPrice.setText(c.getRegion());

        ImageView img=(ImageView) convertView.findViewById(R.id.imgIcon);
        if(!c.getPhotoURL().equals("null"))
        Picasso.with(convertView.getContext()).load("http://inclass01-srachaku.rhcloud.com/"+c.getPhotoURL()).into(img);

        return convertView;
    }
}

