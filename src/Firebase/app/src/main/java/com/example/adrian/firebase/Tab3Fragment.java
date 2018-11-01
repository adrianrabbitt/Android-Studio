package com.example.adrian.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Tab3Fragment extends Fragment {

    ImageView image;
    String Name;
    ImageView image2;
    String newString;
    EditText input;
    private ChildEventListener eventListener;
    ListView listView;
    TextView title;
    Float ratingvalue;
    String description;

    RatingsAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3Fragment newInstance(String param1, String param2) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //Bundle extras = getIntent().getExtras();
        //Bundle extras = getIntent().getExtras();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        description = null;
        Main2Activity act = new Main2Activity();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        newString = settings.getString("ProfileUser", " ");
        Name = "Ytw5ycYhzhTrWibitnCPXrRMIC72";
        ;
        listView = (ListView) view.findViewById(R.id.list_of_ratings);
        List<Ratingc> rating = new ArrayList<>();

        //setting adapter for ratings
        adapter = new RatingsAdapter(getActivity(), R.layout.rate, rating);
        listView.setAdapter(adapter);
        attachDatabaseReadListener();
        image = (ImageView) view.findViewById(R.id.pic);

        input = (EditText) view.findViewById(R.id.edittext);
        final RatingBar mBar = (RatingBar) view.findViewById(R.id.rate);
        //Reference dataabse
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        input = (EditText) view.findViewById(R.id.edittext);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");

		
		//setting rating bar
        mBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Float ratingVal = (Float) rating;
                ratingvalue = (Float) mBar.getRating();
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View promptView = layoutInflater.inflate(R.layout.comment, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.edittext2);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (!editText.getText().toString().equals("Enter Comment..")) {
                                    Toast.makeText(getContext(), "cd ls mdl" + editText.getText(), Toast.LENGTH_LONG).show();
                                    description = editText.getText().toString();
                                    FirebaseDatabase.getInstance().getReference().child("Ratings").child(newString)
                                            .push()
                                            .setValue(new Ratingc(description, description, FirebaseAuth.getInstance()
                                                    .getCurrentUser().getEmail(), ratingvalue)
                                            );
                                } else {
                                    Toast.makeText(getContext(), "Enter Comment", Toast.LENGTH_LONG).show();
                                }

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


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void attachDatabaseReadListener() {
        if (eventListener == null) {
            eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Ratingc message = dataSnapshot.getValue(Ratingc.class);
                    adapter.add(message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            FirebaseDatabase.getInstance().getReference().child("Ratings").child(newString).addChildEventListener(eventListener);


        }
    }

}
