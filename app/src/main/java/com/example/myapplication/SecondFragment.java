package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.databinding.FragmentSecondBinding;

import com.ubidots.ApiClient;
import com.ubidots.Value;
import com.ubidots.Variable;

import java.util.Objects;

public class SecondFragment extends Fragment {

private FragmentSecondBinding binding;

    private TextView mCurrentLevel;
    private static final String CURRENT_LEVEL = "level";
    private BroadcastReceiver mCurrentyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(CURRENT_LEVEL, 0);
            mCurrentLevel.setText(Integer.toString(level) + "%");

            new ApiUbidots().execute(level);
        }
    };
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    ) {

      binding = FragmentSecondBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentLevel = binding.ubiMtv1;
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.buttonFiftth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);
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

            current.saveValue(params[0]);
            return null;
        }
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}