package bottomnav.hitherejoe.com.bottomnavigationsample.profil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

public class SettingsFragment extends Fragment {
    private EditText date;
    private DatePickerDialog datePickerDialog;

    private EditText pseudo;
    private EditText first_name;
    private EditText last_name;
    private EditText city ;
    private Button retour;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the post for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        pseudo = (EditText) rootView.findViewById(R.id.edit_pseudo);
        first_name = (EditText) rootView.findViewById(R.id.edit_first_name);
        last_name = (EditText) rootView.findViewById(R.id.edit_last_name);
        city = (EditText) rootView.findViewById(R.id.edit_city);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        date = (EditText) rootView.findViewById(R.id.edit_date_birth);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        retour = (Button) rootView.findViewById(R.id.settings_return_button);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settings_return_button:
                        Fragment fragment = new UserFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                        transaction.replace(R.id.rL_Settings, fragment).commit();
                        hideKeyboard(getActivity());
                        break;
                }
            }
        });

        return rootView;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
