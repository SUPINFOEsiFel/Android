package com.esilyon.fel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HousingFragment extends android.support.v4.app.Fragment {

    public HousingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_housing, container, false);
        Button Housing1Btn = (Button) view.findViewById(R.id.HousingBtn1);
        Housing1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HousingActivity.class);
                intent.putExtra("title", getString(R.string.Studélite_Housing1));
                intent.putExtra("link", getString(R.string.Studélite_Housing1_link));
                getActivity().startActivity(intent);
            }
        });

        setMenuVisibility(false);

        Button Housing2Btn = (Button) view.findViewById(R.id.HousingBtn2);
        Housing2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HousingActivity.class);
                intent.putExtra("title", getString(R.string.Studélite_Housing2));
                intent.putExtra("link", getString(R.string.Studélite_Housing2_link));
                getActivity().startActivity(intent);
            }
        });

        Button Housing3Btn = (Button) view.findViewById(R.id.HousingBtn3);
        Housing3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HousingActivity.class);
                intent.putExtra("title", getString(R.string.Studélite_Housing3));
                intent.putExtra("link", getString(R.string.Studélite_Housing3_link));
                getActivity().startActivity(intent);
            }
        });

        Button Housing4Btn = (Button) view.findViewById(R.id.HousingBtn4);
        Housing4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HousingActivity.class);
                intent.putExtra("title", getString(R.string.Studélite_Housing4));
                intent.putExtra("link", getString(R.string.Studélite_Housing4_link));
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
