package bottomnav.hitherejoe.com.bottomnavigationsample.dressing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bottomnav.hitherejoe.com.bottomnavigationsample.PrimaryKey;
import bottomnav.hitherejoe.com.bottomnavigationsample.R;

import static bottomnav.hitherejoe.com.bottomnavigationsample.algo.TypeV.getCat;

/**
 * Created by X250 on 24/03/2017.
 */

public class AjouterVetementFragment extends Fragment {
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Champs necessaire";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private Calendar now;
    private EditText mBodyField, inputDate, editTextAdresse;
    private TextView mSubmitButton;
    private Spinner spinnerType, spinnerCouleur;
    ArrayAdapter<CharSequence> dataAdapter, dataAdapterSC, dataAdapterVille;
    private String title;
    private String title2;
    private String Couleur;
    private String TypeV;
    int PLACE_PICKER_REQUEST = 1;

    Uri selectedImage;
    StorageReference riversRef;
    String newPicFile;
    int nb= 0;

    String result = "";
    private DatabaseReference databaseImages = FirebaseDatabase.getInstance().getReference().child("images");
    static int n = 0;
    // private ImageView img;
    private static String logtag = "CameraApp";
    static final int CAM_IMAGE = 1;
    static final int GAL_IMAGE = 1;
    private Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private FirebaseAuth authentificationFireBase;
    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
    String uid = u.getUid();

