package com.example.nuovo_pt.ui.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();
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
        if(v != null && v == registerButton)
            registerUser();
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
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(firstNameString,lastNameString,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Toast.makeText(getContext(),"User has been registered succesfully!",Toast.LENGTH_LONG).show();
                                        registerProgressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getContext(),"Failed to register!",Toast.LENGTH_LONG).show();
                                        registerProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(),"Failed to register the user!",Toast.LENGTH_LONG).show();
                            registerProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
        
    }
}
