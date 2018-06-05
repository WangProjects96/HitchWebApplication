package bottomnav.hitherejoe.com.bottomnavigationsample.fil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.Random;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

import static android.app.Activity.RESULT_OK;
import static bottomnav.hitherejoe.com.bottomnavigationsample.profil.SettingsFragment.hideKeyboard;

public class CreerPostFragment extends Fragment {

    private static final int MAX_LENGTH = 12;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private ImageButton imagePst;
    private TextView titrePst;
    private TextView commPst;
    private TextView boutonPst;
    private Uri resultUri = null;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    private ProgressDialog progDial;

    private static final int GALLERY_REQUEST = 1;

    private StorageReference refStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    private FirebaseUser loggedUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_creer_post, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        refStorage = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        imagePst = (ImageButton) rootView.findViewById(R.id.imagePost);
        titrePst = (TextView) rootView.findViewById(R.id.titrePost);
        commPst = (TextView) rootView.findViewById(R.id.commentairePost);
        boutonPst = (TextView) rootView.findViewById(R.id.boutonPost);

        final ImageButton rtButton = (ImageButton) rootView.findViewById(R.id.r_Post);

        rtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.r_Post:
                        hideKeyboard(getActivity());
                        Fragment fragment = new FilFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                        transaction.replace(R.id.activity_post, fragment).commit();
                        break;
                }
            }
        });


        this.progDial = new ProgressDialog(getActivity());

        boutonPst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poster();
            }
        });

        imagePst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallerieIntent = new Intent(Intent.ACTION_GET_CONTENT);
                gallerieIntent.setType("image/*");
                startActivityForResult(gallerieIntent,GALLERY_REQUEST);
            }
        });
        return rootView;
    }

    private void poster() {
        this.progDial.setMessage("Envoi en cours");
        this.progDial.show();

        final String titre_val = this.titrePst.getText().toString().trim();
        final String comm_val = this.commPst.getText().toString().trim();

        if(!TextUtils.isEmpty(titre_val) && !TextUtils.isEmpty(comm_val) && imagePst != null){

            loggedUser = firebaseAuth.getCurrentUser();
            String imageName = getRandomString(MAX_LENGTH);
            StorageReference filepath = refStorage.child(loggedUser.getUid()).child(imageName);

            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    // add post
                    DatabaseReference newPost = mDatabase.child("Post").push();

                    newPost.child("user").setValue(loggedUser.getUid());
                    newPost.child("titre").setValue(titre_val);
                    newPost.child("commentaire").setValue(comm_val);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("post_time").setValue(Calendar.getInstance().getTimeInMillis());

                    newPost.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DatabaseReference user = mDatabase.child("users").child(loggedUser.getUid());
                            user.child("Post").child(dataSnapshot.getKey()).setValue("true");
                            dataSnapshot.exists();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference posts = mDatabase.child("Post");
                    posts.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Log.d("removL",dataSnapshot.child(loggedUser.getUid()).getKey());
                            mDatabase.child("users").child(dataSnapshot.child(loggedUser.getUid()).getKey()).child("Post").child(dataSnapshot.getKey()).removeValue();
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    // save for user
                    fragmentManager = getFragmentManager();
                    fragment = new FilFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_post, fragment).commit();
                    progDial.dismiss();
                }
            });
        }else{
            this.progDial.dismiss();
            Toast.makeText(getActivity(),"Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = data.getData();
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(3, 2)
                    .setMaxCropResultSize(2560,1640)
                    .setMinCropResultSize(2560,1640)
                    .start(getContext(),this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                this.imagePst.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
