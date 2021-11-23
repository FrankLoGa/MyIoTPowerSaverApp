package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.myapplication.databinding.FragmentThirdBinding;
import com.ubidots.ApiClient;
import com.ubidots.Variable;

public class ThirdFragment extends Fragment {
    private @NonNull FragmentThirdBinding binding;

    private TextView mCurrentLevel;
    private static final String CURRENT_LEVEL = "level";
    private final BroadcastReceiver mCurrentyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(CURRENT_LEVEL, 0);
            mCurrentLevel.setText(Integer.toString(level) + "%");

            new ThirdFragment.ApiUbidots().execute(level);
        }
    };
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    ) {

        binding = com.example.myapplication.databinding.FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentLevel = binding.ubiMtv2;
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }
        });
    }



    public class ApiUbidots extends AsyncTask<Integer, Void, Void> {
        private final String API_KEY = "BBFF-f168207a80f4c4a2d7b5908eb0152f82ad3";
        private final String VARIABLE_ID = "61847a117eab7f6cefe48194";

        @Override
        protected Void doInBackground(Integer... params) {
            ApiClient apiClient = new ApiClient(API_KEY);
            Variable current = apiClient.getVariable(VARIABLE_ID);

            current.saveValue(params[0] * .30);
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
