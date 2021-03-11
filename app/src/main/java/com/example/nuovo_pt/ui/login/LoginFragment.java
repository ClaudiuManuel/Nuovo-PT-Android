package com.example.nuovo_pt.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.ClientViewModel;
import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.ui.GridSpacing;

import java.util.List;

public class LoginFragment extends Fragment {
    EditText email;
    EditText password;
    TextView forgotPass;
    Button loginButton;
    private float alpha = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = view.findViewById(R.id.email_login);
        password = view.findViewById(R.id.password_login);
        forgotPass = view.findViewById(R.id.forgot_password);
        loginButton = view.findViewById(R.id.login_button);

        email.setTranslationX(800);
        password.setTranslationX(800);
        forgotPass.setTranslationX(800);
        loginButton.setTranslationX(800);

        email.setAlpha(alpha);
        password.setAlpha(alpha);
        forgotPass.setAlpha(alpha);
        loginButton.setAlpha(alpha);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return view;
    }
}
