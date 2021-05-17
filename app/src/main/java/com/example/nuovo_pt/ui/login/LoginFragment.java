package com.example.nuovo_pt.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nuovo_pt.MainActivity;
import com.example.nuovo_pt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText email;
    EditText password;
    TextView forgotPass;
    Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private float alpha = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.email_login);
        password = view.findViewById(R.id.password_login);
        forgotPass = view.findViewById(R.id.forgot_password);
        loginButton = view.findViewById(R.id.login_button);
        progressBar = view.findViewById(R.id.login_progressbar);
        loginButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v != null && v == loginButton)
            loginUser();
        else if (v != null && v == forgotPass) {

        }
    }

    private void loginUser() {
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

        if(emailString.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if(passwordString.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(password.length() < 6) {
            password.setError("Passwords must be at least 6 characters long");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                    if(user.isEmailVerified()) {
//                        startActivity(new Intent(getActivity(), MainActivity.class));
//                        progressBar.setVisibility(View.GONE);
//                    } else {
//                        user.sendEmailVerification();
//                        Toast.makeText(getContext(), "Check your email to verify your account", Toast.LENGTH_SHORT).show();
//                    }
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("userEmail",emailString);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(),"Failed to login!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}
