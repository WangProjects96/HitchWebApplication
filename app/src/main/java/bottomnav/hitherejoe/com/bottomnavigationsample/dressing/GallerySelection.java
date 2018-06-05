package bottomnav.hitherejoe.com.bottomnavigationsample.dressing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bottomnav.hitherejoe.com.bottomnavigationsample.R;

public class GallerySelection extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.test_gallery_selection_layout, container, false);

        Button btnHauts = (Button) rootView.findViewById(R.id.buttonHauts);
        Button btnBas = (Button) rootView.findViewById(R.id.buttonBas);
        Button btnChaussures = (Button) rootView.findViewById(R.id.buttonChaussures);
        Button btnSupprimer = (Button) rootView.findViewById(R.id.buttonSupprimer);

        final Intent intent = new Intent(getActivity(), GalleryActivity.class);

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Choix", "Supprimer");
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnHauts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Choix", "Hauts");
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Choix", "Bas");
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnChaussures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Choix", "Chaussures");
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }
}
