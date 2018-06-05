package bottomnav.hitherejoe.com.bottomnavigationsample.accueil;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/**
 * Created by Caron on 20/02/2017.
 */

public class CityPreference {

    SharedPreferences prefs;
    public static String city;
    private DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();


    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity() {
        return prefs.getString("city", "Mantes-la-Jolie");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }


}