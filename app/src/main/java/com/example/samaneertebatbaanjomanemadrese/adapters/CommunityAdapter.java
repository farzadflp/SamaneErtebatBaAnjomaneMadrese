package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.samaneertebatbaanjomanemadrese.EditCommunityActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {
    private List<Community> communityList;
    private Context context;
    private Activity activity;
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTextView;
    private static final int VIEW_TYPE_EMPTY , VIEW_TYPE_OCCUPIED;
    static {
        VIEW_TYPE_EMPTY = 1;
        VIEW_TYPE_OCCUPIED = 2;
    }

    public CommunityAdapter(@NonNull List<Community> msgList) {
        if (msgList == null){
            this.communityList = new ArrayList<>();
        }else {
            this.communityList = msgList;
        }
    }
    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_row , parent , false);
        context = parent.getContext();
        activity = (Activity) context;
        recyclerView  = activity.findViewById(R.id.get_community_rc);
        emptyTextView = activity.findViewById(R.id.get_community_tv);
        return new CommunityViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else if (viewType == VIEW_TYPE_OCCUPIED) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            holder.bind(communityList.get(position));

            holder.edit.setOnClickListener(v -> {
                int selectedPosition = position;
                notifyDataSetChanged();
                Intent intent = new Intent(activity , EditCommunityActivity.class);
                intent.putExtra("community" , communityList.get(selectedPosition));
                activity.startActivity(intent);
            });

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (communityList.size() == 0){
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_OCCUPIED;
        }
    }



    @Override
    public int getItemCount() {
        return communityList.size() != 0 ? communityList.size() : 1;
    }



    public List<Community> getCommunityList() {
        return communityList;
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView firstname , lastname ,gender , degree , course , post , addressWork , telWork , phone_no;
        private AppCompatImageView edit;

        CommunityViewHolder(View itemView){
            super(itemView);
            firstname = itemView.findViewById(R.id.community_row_tv_name_content);
            lastname = itemView.findViewById(R.id.community_row_tv_family_name_content);
            gender = itemView.findViewById(R.id.community_row_tv_gender_content);
            degree = itemView.findViewById(R.id.community_row_tv_degree_content);
            course = itemView.findViewById(R.id.community_row_tv_course_content);
            post = itemView.findViewById(R.id.community_row_tv_post_content);
            addressWork = itemView.findViewById(R.id.community_row_tv_address_work_content);
            telWork = itemView.findViewById(R.id.community_row_tv_tel_work_content);
            phone_no = itemView.findViewById(R.id.community_row_tv_phone_no_content);
            edit = itemView.findViewById(R.id.community_row_imgv_edit);
        }

        public void bind(Community community){
            firstname.setText(community.getFirstname());
            lastname.setText(community.getLastname());
            switch (community.getGender()){
                case 0 :
                    gender.setText(activity.getString(R.string.female));
                    break;
                case 1 :
                    gender.setText(activity.getString(R.string.female));
                    break;
            }

            degree.setText(community.getDegree());
            course.setText(community.getCourse());
            post.setText(community.getPost());
            addressWork.setText(community.getAddressWork());
            telWork.setText(community.getTelWork());
            phone_no.setText(community.getPhoneNo());

        }
    }
}
