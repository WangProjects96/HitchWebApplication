package bottomnav.hitherejoe.com.bottomnavigationsample.accueil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

public class WeatherAtivityFragment extends Fragment {

    Fragment fragment;
    public WeatherAtivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final View rootView = inflater.inflate(R.layout.fragment_weather_ativity, container, false);

       /*   final FrameLayout bNV = (FrameLayout) rootView.findViewById(R.id.container);
        fragment.getView().setBackgroundColor(getResources().getColor(rootView.bNV,blue1));*/
        return rootView;
    }
}
