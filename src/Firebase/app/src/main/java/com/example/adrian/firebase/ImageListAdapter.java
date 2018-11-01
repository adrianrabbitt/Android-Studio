package com.example.adrian.firebase;

/**
 * Created by Adrian on 21/03/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private DatabaseReference databaseReference;
    private List<ImageUpload> listImage;

    //Initialse adapter
    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
   //set the current image object
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("certs").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        CustomImageView img = (CustomImageView) v.findViewById(R.id.imgView);
        ImageView delete = (ImageView) v.findViewById(R.id.img);

        if (delete != null) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    View promptView = layoutInflater.inflate(R.layout.deletefile, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setView(promptView);

                    final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                    final Animation animShakeP = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                    final ImageView loc = (ImageView) promptView.findViewById(R.id.xbutton);
                    loc.startAnimation(animShakeP);
                    // setup a dialog window
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ImageUpload a = new ImageUpload();

                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("certs").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    databaseReference.removeValue();
                                    notifyDataSetChanged();


                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create an alert dialog
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();


                }
            });
        }


        tvName.setText(listImage.get(position).getName());

        //load into imageView within Image Object
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);

        return v;

    }
	
	//reference tutorial that helped https://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/


}