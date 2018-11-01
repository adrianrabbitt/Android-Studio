package com.example.adrian.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Adrian on 12/02/2018.
 */

public class RatingsAdapter extends ArrayAdapter<Ratingc>{

    private String parm;
    private ChildEventListener eventListener;

    public RatingsAdapter(Context context, int resource, List<Ratingc> objects) {
        super(context, resource,objects);

    }

    ArrayList<String> members = new ArrayList<String>();
    ArrayList<String> users = new ArrayList<String>();





    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Ratingc message = getItem(position);

        Toast.makeText(getContext(), ""+ getContext().getClass().getSimpleName() ,Toast.LENGTH_SHORT).show();

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.rate, parent, false);
            RatingBar mBar = (RatingBar) convertView.findViewById(R.id.rate);
            TextView text1 = (TextView) convertView.findViewById(R.id.sender);
            TextView text3 = (TextView) convertView.findViewById(R.id.message);
          TextView best_deals = (TextView) convertView.findViewById(R.id.message_userC);
        int boy[]={R.drawable.cir,R.drawable.cir2,R.drawable.cir3,R.drawable.cir4};

        Random rand = new Random();
        int index = rand.nextInt((boy.length- 1) - 0 + 1) + 0;
        best_deals.setBackground(getContext().getResources().getDrawable(boy[index]));

            //TextView text3 = (TextView) convertView.findViewById(R.id.message_time);

            //text1.setText(message.getMessageUser());
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String date = df.format(message.getMessagetime());
            text1.setText(message.getsName());
            text3.setText(message.getMessageUser() );
           mBar.setRating(message.getRating());
        best_deals.setText(message.getsName().substring(0, 1));

        return convertView;
    }





}
