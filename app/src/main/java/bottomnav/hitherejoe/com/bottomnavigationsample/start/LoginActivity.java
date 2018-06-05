package bottomnav.hitherejoe.com.bottomnavigationsample.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import bottomnav.hitherejoe.com.bottomnavigationsample.MainActivity;
import bottomnav.hitherejoe.com.bottomnavigationsample.R;

/**
 * Created by X250 on 21/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;

    private EditText email;
    private EditText password;

    private TextView sign_in_button;
    private TextView register_button;
    private TextView forgotten_account_button;

    //classe qui gère l'authentification de firebase
    private FirebaseAuth authentificationFireBase;
    //classe qui observe la connectivité du client
    private FirebaseAuth.AuthStateListener listenerConnectivité;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide status bar
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //hide toolBar
        //getSupportActionBar().hide();

        // Avoid automatically appear keyboard when you start an activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //récup des layouts
        this.email = (EditText) findViewById(R.id.email);
        this.password = (EditText) findViewById(R.id.password);

        this.sign_in_button = (TextView) findViewById(R.id.sign_in_button);
        this.register_button = (TextView) findViewById(R.id.register_button);
        this.forgotten_account_button = (TextView) findViewById(R.id.forgotten_account_button);

        // mise en place des utilitaires d'authentification
        this.authentificationFireBase = FirebaseAuth.getInstance();

        this.listenerConnectivité = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // si il se co bien
                if(firebaseAuth.getCurrentUser() != null){
                    // intent d'activité en cours (objet mém), class a lancer
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        };

        // buttons on click listeners
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentifier();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,InscriptionActivity.class));
            }
        });

        forgotten_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PassOublieActivity.class));
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.logohitch);
        ViewGroup container = (ViewGroup) findViewById(R.id.email_login_form);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    // on indique a FireBase la connexion du client avec un listener
    @Override
    protected void onStart() {
        super.onStart();
        this.authentificationFireBase.addAuthStateListener(listenerConnectivité);
    }

    private void authentifier(){
        String mail = this.email.getText().toString().trim();
        String pass = this.password.getText().toString().trim();
        if(isCorrect(mail) && isCorrect(pass)) {
            this.authentificationFireBase.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        email.setError("entrer un email valide");
                        password.setError("entrer un mot de passe valide");
                        Toast.makeText(LoginActivity.this, "Veuillez vérifier vos identifiants", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Veuillez renseigner tous les champs !", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCorrect(String s){
        return (s!=null && !s.equals(""));
    }
}