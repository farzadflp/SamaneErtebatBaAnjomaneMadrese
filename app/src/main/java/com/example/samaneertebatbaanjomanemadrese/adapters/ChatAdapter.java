package com.example.samaneertebatbaanjomanemadrese.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.R;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Message> msgList;
    private int selectedPosition=-1;

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
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#ffa800"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ECF0F1"));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                Context context =holder.itemView.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNeutralButton(R.string.send_to, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do sendto
                    }
                })
                        .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView msg , username ,date , time;
        AppCompatImageView avatar;
        ChatViewHolder(View itemView){
            super(itemView);
            msg = (AppCompatTextView) itemView.findViewById(R.id.msg_row_tv_msg);
            username = (AppCompatTextView) itemView.findViewById(R.id.msg_row_tv_username);
            date = (AppCompatTextView) itemView.findViewById(R.id.msg_row_tv_date);
            time = (AppCompatTextView) itemView.findViewById(R.id.msg_row_tv_time);
            avatar = (AppCompatImageView) itemView.findViewById(R.id.msg_row_avatar);
        }
        public void bind(Message message){
            msg.setText(message.getMsg());
            username.setText(message.getUsername());
            time.setText(" ");
            date.setText(message.getDateTimeMsg());
            avatar.setImageResource(R.drawable.user);

            /*
            msg.setText(message.getMsg());
            avatar.setImageResource(R.drawable.user);
            username.setText(message.getUsername());
            time.setText(message.getTime());
            date.setText(message.getDate());
            */

        }
    }
}
