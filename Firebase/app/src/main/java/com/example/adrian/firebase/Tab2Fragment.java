package com.example.adrian.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab2Fragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    Button Parent;
    TextView NewText;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TextView Name;
    private TextView Email;
    private TextView Mobile;
    private TextView Address;
    private TextView Info;
    private TextView previous;
    private TextView Age;
    private TextView des;
    private TextView covername;
    private TextView skill;
    private TextView dis;
    Vairables var;
    public String ID;
    String newString;
    double harsine;
    GPSTracker gps;
    SharedPreferences sharedPref;
    private ScrollView Scroll;
    double distance;
    ImageView image;
    private TextView age;
    ImageView mesage;
    String first;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);


        image = (ImageView) view.findViewById(R.id.image);

        sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Bundle extras = getIntent().getExtras();
        Main2Activity act = new Main2Activity();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        newString = settings.getString("ProfileUser", " ");
   //     Toast.makeText(getContext(), " " + newString, Toast.LENGTH_LONG).show();
        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.fromtop);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.startAnimation(animShake);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatApplication.class);
                Bundle extras = new Bundle();
                extras.putString("U", newString);
                extras.putString("F", first);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");


        //Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
        //  mImageView.setImageBitmap(bitmap);

        myRef = database.getReference();

        //covername = (TextView)findViewById(R.id.name2);
        // NewText = (TextView)findViewById(R.id.button);
        Email = (TextView) view.findViewById(R.id.email);
        //  Address = (TextView)findViewById(R.id.button);
        Mobile = (TextView) view.findViewById(R.id.number);
        Address = (TextView) view.findViewById(R.id.address);
        Info = (TextView) view.findViewById(R.id.personal);
        Info.setMovementMethod(new ScrollingMovementMethod());
        previous = (TextView) view.findViewById(R.id.previous);
        skill = (TextView) view.findViewById(R.id.skills);
        Scroll = (ScrollView) view.findViewById(R.id.scroll);

        // age = (TextView)findViewById(R.id.age);
        previous.setMovementMethod(new ScrollingMovementMethod());


        Scroll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Info.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });


        previous.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                previous.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });


        Info.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Info.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });


        //    var = new Vairables();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Count", "" + dataSnapshot.getChildrenCount());
                Student s = dataSnapshot.child("Users").child(newString).child("Info").getValue(Student.class);
                ;

                double d = getdistance(s.getlat(), s.getlng());
                double rounded = Math.round((d) * 100.0) / 100.0;

                // Name.setText(" " + s.getfName());

                Email.setText(s.getEmailID());
                Mobile.setText(s.getMobile());
                Address.setText(s.getIAddress1() + " " + s.getIAddress2());
                Info.setText(s.getInfo());
                previous.setText(s.getPtitle() + "\n" + "\n" + "\u25CF" + s.getPdes());

                String skil = "\u25CF" + s.getSkill().replace("\n", "\n" + "\u25CF");
                skill.setText(skil);
                // Age.setText(s.getYear());
                first = s.getfName();

                //Address.setText(s.getjob());

                //Toast.makeText(getApplicationContext(), "Please Complete Sections" + s.getlat() + " "+ s.getlng(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void OnClickParent() {
       /* Parent = (Button) findViewById(R.id.sign_out);
        Parent.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {

                                          Intent intent = new Intent(Profile.this, ChatApplication.class);
                                          startActivity(intent);
                                      }

                                  }
        );
        */
    }

    public void OnMessage() {


    }

    public double getdistance(double lat, double lng)    // boolean function waiting for parms from server
    {
        gps = new GPSTracker(getActivity());    // currLoc = current location
        harsine =
                (
                        //Harversine formula this will get the distance as the crow flys
                        //Values have to be putinto radians.
                        Math.sin(Math.toRadians(lat - gps.getLatitude())) * Math.sin(Math.toRadians(lat - gps.getLatitude()))
                                + Math.cos(Math.toRadians(gps.getLatitude())) * Math.cos(Math.toRadians(lat)) * (Math.sin(Math.toRadians(lng - gps.getLongitude())) * Math.sin(Math.toRadians(lat - gps.getLongitude())))

                );

        distance = Math.abs((2 * 6371) * Math.asin(harsine));
        distance = distance * 10;

        return distance;
    }




}
