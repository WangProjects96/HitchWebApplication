package bottomnav.hitherejoe.com.bottomnavigationsample.start;

/**
 * Created by X250 on 21/03/2017.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;
import userPackage.User;

public class InscriptionActivity extends AppCompatActivity {


    private EditText date;
    private DatePickerDialog datePickerDialog;

    private EditText pseudo;
    private EditText email ;
    private EditText email_confirm ;
    private EditText password ;
    private EditText first_name;
    private EditText last_name;
    private EditText city ;
    private RadioGroup sexe;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide status bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //hide toolBar
        //getSupportActionBar().hide();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        pseudo = (EditText) findViewById(R.id.pseudo);
        email = (EditText) findViewById(R.id.email);
        email_confirm = (EditText) findViewById(R.id.email_confirm);
        password = (EditText) findViewById(R.id.password);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        city = (EditText) findViewById(R.id.city);
        sexe = (RadioGroup) findViewById(R.id.radioSexe);


        // Avoid automatically appear keyboard when you start an activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date_birth);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(InscriptionActivity.this,
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

        ImageButton swapLoginActivity = (ImageButton) findViewById(R.id.return_button);
        swapLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView createButton = (TextView) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addUserBase()){
                    Log.d("TAG","Cest la merde");
                    return;
                }else{
                    Toast.makeText(InscriptionActivity.this,"success add",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private User addUser(String firstName, String lastName,String pseudo,String dateNaissance,String ville,String sexe){
        return new User(firstName,lastName,pseudo,dateNaissance,ville,sexe);
    }

    private boolean addUserBase(){
        String pseudo = this.pseudo.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String email_confirm = this.email_confirm.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String first_name = this.first_name.getText().toString().trim();
        String last_name = this.last_name.getText().toString().trim();
        String city = this.city.getText().toString().trim();
        String date = this.date.getText().toString().trim();


        if(TextUtils.isEmpty(pseudo) || TextUtils.isEmpty(email) || TextUtils.isEmpty(email_confirm) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(first_name)|| TextUtils.isEmpty(last_name)|| TextUtils.isEmpty(city)){

            Toast.makeText(InscriptionActivity.this, "Veuillez renseigner tous les champs !", Toast.LENGTH_SHORT).show();

            return false;
        }

        if(!checkMail(email)){

            Toast.makeText(InscriptionActivity.this, "Ceci n'est pas un mail !", Toast.LENGTH_SHORT).show();

            return false;
        }

        if(!checkConfirmMail(email,email_confirm)){

            Toast.makeText(InscriptionActivity.this, "Le mail et sa confirmation ne correspondent pas !", Toast.LENGTH_SHORT).show();

            return false;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(InscriptionActivity.this, "Echec",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            finalizeregistration(task.getResult().getUser());
                        }
                    }
                });
        return true;
    }

    private void finalizeregistration(FirebaseUser u){
        String pseudo = this.pseudo.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String first_name = this.first_name.getText().toString().trim();
        String last_name = this.last_name.getText().toString().trim();
        String city = this.city.getText().toString().trim();
        String date = this.date.getText().toString().trim();

        User userMore = addUser( first_name,  last_name, pseudo, date, city, getSexe());
        mDatabase.child("users").child(u.getUid()).setValue(userMore);
        Toast.makeText(InscriptionActivity.this,"Success !", Toast.LENGTH_SHORT).show();
    }

    private boolean checkConfirmMail(String mail, String confirm){
        return mail.equals(confirm);
    }

    private boolean checkMail(String mail){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}");
        Matcher mat = pattern.matcher(mail);
        return mat.matches();
    }

    private String getSexe(){
        if(this.sexe.getCheckedRadioButtonId()==R.id.maleRadioButton){
            return "M";
        }else
        {
            return "F";
        }
    }
}
