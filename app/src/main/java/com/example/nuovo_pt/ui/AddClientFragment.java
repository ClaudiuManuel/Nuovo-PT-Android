package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.nuovo_pt.R;
import com.example.nuovo_pt.db.clients.ClientFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClientFragment extends Fragment implements View.OnClickListener {
    private EditText clientNameEditText;
    private RadioGroup clientSexRadioGrup;
    NavController navController;
    private boolean isMale=true;
    private Button addNewClientButton;
    private Button cancelNewClientButton;
    private DatabaseReference databaseReference;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_client, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Clients");
        clientNameEditText = view.findViewById(R.id.editTextPersonName);
        clientSexRadioGrup = view.findViewById(R.id.radioGroup);
        addNewClientButton = view.findViewById(R.id.confirm_client_addition);
        cancelNewClientButton = view.findViewById(R.id.cancel_client_addition);
        addNewClientButton.setOnClickListener(this);
        cancelNewClientButton.setOnClickListener(this);

        clientSexRadioGrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_button_masculin:
                        isMale = true;
                        break;
                    case R.id.radio_button_feminin:
                        isMale = false;
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == addNewClientButton) {
            String clientName = clientNameEditText.getText().toString();
            if(clientName.length() > 0) {
                if (isMale) {
                    ClientFirebase clientFirebase = new ClientFirebase(clientName,true);
                    databaseReference.child(clientName).setValue(clientFirebase);
                } else {
                    ClientFirebase clientFirebase = new ClientFirebase(clientName,false);
                    databaseReference.child(clientName).setValue(clientFirebase);
                }
                Toast feedback = Toast.makeText(getContext(), "Client added successfully:  " + clientName, Toast.LENGTH_LONG);
                feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
                feedback.show();
                getActivity().onBackPressed();
            }
        } else if(v == cancelNewClientButton) {
            getActivity().onBackPressed();
        }
    }
    
}
