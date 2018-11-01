package com.example.adrian.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by Adrian on 12/02/2018.
 */

public class MessageAdapter extends ArrayAdapter<ChatMessage>{

    private String parm;
	
    //Custom adapter for messages
    public MessageAdapter(Context context, int resource, List<ChatMessage> objects) {
        super(context, resource,objects);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChatMessage message = getItem(position);
       ;

        if( FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().equals(message.getMessageUser())) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.message_other,parent,false);
            TextView text2 = (TextView) convertView.findViewById(R.id.message_text);
            //TextView text3 = (TextView) convertView.findViewById(R.id.message_time);
            //text1.setText(message.getMessageUser());
            text2.setText(message.getMessageText());

        }
        else
        {

            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.message,parent,false);
            TextView text11 = (TextView) convertView.findViewById(R.id.message_user2);
            TextView text22 = (TextView) convertView.findViewById(R.id.message_text2);
            text22.setText(message.getMessageText());
            text11.setText(message.getMessageUser().substring(0,1));
           // text33.setText(DateFormat.format("dd-MM-yyyy",
                  //  message.getMessageTime()));
        }


        return convertView;
    }


}
