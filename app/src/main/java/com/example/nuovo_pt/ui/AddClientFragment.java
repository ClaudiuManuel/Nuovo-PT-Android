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

import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.ClientsAdditionListener;
import com.example.nuovo_pt.R;
import com.google.android.material.snackbar.Snackbar;

public class AddClientFragment extends Fragment implements View.OnClickListener {
    private EditText clientNameEditText;
    private RadioGroup clientSexRadioGrup;
    NavController navController;
    private boolean isMale=true;
    private Button addNewClientButton;
    private Button cancelNewClientButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_client, container, false);
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
            ClientsAdditionListener clientsAdditionListener = (ClientsAdditionListener) getActivity();
            String clientName = clientNameEditText.getText().toString();
            System.out.println(clientName + "   on click add client");
            if(clientName.length() > 0) {
                if (isMale)
                    clientsAdditionListener.addClient(new Client(clientName, 1));
                else
                    clientsAdditionListener.addClient(new Client(clientName, 0));
                clientNameEditText.setText("");
                Toast feedback = Toast.makeText(getContext(), "Client added successfully:  " + clientName, Toast.LENGTH_LONG);
                feedback.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 230);
                feedback.show();
            }
        } else if(v == cancelNewClientButton) {
            getActivity().onBackPressed();
        }
    }
    
}
