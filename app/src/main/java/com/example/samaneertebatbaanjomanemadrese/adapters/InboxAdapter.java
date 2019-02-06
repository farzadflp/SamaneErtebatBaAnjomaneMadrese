package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;

import java.util.ArrayList;
import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private List<Inbox> inboxList;


    public InboxAdapter(@NonNull List<Inbox> inboxList) {
        if (inboxList == null){
            this.inboxList = new ArrayList<>();
        }else {
            this.inboxList = inboxList;
        }
    }


    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_row, parent , false);
        return new InboxViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        holder.bind(inboxList.get(position));

    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView avatar;
        private AppCompatTextView username;
        public InboxViewHolder(View itemView) {
            super(itemView);
            username = (AppCompatTextView) itemView.findViewById(R.id.inbox_row_tv_username);
            avatar = (AppCompatImageView) itemView.findViewById(R.id.inbox_row_imgv_avatar);

        }
        public void bind(Inbox inbox){
            username.setText(inbox.getUsername());
            avatar.setImageResource(R.drawable.user);
        }
    }
}
