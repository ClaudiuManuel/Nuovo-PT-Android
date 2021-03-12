package com.example.nuovo_pt.ui.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.nuovo_pt.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    Button registerButton;
    EditText firstName;
    EditText lastName;
    EditText registerEmail;
    EditText registerPassword;
    ProgressBar registerProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_tab_fragment, container, false);

        registerEmail = view.findViewById(R.id.email_register);
        registerPassword = view.findViewById(R.id.password_register);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        registerProgressBar = view.findViewById(R.id.register_progressbar);
        registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                registerUser();

        }
    }
    private void registerUser() {
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String firstNameString = firstName.getText().toString().trim();
        String lastNameString = lastName.getText().toString().trim();

        if(firstNameString.isEmpty()) {
            firstName.setError("First name is required");
            firstName.requestFocus();
            return;
        }

        if(lastNameString.isEmpty()) {
            lastName.setError("Last name is required");
            lastName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            registerEmail.setError("Email is required");
            registerEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("Please provide a valid email");
            registerEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            registerPassword.setError("Password is required");
            registerPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            registerPassword.setError("Passwords must be at least 6 characters long");
            registerPassword.requestFocus();
            return;
        }

        registerProgressBar.setVisibility(View.VISIBLE);
        
    }
}
