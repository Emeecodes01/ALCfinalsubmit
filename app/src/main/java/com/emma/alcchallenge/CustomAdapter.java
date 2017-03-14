package com.emma.alcchallenge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by dell on 06/03/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private ArrayList<myModel> listitems;
    private  int resource;
    private Context context;
    private LayoutInflater inflater;


    public CustomAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this.listitems = objects;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(resource,null);
        }

        TextView userName = (TextView)convertView.findViewById(R.id.user_name);
        ImageView userImage = (ImageView)convertView.findViewById(R.id.user_img);

        // Then later, when you want to display image
        ImageLoader.getInstance().displayImage(listitems.get(position).getProfilePix() + ".png", userImage);

        userName.setText(listitems.get(position).getUserName());

        convertView.setTag(listitems.get(position).getProfilePix());

        convertView.setTag(R.id.user,listitems.get(position).getUserName());

        return convertView;
    }

}