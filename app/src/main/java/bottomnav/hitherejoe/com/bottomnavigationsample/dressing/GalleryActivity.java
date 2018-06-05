package bottomnav.hitherejoe.com.bottomnavigationsample.dressing;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;
import bottomnav.hitherejoe.com.bottomnavigationsample.librairies.TouchHighlightImageButton;

/**
 * Created by pauli on 23/03/2017.
 */


@SuppressWarnings("deprecation")
public class GalleryActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    private DatabaseReference databaseImages = FirebaseDatabase.getInstance().getReference().child("images");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef = storageRef.child("images");

    StorageReference storageReftest = storageRef.child("images").child(user.getUid()).child("20170323135021.jpg");


    String nom;
    String texte;
    String idVetement;
    List<String> idImages = new ArrayList<>();


    //ImageView img;

    int cpt = 0;

    LinearLayout layout;
    TextView txtVide;
    ProgressBar bar;

    Bitmap bm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gallery_layout);

        layout = (LinearLayout)findViewById(R.id.lnrMainGallery);
        txtVide = (TextView)findViewById(R.id.Nothing);
        bar = (ProgressBar)findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        String choix = intent.getStringExtra("Choix");

        if(choix.equals("Supprimer")){
            AfficherSuppression();
        }

        if(choix.equals("Hauts")){
            AfficherParCatégories("Hauts");
        }

        if(choix.equals("Bas")){
            AfficherParCatégories("BAS");
        }

        if(choix.equals("Chaussures")){
            AfficherParCatégories("CHAUSSURES");
        }
    }

    public void AfficherParCatégories(final String catego){

        txtVide.setVisibility(View.VISIBLE);
        bar.setVisibility(View.VISIBLE);

        //img = (ImageView) findViewById(R.id.imgBaseDeDonnees);

        databaseImages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getValue().toString());
                txtVide.setVisibility(View.GONE);

                if (dataSnapshot.child("idUser").getValue().toString().equals(user.getUid().toString())) {
                    if (!catego.toString().equals("Hauts")) {
                        if (dataSnapshot.child("categorie").getValue().toString().equals(catego.toString())) {
                            Log.i("-------AHHHHHHHHHHHHAAA", "HHHHEEEERRRRREEEEuuuuhhhhh");
                            Log.i("-------data", dataSnapshot.child("categorie").getValue().toString());

                            nom = dataSnapshot.child("url").getValue().toString();

                            storageReftest = storageRef.child("images").child(user.getUid()).child(nom);

                            Glide.with(getApplicationContext())
                                    .using(new FirebaseImageLoader())
                                    .load(storageReftest)
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                            ImageView img = new ImageView(getApplicationContext());
                                            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600));
                                            img.setMaxHeight(150);
                                            img.setMaxWidth(150);

                                            Bitmap targetBitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight());
                                            img.setImageBitmap(targetBitmap);

                                            bar.setVisibility(View.GONE);
                                            // Adds the view to the layout
                                            layout.addView(img);
                                        }
                                    });
                        }
                    }
                    else{

                        if (dataSnapshot.child("categorie").getValue().toString().equals("HAUT1")||dataSnapshot.child("categorie").getValue().toString().equals("HAUT2")) {

                            nom = dataSnapshot.child("url").getValue().toString();
                            Log.i("-------AHHHHHHAAA_URL", nom);


                            storageReftest = storageRef.child("images").child(user.getUid()).child(nom);


                            Glide.with(getApplicationContext())
                                    .using(new FirebaseImageLoader())
                                    .load(storageReftest)
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                            ImageView img = new ImageView(getApplicationContext());
                                            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600));
                                            //img.setMaxHeight(150);
                                            //img.setMaxWidth(150);
                                            img.setScaleType(TouchHighlightImageButton.ScaleType.CENTER_CROP);

                                            Bitmap targetBitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight());
                                            img.setImageBitmap(targetBitmap);

                                            /*TouchHighlightImageButton imgTHIB = new TouchHighlightImageButton(getApplicationContext());
                                            imgTHIB.setLayoutParams(new android.view.ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600));
                                            imgTHIB.setScaleType(TouchHighlightImageButton.ScaleType.CENTER_CROP);
                                            imgTHIB.setOnClickListener(imgTHIBlistener);

                                            Bitmap targetBitmapTHIB = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight());
                                            imgTHIB.setImageBitmap(targetBitmapTHIB);
                                            bm = targetBitmapTHIB;
                                            imgTHIB.setOnClickListener(imgTHIBlistener);*/

                                            bar.setVisibility(View.GONE);
                                            // Adds the view to the layout
                                            layout.addView(img);

                                            //layout.addView(imgTHIB);
                                        }
                                    });
                        }
                    }
                }
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

    public View.OnClickListener imgTHIBlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            zoomImageFromThumb(v, bm);
        }
    };


    public void AfficherSuppression(){

        databaseImages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getValue().toString());

                if (dataSnapshot.child("idUser").getValue().toString().equals(user.getUid().toString())) {

                    idVetement = dataSnapshot.getKey().toString();

                    idImages.add(idVetement);
                    txtVide.setVisibility(View.GONE);

                    Log.i("-------ID LISTE", idVetement.toString());
                }

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


        //img = (ImageView) findViewById(R.id.imgBaseDeDonnees);

        databaseImages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getValue().toString());
                bar.setVisibility(View.GONE);

                if (dataSnapshot.child("idUser").getValue().toString().equals(user.getUid().toString())) {


                    for(final String nb : idImages) {
                        if (dataSnapshot.getKey().toString().equals(nb.toString())) {

                            idVetement = dataSnapshot.getKey().toString();
                            nom = dataSnapshot.child("url").getValue().toString();
                            texte = dataSnapshot.child("type").getValue().toString();

                            Log.i("-------AHHHHHHAAA_URL", nom);
                            Log.i("-------texte", texte.toString());
                            Log.i("------idVetement", idVetement.toString());


                            storageReftest = storageRef.child("images").child(user.getUid()).child(nom);


                            Glide.with(getApplicationContext())
                                    .using(new FirebaseImageLoader())
                                    .load(storageReftest)
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                            ImageView img = new ImageView(getApplicationContext());
                                            img.setLayoutParams(new android.view.ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600));
                                            img.setMaxHeight(150);
                                            img.setMaxWidth(150);

                                            Bitmap targetBitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight());
                                            img.setImageBitmap(targetBitmap);

                                            // Adds the view to the layout
                                            layout.addView(img);
                                            btnSupprimer(nb.toString());
                                            //cpt++;
                                        }
                                    });
                        }

                    }



                }

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


    public void btnSupprimer(final String id){
        final Button btn = new Button(getApplicationContext());
        btn.setText("Supprimer");
        btn.setId(Integer.parseInt(id));

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                databaseImages.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Log.i("-------AHHHHHHHHHHHHAAA", dataSnapshot.getKey().toString());

                        if (dataSnapshot.getKey().toString().equals(id.toString())) {

                            dataSnapshot.getRef().removeValue();
                        }

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

                Toast.makeText(view.getContext(),
                        "Suppression de l'image " + id.toString(), Toast.LENGTH_SHORT)
                        .show();

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });


        layout.addView(btn);
    }

    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        //expandedImageView.setImageResource(imageResId);
        expandedImageView.setImageBitmap(imageResId);
        bm = null;

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.lL_Gal).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        expandedImageView.bringToFront();
        expandedImageView.setZ(10);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        expandedImageView.invalidate();
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        expandedImageView.invalidate();
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


}
