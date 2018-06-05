package bottomnav.hitherejoe.com.bottomnavigationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Couleur;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.TypeV;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Vetements;

public class Tester extends AppCompatActivity {

    static ArrayList<Vetements> dressing;

    private static DatabaseReference mDatabase;
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mLoggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mLoggedUser = mFirebaseAuth.getCurrentUser();


        initDressing();
        //double tmp = 15.5;
        //Appli.main(tmp,dressing);




    }

    private static void initDressing() {

        dressing = new ArrayList<Vetements>();
//
//        dressing.add(new Vetements(nom = nom + ".1", Couleur.BLUE, TypeV.MANCHECOURTE));
//        dressing.add(new Vetements(nom = "Tshirt.2", Couleur.GREEN, TypeV.MANCHECOURTE));
//        dressing.add(new Vetements(nom = "Tshirt.3", Couleur.RED, TypeV.MANCHECOURTE));
//        dressing.add(new Vetements(nom = "Tshirt.4", Couleur.BLACK, TypeV.MANCHECOURTE));
//        dressing.add(new Vetements(nom = "Tshirt.longue", Couleur.BLUE, TypeV.MANCHELONGUE));
//        dressing.add(new Vetements(nom = "Tshirt.chemise", Couleur.GREEN, TypeV.MANCHECOURTE));
//        nom = "Pantalon";
//        dressing.add(new Vetements(nom = "Pantalon.1", Couleur.BLUE, TypeV.PANTALON));
//        dressing.add(new Vetements(nom = "Pantalon.2", Couleur.GREEN, TypeV.PANTALON));
//        dressing.add(new Vetements(nom = "Pantalon.3", Couleur.RED, TypeV.PANTALON));
//        dressing.add(new Vetements(nom = "Pantalon.4", Couleur.BLACK, TypeV.PANTALON));
//        nom = "Haut2";
//        dressing.add(new Vetements(nom = "Haut2.1", Couleur.BLUE, TypeV.SWEAT));
//        dressing.add(new Vetements(nom = "Haut2.2", Couleur.GREEN, TypeV.SWEAT));
//        dressing.add(new Vetements(nom = "Haut2.3", Couleur.RED, TypeV.SWEAT));
//        nom = "Manteau";//        dressing.add(new Vetements(nom = "Haut2.4", Couleur.BLACK, TypeV.SWEAT));

//        dressing.add(new Vetements("Manteau.1", Couleur.BLUE, TypeV.MANTEAU));
//        dressing.add(new Vetements("Manteau.2", Couleur.GREEN, TypeV.MANTEAU));
//        dressing.add(new Vetements("Manteau.3", Couleur.RED, TypeV.MANTEAU));
//        dressing.add(new Vetements("Manteau.4", Couleur.BLACK, TypeV.MANTEAU));
//        nom = "Chaussure";
//        dressing.add(new Vetements(nom = "Chaussure.1", Couleur.BLUE, TypeV.BASKET));
//        dressing.add(new Vetements(nom = "Chaussure.2", Couleur.GREEN, TypeV.BASKET));
//        dressing.add(new Vetements(nom = "Chaussure.3", Couleur.RED, TypeV.CHAUSSUREVILLE));
//        dressing.add(new Vetements(nom = "Chaussure.4", Couleur.BLACK, TypeV.CHAUSSUREVILLE));

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("vetements").child(mLoggedUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String nom = dataSnapshot.child("url").getValue().toString();
                String color =  dataSnapshot.child("color").getValue().toString();
                Couleur c = Couleur.getCouleur(color);
                String type = dataSnapshot.child("type").getValue().toString();
                TypeV t = TypeV.type(type);
                dressing.add(new Vetements(nom, c, t));
                getDressing();
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
        });
    }

    private static void getDressing(){
        for(Vetements v : dressing){
            Log.d("dress",v.toString());
        }
    }

//    (new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            Iterable<DataSnapshot> types = dataSnapshot.getChildren();
//            for(DataSnapshot t : types){
//                Log.d("Debug : hhh ",t.getKey());
//                Iterable<DataSnapshot> vetements = t.getChildren();
//                for (DataSnapshot v : vetements) {
//                    Log.d("Debug : hhh ","/////"+v.child("type").getValue());
//
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });

}
