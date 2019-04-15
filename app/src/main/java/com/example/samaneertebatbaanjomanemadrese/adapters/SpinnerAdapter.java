package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.samaneertebatbaanjomanemadrese.R;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private String[] lists;
    private LayoutInflater inflater;

    public SpinnerAdapter(Context context, String[] lists) {
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.length;
    }

    @Override
    public Object getItem(int position) {
        return lists[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_layout, parent, false);
            holder = new ViewHolder();
            holder.list = convertView.findViewById(R.id.spinner_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(position);
        return convertView;
    }

    public String[] getLists() {
        return lists;
    }

    public void setLists(String[] lists) {
        this.lists = lists;
    }

    public class ViewHolder {
        private AppCompatTextView list;

        public void fill(int position) {
            list.setText(lists[position]);
        }
    }
}
