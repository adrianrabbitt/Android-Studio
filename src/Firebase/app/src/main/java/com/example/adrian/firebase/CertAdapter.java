package com.example.adrian.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 12/02/2018.
 */

public class CertAdapter extends ArrayAdapter<Ratingc> {

    private String parm;
    private ChildEventListener eventListener;

    public CertAdapter(Context context, int resource, List<Ratingc> objects) {
        super(context, resource, objects);

    }

    ArrayList<String> members = new ArrayList<String>();
    ArrayList<String> users = new ArrayList<String>();


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Ratingc message = getItem(position);

        Toast.makeText(getContext(), "" + getContext().getClass().getSimpleName(), Toast.LENGTH_SHORT).show();

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.cert, parent, false);
        ImageView image = (ImageView) convertView.findViewById(R.id.rate);

        //TextView text3 = (TextView) convertView.findViewById(R.id.message_time);

        //text1.setText(message.getMessageUser());


        return convertView;
    }


}
