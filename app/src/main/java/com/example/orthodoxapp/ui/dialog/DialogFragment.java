package com.example.orthodoxapp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFragment extends Fragment {

    private DialogViewModel mViewModel;
    private MyRecyclerViewDialogAdapter dialogAdapter;
    private Message mMessageFromBundle;

    private FirebaseUser firebaseUser;
    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog, container, false);

        ImageButton btnSendMsg = root.findViewById(R.id.btn_send_msg);
        EditText etMsg = root.findViewById(R.id.et_my_msg);

        assert getArguments() != null;
        mMessageFromBundle = getArguments().getParcelable("msg");

        initializeDialog();



        btnSendMsg.setOnClickListener(v -> {
            String msg = etMsg.getText().toString();
//            FirebaseDatabase.getInstance().getReference().push()
//                    .setValue(new Message())
            Message senderMsg = Message.builder()
                    .senderUid(firebaseUser.getUid()).addresseeUid(mMessageFromBundle.getAddresseeUid())
                    .textMessage(msg).build();

            database.push().setValue(senderMsg);


            //clean
            etMsg.setText("");
        });

        return root;
    }

    private void initializeDialog() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        database = FirebaseDatabase.getInstance()
                .getReference("users").child(firebaseUser.getUid()).child("dialogs");

    }
}
