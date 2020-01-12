package com.example.orthodoxapp.ui.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.orthodoxapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DialogFragment extends Fragment {

    private DialogViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog, container, false);

        ImageButton btnSendMsg = root.findViewById(R.id.btn_send_msg);
        EditText etMsg = root.findViewById(R.id.et_my_msg);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etMsg.getText().toString();




                //clean
                etMsg.setText("");
            }
        });


        return root;
    }
}
