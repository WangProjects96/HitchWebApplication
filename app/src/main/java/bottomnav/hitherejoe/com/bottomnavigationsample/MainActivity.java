package bottomnav.hitherejoe.com.bottomnavigationsample;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Appli;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Couleur;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.TypeV;
import bottomnav.hitherejoe.com.bottomnavigationsample.algo.Vetements;
import bottomnav.hitherejoe.com.bottomnavigationsample.accueil.WeatherMainFragment;
import bottomnav.hitherejoe.com.bottomnavigationsample.dressing.DressingFragment;
import bottomnav.hitherejoe.com.bottomnavigationsample.fil.FilFragment;
import bottomnav.hitherejoe.com.bottomnavigationsample.profil.UserFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    static ArrayList<Vetements> dressing;

    private static DatabaseReference mDatabase;
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseUser mLoggedUser;
    static  int cmpt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mLoggedUser = mFirebaseAuth.getCurrentUser();


        initDressing();
        initColor();

        final BottomNavigationView bNV = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        fragmentManager = getSupportFragmentManager();
        fragment = new WeatherMainFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
        bNV.setBackgroundResource(R.color.mainColor);

        bNV.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = new WeatherMainFragment();
                                bNV.setBackgroundResource(R.color.mainColor);
                                break;
                            case R.id.action_search:
                                fragment = new FilFragment();
                                bNV.setBackgroundResource(R.color.white);
                                break;
                            case R.id.action_dressing:
                                fragment = new DressingFragment();
                                bNV.setBackgroundResource(R.color.mainColor);
                                break;
                            case R.id.action_user:
                                fragment = new UserFragment();
                                bNV.setBackgroundResource(R.color.white);
                                break;
                            default:
                                fragment = new WeatherMainFragment();
                                bNV.setBackgroundResource(R.color.mainColor);
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }
                });

        /*int statusBarHeight = 0;
        int resourceIdSB = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceIdSB > 0) statusBarHeight = getResources().getDimensionPixelSize(resourceIdSB);
        RelativeLayout rL_Main = (RelativeLayout) findViewById(R.id.mainID);
        rL_Main.setPadding(0, statusBarHeight, 0, 0);*/

        /*int navigationBarHeight = 0;
        int resourceIdNB = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceIdNB > 0) navigationBarHeight = getResources().getDimensionPixelSize(resourceIdNB);
        boolean hasMenuKey = ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if(!hasMenuKey && !hasBackKey) bNV.setPadding(0, 0, 0, 0);
        bNV.setPadding(0, 0, 0, navigationBarHeight);*/
    }

    private static void initColor() {
        mDatabase.child("users").child(mLoggedUser.getUid()).child("color").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        Appli.setColor(dataSnapshot.getValue().toString());
                    }catch (Exception e){
                        Log.d("cooll",e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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


                if(dataSnapshot.child("type").exists()) {

                    Log.d("Boug" + cmpt++, dataSnapshot.toString());
                    String nom = dataSnapshot.child("url").getValue().toString();
                    String color = dataSnapshot.child("color").getValue().toString();
                    Couleur c = Couleur.getCouleur(color);
                    String type = dataSnapshot.child("type").getValue().toString();
                    TypeV t = TypeV.type(type);
                    dressing.add(new Vetements(nom, c, t));


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.child("type").exists()){

                    Log.d("Boug"+cmpt++,dataSnapshot.toString());
                    String nom = dataSnapshot.child("url").getValue().toString();
                    String color =  dataSnapshot.child("color").getValue().toString();
                    Couleur c = Couleur.getCouleur(color);
                    String type = dataSnapshot.child("type").getValue().toString();
                    TypeV t = TypeV.type(type);
                    dressing.add(new Vetements(nom, c, t));
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                logList(dressing);
//                dressing.remove(new Vetements(dataSnapshot.child("url").getValue().toString(),null,null));
//
//                Appli.logList(dressing);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void logList( ArrayList<Vetements> vs){
        for(Vetements v:vs){
            Log.d("logRem",v.toString());
        }
    }




}
