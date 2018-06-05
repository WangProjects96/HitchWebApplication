package bottomnav.hitherejoe.com.bottomnavigationsample.dressing;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

public class DressingFragment extends Fragment {

    public DressingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_dressing, container, false);

        final RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.rL_Dressing);

        final TextView ajoutTV = (TextView) rootView.findViewById(R.id.tv_Haut);
        final TextView libTV = (TextView) rootView.findViewById(R.id.tv_Bas);

        final ImageButton ajoutButton = (ImageButton) rootView.findViewById(R.id.aButton);
        final ImageButton libButton = (ImageButton) rootView.findViewById(R.id.lButton);

        ajoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.aButton:
                        animateLayoutHaut();
                        libTV.setVisibility(View.GONE);
                        ajoutButton.setVisibility(View.GONE);
                        libButton.setVisibility(View.GONE);
                        ajoutTV.setVisibility(View.GONE);
                        Fragment fragment = new AjouterVetementFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in_slow, R.anim.fade_out_slow);
                        transaction.replace(R.id.rL_Dressing, fragment).commit();
                        break;
                }
            }
        });

        libButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.lButton:
                        animateLayoutBas();
                        libTV.setVisibility(View.GONE);
                        ajoutTV.setVisibility(View.GONE);
                        ajoutButton.setVisibility(View.GONE);
                        libButton.setVisibility(View.GONE);
                        Fragment fragment = new GallerySelection();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in_slow2, R.anim.fade_out_slow2);
                        transaction.replace(R.id.rL_Dressing, fragment).commit();
                        break;
                }
            }
        });

        final View ci1 = rootView.findViewById(R.id.c1);
        final View ci2 = rootView.findViewById(R.id.c2);
        final View ci3 = rootView.findViewById(R.id.c3);
        final View ci4 = rootView.findViewById(R.id.c4);
        final View ci5 = rootView.findViewById(R.id.c5);
        final View ci6 = rootView.findViewById(R.id.c6);
        final View ci7 = rootView.findViewById(R.id.c7);
        final View ci8 = rootView.findViewById(R.id.c8);
        final View ci9 = rootView.findViewById(R.id.c9);
        final View ci10 = rootView.findViewById(R.id.c10);
        final View ci11 = rootView.findViewById(R.id.c11);
        final View ci12 = rootView.findViewById(R.id.c12);

        changeColor(ci1);
        changeColor(ci2);
        changeColor(ci3);
        changeColor(ci4);
        changeColor(ci5);
        changeColor(ci6);
        changeColor(ci7);
        changeColor(ci8);
        changeColor(ci9);
        changeColor(ci10);
        changeColor(ci11);
        changeColor(ci12);

        return rootView;
    }

    private void animateLayoutHaut() {
        /*RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rL_Dressing_Haut);
        relativeLayout.bringToFront();
        ScaleAnimation scale = new ScaleAnimation((float)1.0, (float)1.0, (float)1.0, (float)1.65);
        scale.setFillAfter(true);
        scale.setDuration(500);
        relativeLayout.startAnimation(scale);*/
        RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rL_Dressing_Bas);
        Animation anim = new ScaleAnimation(1f, 1f, (float)1.0, (float)0.0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true);
        anim.setDuration(800);
        relativeLayout.startAnimation(anim);
    }

    public void animateLayoutBas() {
        RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rL_Dressing_Bas);
        Animation anim = new ScaleAnimation(1f, 1f, (float)1.0, (float)3.0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true);
        anim.setDuration(600);
        relativeLayout.startAnimation(anim);
    }

    public void changeColor(View v) {
        Drawable background = v.getBackground();
        switch (v.getId()) {
            case R.id.c1:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c1));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c1));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable)background).setColor(getResources().getColor(R.color.c1));
                }
                break;

            case R.id.c2:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c2));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c2));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c2));
                }
                break;
            case R.id.c3:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c3));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c3));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c3));
                }
                break;
            case R.id.c4:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c4));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c4));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c4));
                }
                break;
            case R.id.c5:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c5));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c5));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c5));
                }
                break;
            case R.id.c6:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c6));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c6));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c6));
                }
                break;
            case R.id.c7:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c7));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c7));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c7));
                }
                break;
            case R.id.c8:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c8));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c8));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c8));
                }
                break;
            case R.id.c9:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c9));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c9));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c9));
                }
                break;
            case R.id.c10:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c10));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c10));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c10));
                }
                break;
            case R.id.c11:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c11));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c11));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c11));
                }
                break;

            case R.id.c12:
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.c12));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(getResources().getColor(R.color.c12));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable) background).setColor(getResources().getColor(R.color.c12));
                }
                break;
        }
    }
}
