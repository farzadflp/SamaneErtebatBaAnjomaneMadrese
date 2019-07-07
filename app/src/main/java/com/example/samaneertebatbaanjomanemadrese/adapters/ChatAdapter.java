package com.example.samaneertebatbaanjomanemadrese.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Message> msgList;

    public ChatAdapter(@NonNull List<Message> msgList) {
        if (msgList == null){
            this.msgList = new ArrayList<>();
        }else {
            this.msgList = msgList;
        }
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row , parent , false);
        return new ChatViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(msgList.get(position));

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }



    public List<Message> getMsgList() {
        return msgList;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView msg , username ,date , time;
        AppCompatImageView avatar;
        ChatViewHolder(View itemView){
            super(itemView);
            msg = itemView.findViewById(R.id.msg_row_tv_msg);
            username = itemView.findViewById(R.id.msg_row_tv_username);
            date = itemView.findViewById(R.id.msg_row_tv_date);
            time = itemView.findViewById(R.id.msg_row_tv_time);
            avatar = itemView.findViewById(R.id.msg_row_avatar);
        }
        public void bind(Message message){
            msg.setText(message.getMsg());
            username.setText(message.getUsername());
            time.setText(" ");
            date.setText(message.getDateTimeMsg());
            avatar.setImageResource(R.drawable.user);

        }
    }
}
