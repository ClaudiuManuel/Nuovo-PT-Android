package com.example.nuovo_pt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.nuovo_pt.db.clients.Client;
import com.example.nuovo_pt.ClientsAdditionListener;
import com.example.nuovo_pt.R;

public class AddClientFragment extends Fragment implements View.OnClickListener {
    private EditText clientNameEditText;
    private RadioGroup clientSexRadioGrup;
    private boolean isMale=true;
    private Button addNewClientButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_client, container, false);
        clientNameEditText = view.findViewById(R.id.editTextPersonName);
        clientSexRadioGrup = view.findViewById(R.id.radioGroup);
        addNewClientButton = view.findViewById(R.id.confirm_client_addition);
        addNewClientButton.setOnClickListener(this);

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
            }
        }
    }
    
}
