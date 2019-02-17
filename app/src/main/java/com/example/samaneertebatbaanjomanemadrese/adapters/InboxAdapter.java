package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private List<Inbox> inboxList;
    private int selectedPosition=-1;


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
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#ffa800"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ECF0F1"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });

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
            avatar.setImageBitmap(inbox.getBitmap());

           // Picasso.get().load(inbox.getAvatarPath()).into(avatar);
        }
    }
}
