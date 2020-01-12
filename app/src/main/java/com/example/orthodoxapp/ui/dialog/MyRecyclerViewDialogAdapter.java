package com.example.orthodoxapp.ui.dialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyRecyclerViewDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ME = 10001;
    private static final int VIEW_TYPE_THEIR = 10002;
    private List<Message> itemMsg;

    public MyRecyclerViewDialogAdapter(List<Message> itemMsg) {
        this.itemMsg = itemMsg;
    }

    public void setData(List<Message> data){
        itemMsg.clear();
        itemMsg.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewDialogMy = inflater.inflate(R.layout.item_dialog_my_msg, parent, false);
                viewHolder = new ViewHolderMyDialog(viewDialogMy);
                break;
            case VIEW_TYPE_THEIR:
                View viewDialogTheir = inflater.inflate(R.layout.item_dialog_their_msg, parent, false);
                viewHolder = new ViewHolderTheirDialog(viewDialogTheir);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (TextUtils.equals(itemMsg.get(position).getSender().getId(), FirebaseAuth.getInstance().getUid())) {
            configureMyChatViewHolder((ViewHolderMyDialog) holder, position);
        } else {
            configureTheirChatViewHolder((ViewHolderTheirDialog)holder, position);
        }
    }

    private void configureMyChatViewHolder(final ViewHolderMyDialog viewHolderMyDialog, int position) {
        Message msg = itemMsg.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm   MM/dd/yyyy");
        String date = sdf.format(msg.getMessageDate().getTime());
        viewHolderMyDialog.tvMsgTime.setText(date);
        viewHolderMyDialog.tvMsgBody.setText(msg.getTextMessage());

    }

    private void configureTheirChatViewHolder(final ViewHolderTheirDialog viewHolderTheirDialog, int position) {
        Message msg = itemMsg.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm   MM/dd/yyyy");
        String date = sdf.format(msg.getMessageDate().getTime());
        viewHolderTheirDialog.tvMsgTime.setText(date);
        viewHolderTheirDialog.tvFSName.setText(msg.getSender().getName());
        viewHolderTheirDialog.tvMsgBody.setText(msg.getTextMessage());
        //viewHolderTheirDialog.viewAva.setBackground(msg.getSender().getPhoto());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(itemMsg.get(position).getAddressee().getId(),
                (FirebaseAuth.getInstance().getCurrentUser()).getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_THEIR;
        }
    }

    public class ViewHolderMyDialog extends RecyclerView.ViewHolder {

        private TextView tvMsgBody, tvMsgTime;

        public ViewHolderMyDialog(@NonNull View itemView) {
            super(itemView);
            tvMsgBody = itemView.findViewById(R.id.tv_my_msg_body);
            tvMsgTime = itemView.findViewById(R.id.tv_my_msg_time);
        }
    }

    public class ViewHolderTheirDialog extends RecyclerView.ViewHolder {

        private TextView tvMsgBody, tvFSName, tvMsgTime;
        private View viewAva;

        public ViewHolderTheirDialog(@NonNull View itemView) {
            super(itemView);
            tvMsgBody = itemView.findViewById(R.id.tv_their_message_body);
            tvFSName = itemView.findViewById(R.id.tv_fsName_their_msg);
            viewAva = itemView.findViewById(R.id.view_their_avatar);
            tvMsgTime = itemView.findViewById(R.id.tv_their_msg_time);
        }
    }
}

