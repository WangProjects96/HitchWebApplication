package bottomnav.hitherejoe.com.bottomnavigationsample;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Appli;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Vetements;

/**
 * Created by X250 on 27/03/2017.
 */

public class GenerationActivity extends AppCompatActivity {

    ImageView haut1;
    ImageView haut2;
    ImageView manteau;
    ImageView bas;
    ImageView chaussure;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser loggedUser;

    private StorageReference refStorage;

    private StorageReference userRef;

    private DatabaseReference dbRef;
    private DatabaseReference tenueJourRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_algorithme);
        Log.d("unsuff","p1 ");


        firebaseAuth = FirebaseAuth.getInstance();
        loggedUser = firebaseAuth.getCurrentUser();

        refStorage = FirebaseStorage.getInstance().getReference();
        userRef = refStorage.child("images").child(loggedUser.getUid());

        dbRef = FirebaseDatabase.getInstance().getReference();
        tenueJourRef = dbRef.child("tenues").child(loggedUser.getUid());


        haut1 = (ImageView) findViewById(R.id.idHaut1);
        haut2 = (ImageView) findViewById(R.id.idHaut2);
        manteau = (ImageView) findViewById(R.id.idManteau);
        bas = (ImageView) findViewById(R.id.idBas);
        chaussure = (ImageView) findViewById(R.id.idChaussure);


        Bundle b = getIntent().getExtras();
        double temperature = b.getDouble("temperature");
        Log.d("unsuff","p2 ");

        try {
            Appli.main(15.5, MainActivity.dressing);
        }catch (Exception e){
            Log.d("unsuff",e.toString());
        }

        Log.d("unsuff","p3 ");

        ArrayList<Vetements> tenue = Appli.getTenue();
        Log.d("unsuff","p4 ");


        if(tenue.get(0)!= null){
            tenueJourRef.child("haut1").setValue(tenue.get(0).getNom());
            userRef.child(tenue.get(0).getNom()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("endd",uri.toString());
                    Picasso.with(getApplicationContext())
                            .load(uri)
                            .into(haut1);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("retrieve","nope");
                }
            });
        }

        Log.d("unsuff","p5 ");

        if(tenue.get(1)!= null){
            tenueJourRef.child("haut2").setValue(tenue.get(1).getNom());
            userRef.child(tenue.get(1).getNom()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("endd",uri.toString());
                    Picasso.with(getApplicationContext())
                            .load(uri)
                            .into(haut2);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("retrieve","nope");
                }
            });
        }

        if(tenue.get(2)!= null){
            tenueJourRef.child("bas").setValue(tenue.get(2).getNom());
            userRef.child(tenue.get(2).getNom()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("endd",uri.toString());
                    Picasso.with(getApplicationContext())
                            .load(uri)
                            .into(bas);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("retrieve","nope");
                }
            });
        }

        if(tenue.get(3)!= null){
            tenueJourRef.child("chaussure").setValue(tenue.get(3).getNom());
            userRef.child(tenue.get(3).getNom()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("endd",uri.toString());
                    Picasso.with(getApplicationContext())
                            .load(uri)
                            .into(chaussure);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("retrieve","nope");
                }
            });
        }


        if(tenue.get(4)!= null){
            tenueJourRef.child("manteau").setValue(tenue.get(4).getNom());
            userRef.child(tenue.get(4).getNom()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("endd",uri.toString());
                    Picasso.with(getApplicationContext())
                            .load(uri)
                            .into(manteau);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("retrieve","nope");
                }
            });
        }


    }


}
