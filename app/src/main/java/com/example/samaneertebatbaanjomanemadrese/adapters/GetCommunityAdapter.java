package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.samaneertebatbaanjomanemadrese.R;

public class GetCommunityAdapter extends BaseAdapter {
    private String[] firstnameArray , lastnameArray , postArray , usernameArray;
    private LayoutInflater inflater;
    public GetCommunityAdapter(Context context, String[] firstnameArray , String[] lastnameArray,String[] postArray,String[] usernameArray) {
        Context context1 = context;
        this.firstnameArray = firstnameArray;
        this.lastnameArray = lastnameArray;
        this.postArray  = postArray;
        this.usernameArray = usernameArray;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return firstnameArray.length;
    }

    @Override
    public Object getItem(int position) {
        return usernameArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_layout_get_community, parent, false);
            holder = new ViewHolder();
            holder.firstname = convertView.findViewById(R.id.spinner_tv_firstname);
            holder.lastname = convertView.findViewById(R.id.spinner_tv_lastname);
            holder.post = convertView.findViewById(R.id.spinner_tv_post);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(position);
        return convertView;
    }
    public class ViewHolder {
        private AppCompatTextView firstname , lastname , post;

        public void fill(int position) {
            firstname.setText(firstnameArray[position]);
            lastname.setText(lastnameArray[position]);
            post.setText(postArray[position]);
        }
    }
}
