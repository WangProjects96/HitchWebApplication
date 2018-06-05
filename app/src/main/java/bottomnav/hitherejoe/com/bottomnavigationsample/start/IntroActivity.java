package bottomnav.hitherejoe.com.bottomnavigationsample.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

public class IntroActivity extends AppIntro {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getString(R.string.t1), getString(R.string.d1), R.drawable.slide1, ContextCompat.getColor(getBaseContext(), R.color.s1)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.t2), getString(R.string.d2), R.drawable.slide2, ContextCompat.getColor(getBaseContext(), R.color.s2)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.t3), getString(R.string.d3), R.drawable.slide3, ContextCompat.getColor(getBaseContext(), R.color.s3 )));

        showSkipButton(true);
        setProgressButtonEnabled(true);
        setFadeAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(IntroActivity.this,LoginActivity.class));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(IntroActivity.this,LoginActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}
