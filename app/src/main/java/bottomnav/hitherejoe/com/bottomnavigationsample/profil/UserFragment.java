package bottomnav.hitherejoe.com.bottomnavigationsample.profil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import bottomnav.hitherejoe.com.bottomnavigationsample.start.LoginActivity;
import bottomnav.hitherejoe.com.bottomnavigationsample.MainActivity;
import bottomnav.hitherejoe.com.bottomnavigationsample.R;
import bottomnav.hitherejoe.com.bottomnavigationsample.librairies.TouchHighlightImageButton;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserFragment extends Fragment {
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private int tHIBID;
    private static DatabaseReference mDatabase;
    private static FirebaseUser loggedUser;
    private static FirebaseAuth firebaseAuth;
    private TouchHighlightImageButton haut1, haut2, bas, chaussures, manteau;

    private DatabaseReference dbRef;
    private DatabaseReference tenueJourRef;

    private StorageReference refStorage;

    private StorageReference userRef;



    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        loggedUser = firebaseAuth.getCurrentUser();

        refStorage = FirebaseStorage.getInstance().getReference();
        userRef = refStorage.child("images").child(loggedUser.getUid());

        dbRef = FirebaseDatabase.getInstance().getReference();
        tenueJourRef = dbRef.child("tenues").child(loggedUser.getUid());

        final TextView deconnexion = (TextView) rootView.findViewById(R.id.deconnexion);
        ImageButton sB = (ImageButton) rootView.findViewById(R.id.settingsButton);
        haut1 = (TouchHighlightImageButton) rootView.findViewById(R.id.imageJourHaut1);
        haut2 = (TouchHighlightImageButton) rootView.findViewById(R.id.imageJourHaut2);
        bas = (TouchHighlightImageButton) rootView.findViewById(R.id.imageJourBas);
        chaussures = (TouchHighlightImageButton) rootView.findViewById(R.id.imageJourChaussures);
        manteau = (TouchHighlightImageButton) rootView.findViewById(R.id.imageJourManteau);

        tenueJourRef.child("haut1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userRef.child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("endd",uri.toString());
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(haut1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("retrieve","nope");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tenueJourRef.child("haut2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userRef.child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("endd",uri.toString());
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(haut2);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("retrieve","nope");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tenueJourRef.child("bas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userRef.child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("endd",uri.toString());
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(bas);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("retrieve","nope");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tenueJourRef.child("chaussure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userRef.child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("endd",uri.toString());
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(chaussures);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("retrieve","nope");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tenueJourRef.child("manteau").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userRef.child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("endd",uri.toString());
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(manteau);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("retrieve","nope");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        fragmentManager = getFragmentManager();
        sB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settingsButton:
                        fragment = new ParametresFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        transaction.replace(R.id.rL_User, fragment).commit();
                        hideKeyboard(getActivity());
                        break;
                }
            }
        });

        haut1.setOnClickListener(THIBlistener);
        haut2.setOnClickListener(THIBlistener);
        bas.setOnClickListener(THIBlistener);
        manteau.setOnClickListener(THIBlistener);
        chaussures.setOnClickListener(THIBlistener);

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                hideKeyboard(getActivity());
            }
        });

        /*LinearLayout lL = (LinearLayout)rootView.findViewById(R.id.linearContent);
        tHIBID = 0;
        for (int i = 0; i < 8; i++) {
            RelativeLayout rL_uf = new RelativeLayout(getActivity());
            RelativeLayout.LayoutParams rL_uf_p = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

            HorizontalScrollView hSV_uf = new HorizontalScrollView(getActivity());
            RelativeLayout.LayoutParams hSV_uf_p = new RelativeLayout.LayoutParams(
                    HorizontalScrollView.LayoutParams.MATCH_PARENT,
                    HorizontalScrollView.LayoutParams.MATCH_PARENT);
            hSV_uf_p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            hSV_uf_p.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

            LinearLayout lL_uf = new LinearLayout(getActivity());
            RelativeLayout.LayoutParams lL_uf_p = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            lL_uf_p.addRule(LinearLayout.HORIZONTAL, RelativeLayout.TRUE);

            for (int j = 0; j < 5; j++) {
                final TouchHighlightImageButton tHIB = new TouchHighlightImageButton(getActivity());
                final float scale = getResources().getDisplayMetrics().density;
                int size  = (int) (100 * scale);
                LinearLayout.LayoutParams tHIB_p = new LinearLayout.LayoutParams(size, size);
                tHIB_p.weight = 1;
                tHIB.setId(tHIBID);
                tHIB.setScaleType(TouchHighlightImageButton.ScaleType.FIT_XY);
                tHIB.setImageResource(R.mipmap.ic_launcher);
                tHIB.setOnClickListener(THIBlistener);

                lL_uf.addView(tHIB, tHIB_p);
                ++tHIBID;
            }
            lL_uf.setId(i + 1);
            hSV_uf.addView(lL_uf, lL_uf_p);
            rL_uf.addView(hSV_uf, hSV_uf_p);
            lL.addView(rL_uf, rL_uf_p);
            ++tHIBID;
        }*/

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loggedUser = firebaseAuth.getCurrentUser();

        final ImageView imageProfile = (ImageView) rootView.findViewById(R.id.pp);
        mDatabase.child("users").child(loggedUser.getUid()).child("profilePic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Picasso.with(getActivity()).load(dataSnapshot.getValue().toString()).into(imageProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public View.OnClickListener THIBlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable d;
            switch (v.getId()) {
                case R.id.imageJourHaut1:
                    d = haut1.getDrawable();
                    break;
                case R.id.imageJourHaut2:
                    d = haut2.getDrawable();
                    break;
                case R.id.imageJourBas:
                    d = bas.getDrawable();
                    break;
                case R.id.imageJourChaussures:
                    d = chaussures.getDrawable();
                    break;
                case R.id.imageJourManteau:
                    d = manteau.getDrawable();
                    break;
                default:
                    d = getResources().getDrawable(R.mipmap.ic_launcher);
                    break;
            }
            zoomImageFromThumb(v, d);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), MainActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void zoomImageFromThumb(final View thumbView, Drawable imageResId) {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) getActivity().findViewById(R.id.expanded_image);
        expandedImageView.setImageDrawable(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        getActivity().findViewById(R.id.rL_User).getGlobalVisibleRect(finalBounds, globalOffset);
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

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