    // ArrayList<Category> categories;
    public AjouterVetementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ajouter_vetements, container, false);
        this.authentificationFireBase = FirebaseAuth.getInstance();

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        final TextView camButton = (TextView) rootView.findViewById(R.id.camButton);
        camButton.setOnClickListener(camListener);

        final TextView galButton = (TextView) rootView.findViewById(R.id.galButton);
        galButton.setOnClickListener(galListener);


        // categories = Category.getCategories();
        mSubmitButton = (TextView) rootView.findViewById(R.id.button33);
        now = Calendar.getInstance();

        spinnerType = (Spinner) rootView.findViewById(R.id.spinner);
        spinnerCouleur = (Spinner) rootView.findViewById(R.id.spinner2);

        dataAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);

        // attaching data adapter to spinner
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                title = parent.getItemAtPosition(position).toString();
                if (title.equals("Type Vetement")) {
                    Toast.makeText(getContext(), "Choisissez le type de vetement !", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dataAdapterSC = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerItem2, android.R.layout.simple_spinner_item);
        dataAdapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCouleur.setAdapter(dataAdapterSC);

        // attaching data adapter to spinner
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
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private View.OnClickListener camListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prendrePhoto(v);
        }
    };

    private View.OnClickListener galListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ouvrirPhoto(v);
        }
    };

    private void prendrePhoto(View v) {
        Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newPicFile = df.format(date) + ".jpg";
        String outPath ="/Hitch/"+ newPicFile ;

        Log.i("galleryAdd","outPath" + outPath);

        File photo =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/"+outPath);

        imageUri = Uri.fromFile(photo);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);

        startActivityForResult(camIntent, CAM_IMAGE);
    }

    private void ouvrirPhoto(View v) {
        n++;
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, GAL_IMAGE);

        Log.i("ouvrirPhoto","ok");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://hitch-6a6cc.appspot.com");

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == CAM_IMAGE){
            }

            if (requestCode == GAL_IMAGE && null != intent) {
                Log.i("requestCode", "ok");
                selectedImage = intent.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(selectedImage);

                    ImageView imageView = (ImageView) getActivity().findViewById(R.id.imgView);
                    imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (imageStream != null) {
                        try {
                            imageStream.close();
                        } catch (IOException e) {
                        }
                    }
                }
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                newPicFile = df.format(date) + ".jpg";

                riversRef = storageRef.child("images/"+uid+"/"+newPicFile);

                riversRef.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                @SuppressWarnings("VisibleForTests")
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });
            }
        }
    }


    private void submitPost() {
        //final String title = mTitleField.getText().toString();
        //final String sousCategorie = mSousCategorieField.getText().toString();
        //final String lieu = mLieuField.getText().toString();
        //final String body = mBodyField.getText().toString();
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imgView);

        if (imageView.getDrawable()==null){
            Toast.makeText(getContext(), "Veuillez choisir une photo dans la galerie", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("titre", title.toString());

        // Categorie necessaire
        if (title2.equals("Couleur")) {
            Log.i("enter", title.toString());
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
       /* if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }*/

        // Lieu necessaire
        if (title.equals("Type Vetements")) {
            Toast.makeText(getContext(), "Veuillez choisir un Type de Vetements.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.equals("Tshirt")) {
            TypeV = "MANCHECOURTE";
        }
        if (title.equals("Manche Longue")) {
            TypeV = "MANCHELONGUE";
        }
        if (title.equals("Chemise")) {
            TypeV = "CHEMISE";
        }
        if (title.equals("Costume")) {
            TypeV = "COSTUME";
        }
        if (title.equals("Pull")) {
            TypeV = "PULL";
        }
        if (title.equals("Sweat")) {
            TypeV = "SWEAT";
        }
        if (title.equals("Veste")) {
            TypeV = "VESTE";
        }
        if (title.equals("Manteau")) {
            TypeV = "MANTEAU";
        }
        if (title.equals("Pantalon Costume")) {
            TypeV = "PANTALONCOSTUME";
        }
        if (title.equals("Pantalon")) {
            TypeV = "PANTALON";
        }
        if (title.equals("Jupe")) {
            TypeV = "JUPE";
        }
        if (title.equals("Basket")) {
            TypeV = "BASKET";
        }
        if (title.equals("Chaussure Ville")) {
            TypeV = "CHAUSSUREVILLE";
        }
        if (title.equals("Tong")) {
            TypeV = "TONG";
        }
        if (title.equals("Botte")) {
            TypeV = "BOTTE";
        }

        String categoV = getCat(TypeV);

        DatabaseReference refVetement = mDatabase.child("vetements").child(u.getUid().toString()).push();

        refVetement.child("color").setValue(Couleur);
        refVetement.child("url").setValue(newPicFile);
        refVetement.child(categoV).setValue(categoV);
        refVetement.child("type").setValue(TypeV);


        mDatabase.child("PrimeKeys").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Log.i("prob", "enter");

                result = snapshot.child("Image2Key").getValue().toString();
                Log.i("res key1", result.toString());

                String categorieVetement = getCat(TypeV);

                Vetement AddVetement = new Vetement(Couleur,categorieVetement,u.getUid().toString(),TypeV, newPicFile);
                mDatabase.child("images").child(result).setValue(AddVetement);

                Toast.makeText(getContext(), "Vêtement enregistré", Toast.LENGTH_SHORT).show();

                mDatabase.removeEventListener(this);

                mDatabase.child("PrimeKeys").removeValue();

                int nb = Integer.parseInt(result.toString());
                nb++;
                String nombre = String.valueOf(nb);
                Log.i("res key", nombre.toString());

                PrimaryKey Image2Key = new PrimaryKey(nombre);

                mDatabase.child("PrimeKeys").setValue(Image2Key);

            }
            @Override public void onCancelled(DatabaseError error) { }
        });
    }

//    public void cléPrimaire(){
//
//
//        databaseImages.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                //Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getValue().toString());
//
//                if (dataSnapshot.child("idUser").getValue().toString().equals(u.getUid().toString())) {
//
//                    Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getValue().toString());
//                    String res = dataSnapshot.getKey().toString();
//                    int nombre = Integer.parseInt(res.toString());
//                    if(nombre > nb)
//                        nb = nombre;
//                }
//
//
//
//            }
//
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        String resultat = String.valueOf(nb);
//        Log.i("resultat obtenu", resultat.toString());
//
//    }
}
