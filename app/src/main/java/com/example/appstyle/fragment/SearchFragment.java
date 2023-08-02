package com.example.appstyle.fragment;

import android.content.Intent;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstyle.api.StringCallback;
import com.example.appstyle.api.ExerciseApiService;
import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class SearchFragment extends Fragment implements StringCallback {

    private ImageView logout_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        InicializarCampos(rootView);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        LinearLayout containerLayout = rootView.findViewById(R.id.div);

        // Define o número de views que você deseja criar
        int numberOfViews = 5;

        for (int i = 0; i < numberOfViews; i++) {
            // Crie uma nova TextView programaticamente
            TextView textView = new TextView(requireContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("Texto da view " + (i + 1));
            textView.setTextSize(16); // Ajuste o tamanho do texto conforme necessário
            textView.setTextColor(Color.BLACK);
            // Adicione a TextView ao contêiner
            containerLayout.addView(textView);
        }
        ExerciseApiService exerciseApiService = new ExerciseApiService();
        exerciseApiService.getListOfBodyParts(new StringCallback() {
            @Override
            public void callbacK(String result) {

                Gson gson = new Gson();
                String[] bodyPartsArray = gson.fromJson(result, String[].class);

                Drawable drawable = getResources().getDrawable(R.drawable.card_search);

                for (int i = 0; i < bodyPartsArray.length; i++) {

                    TextView textView = new TextView(requireContext());
                    textView.setBackground(drawable);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextSize(20);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 20, 0, 0);

                    textView.setLayoutParams(params);

                    if (bodyPartsArray != null && bodyPartsArray.length > 0) {
                        String firstBodyPart = bodyPartsArray[i];
                        String primeiraLetraMaiuscula = firstBodyPart.substring(0, 1).toUpperCase();
                        String restanteTexto = firstBodyPart.substring(1);
                        String textoComPrimeiraLetraMaiuscula = primeiraLetraMaiuscula + restanteTexto;
                        textView.setText(textoComPrimeiraLetraMaiuscula);
                    }

                    containerLayout.addView(textView);
                }
            }
        });
        return rootView;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void InicializarCampos(View rootView) {
        logout_button = rootView.findViewById(R.id.logout);
    }


    @Override
    public void callbacK(String value) {

    }
}
