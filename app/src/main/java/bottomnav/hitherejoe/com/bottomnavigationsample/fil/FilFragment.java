package bottomnav.hitherejoe.com.bottomnavigationsample.fil;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FilFragment extends Fragment {
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Button sign_out_button;
    private TextView user;
    private ImageButton addB;
    private Button aParamButton;
    private RecyclerView mPosts_list;

    private FirebaseAuth authentificationFireBase;

    private static StorageReference refStorage;
    private static DatabaseReference mDatabase;
    private static DatabaseReference postRef;
    private static DatabaseReference likeRef;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser loggedUser;
    private boolean likeClick = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fil, container, false);

        refStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        postRef = FirebaseDatabase.getInstance().getReference().child("Post");
        likeRef = FirebaseDatabase.getInstance().getReference().child("Like");

        firebaseAuth = FirebaseAuth.getInstance();
        loggedUser = firebaseAuth.getCurrentUser();

        RecyclerView rV = (RecyclerView) rootView.findViewById(R.id.posts_list);



        //this.sign_out_button = (Button) findViewById(R.id.Blogout);
        //this.user = (TextView) findViewById(R.id.user);

        this.addB = (ImageButton) rootView.findViewById(R.id.add_button);
        this.mPosts_list = (RecyclerView) rootView.findViewById(R.id.posts_list);

        this.mPosts_list.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        this.mPosts_list.setLayoutManager(manager);

        fragmentManager = getFragmentManager();
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add_button:
                        fragment = new CreerPostFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.activity_F, fragment).commit();
                        break;
                }
            }
        });
        /*aParamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.paramButton:
                        fragment = new ParametresFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.activity_F, fragment).commit();
                        break;
                }
            }
        });*/
        this.authentificationFireBase = FirebaseAuth.getInstance();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Post,PostsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostsViewHolder>(
                Post.class,
                R.layout.fragment_post,
                PostsViewHolder.class,
                postRef
        ) {
            @Override
            protected void populateViewHolder(PostsViewHolder viewHolder, Post model, int position) {

                final String clé_post = getRef(position).getKey();
                viewHolder.setTitre(model.getTitre());
                viewHolder.setCommentaire(model.getCommentaire());
                viewHolder.setUser(model.getUser());
                viewHolder.setPost_time((long) model.getPost_time());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setaLikeButton(clé_post);
                viewHolder.setPostNumberLikes(clé_post);
                viewHolder.setPostProfile(clé_post);

                viewHolder.aLikeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        likeClick = true;

                        likeRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(likeClick){
                                    //n'existe pas
                                    if(dataSnapshot.child(clé_post).hasChild(loggedUser.getUid())){

                                        likeRef.child(clé_post).child(loggedUser.getUid()).removeValue();
                                        likeClick = false;

                                    }else{
                                        // not liked
                                        likeRef.child(clé_post).child(loggedUser.getUid()).setValue("true");
                                        likeClick = false;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                });
            }
        };

        mPosts_list.setAdapter(firebaseRecyclerAdapter);

    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder{
        View vue;
        ImageButton aLikeButton;
        ImageView profilePic ;

        public PostsViewHolder(View itemView) {
            super(itemView);
            vue= itemView ;
            aLikeButton = (ImageButton) vue.findViewById(R.id.postLikeImage);
            profilePic =  (ImageView) vue.findViewById(R.id.idProfilePicPost);
        }
        public void setTitre(String titre){
            TextView titre_post = (TextView) vue.findViewById(R.id.postTitle);
            titre_post.setText(titre);
        }

        public void setCommentaire(String commentaire){
            TextView titre_post = (TextView) vue.findViewById(R.id.postComment);
            titre_post.setText(commentaire);
        }

        public void setUser(String user){

            mDatabase.child("users").child(user).child("pseudo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TextView titre_post = (TextView) vue.findViewById(R.id.postUser);
                    titre_post.setText(dataSnapshot.getValue().toString());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        public void setPost_time(long d){
            TextView titre_post = (TextView) vue.findViewById(R.id.postDate);

            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(d);

            SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            fmt.setCalendar(cal);
            String dateFormatted = fmt.format(cal.getTime());

            titre_post.setText(dateFormatted);
        }

        public void setImage(Context context, String image){
            ImageView image_post = (ImageView) vue.findViewById(R.id.postImage);
            Picasso.with(context).load(image).into(image_post);
        }

        public  void setaLikeButton(final String post_key){
            likeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(loggedUser.getUid())){
                        aLikeButton.setImageResource(R.drawable.heart);
                    }else{
                        aLikeButton.setImageResource(R.drawable.heart_outline);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        public  void setPostNumberLikes(final String post_key){

            likeRef.child(post_key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TextView like_number = (TextView) vue.findViewById(R.id.postNumberLikes);
                    like_number.setText(dataSnapshot.getChildrenCount()+" Likes");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        public  void setPostProfile(final String post_key){
            postRef.child(post_key).child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {



                    refStorage.child("ProfileImages").child(dataSnapshot.getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getApplicationContext())
                                    .load(uri)
                                    .into(profilePic);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}
