package com.example.myapplication;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.databinding.FragmentFirstBinding;

import java.util.Locale;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.forgotPassword.setVisibility(View.INVISIBLE);
        EditText tmp1 = binding.usrTextEdit;
        EditText tmp2 = binding.pwTextEdit;
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tmp1.getText().toString().toLowerCase(Locale.ROOT).equals("admin")){
                    binding.forgotPassword.setVisibility(View.VISIBLE);
                }else if(!tmp2.getText().toString().equals("secret")){
                    binding.forgotPassword.setVisibility(View.VISIBLE);
                }else if(tmp1.getText().toString().toLowerCase(Locale.ROOT).equals("piso 1")){
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_ThirdFragment);
                }else{
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}