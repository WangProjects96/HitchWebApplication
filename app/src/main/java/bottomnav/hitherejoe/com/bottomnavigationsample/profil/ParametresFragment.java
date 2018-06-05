package bottomnav.hitherejoe.com.bottomnavigationsample.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

import static android.app.Activity.RESULT_OK;

public class ParametresFragment extends Fragment {
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private ImageView aImageProfile;
    private EditText aPseudoEdit;
    private EditText aNomEdit;
    private TextView aPP;
    private EditText aPrénomEdit;
    private EditText aVilleEdit;
    private RelativeLayout relativeLayout;

    private ProgressDialog progDial;

    private TextView aConfirmer;
    private ImageButton aRetour;

    ArrayAdapter<CharSequence> dataAdapter, dataAdapterSC;
    private Spinner spinnerCouleur;
    private String Couleur;
    private String title2;

    private Uri newImage = null;

    private  final  static int GALLERY_CODE = 1;

    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser loggedUser;
    private static DatabaseReference userRef;
    private static StorageReference refStorage;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_parametres, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        refStorage = FirebaseStorage.getInstance().getReference().child("ProfileImages");
        loggedUser = firebaseAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(loggedUser.getUid());

        this.progDial = new ProgressDialog(getActivity());

        aImageProfile = (ImageView) rootView.findViewById(R.id.idImage);
        aPseudoEdit = (EditText) rootView.findViewById(R.id.idProfilePseudo);
        aNomEdit = (EditText) rootView.findViewById(R.id.idNomProfile);
        aPrénomEdit = (EditText) rootView.findViewById(R.id.idPrenomProfile);
        aVilleEdit = (EditText) rootView.findViewById(R.id.idVilleProfile);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.activity_param);
        aPP = (TextView) rootView.findViewById(R.id.idTVPP);

        spinnerCouleur = (Spinner) rootView.findViewById(R.id.spinner2);
        dataAdapterSC = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerItem2, android.R.layout.simple_spinner_item);
        dataAdapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCouleur.setAdapter(dataAdapterSC);

        spinnerCouleur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title2 = parent.getItemAtPosition(position).toString();
                if (title2.equals("Couleur")) {
                    Toast.makeText(getContext(), "Choisissez une Couleur !", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });





        aConfirmer = (TextView) rootView.findViewById(R.id.idConfirmProfile);
        aRetour = (ImageButton) rootView.findViewById(R.id.idAnnulerProfile);
        fragmentManager = getFragmentManager();
        aRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.idAnnulerProfile:
                        SettingsFragment.hideKeyboard(getActivity());
                        relativeLayout.setVisibility(View.GONE);
                        Fragment fragment = new UserFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                        transaction.replace(R.id.activity_param, fragment).commit();
                        break;
                }
            }
        });

        aPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
            }
        });

        aConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingsFragment.hideKeyboard(getActivity());
                progDial.setMessage("Envoi en cours");
                progDial.show();

                if (title2.equals("Couleur")) {
                    Toast.makeText(getContext(), "Veuillez choisir une Couleur.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title2.equals("Blanc")) {
                    //Couleur = Color.WHITE;
                    Couleur="white";
                }
                if (title2.equals("Noir")) {
                    // Couleur = Color.BLACK;
                    Couleur="black";
                }
                if (title2.equals("Vert")) {
                    // Couleur = Color.GREEN;
                    Couleur="green";
                }
                if (title2.equals("Rouge")) {
                    //Couleur = Color.RED;
                    Couleur="red";
                }
                if (title2.equals("Bleue")) {
                    //Couleur = Color.BLUE;
                    Couleur="blue";
                }
                if (title2.equals("Gris")) {
                    // Couleur = Color.GRAY;
                    Couleur="gray";
                }
                if (title2.equals("Jaune")) {
                    // Couleur = Color.GRAY;
                    Couleur = "yellow";
                }

                setData("pseudo",aPseudoEdit.getText().toString().trim());
                setData("firstName",aPrénomEdit.getText().toString().trim());
                setData("lastName",aNomEdit.getText().toString().trim());
                setData("ville",aVilleEdit.getText().toString().trim());
                setData("color",Couleur);

                if(newImage!=null){
                    StorageReference filePath = refStorage.child(loggedUser.getUid());
                    filePath.putFile(newImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            setData("profilePic",taskSnapshot.getDownloadUrl().toString());
                        }
                    });

                }
                progDial.dismiss();
                relativeLayout.setVisibility(View.GONE);
                fragment = new UserFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.activity_param, fragment).commit();
            }
        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setMinCropResultSize(500,500)
                    .setMaxCropResultSize(500,500)
                    .start(getContext(),this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                aImageProfile.setImageURI(resultUri);
                newImage = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setData(String child, String value){
        if(!TextUtils.isEmpty(value) && !TextUtils.isEmpty(child)){
            userRef.child(child).setValue(value);
        }
    }
}
