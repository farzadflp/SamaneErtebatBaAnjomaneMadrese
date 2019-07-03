package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samaneertebatbaanjomanemadrese.ManagerVerifyParentActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.task.ManagerVerifyParentTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.VerifyViewHolder> {
    private List<Parent> parentList;
    private int selectedPosition = -1;
    private int idSelected;
    private Context context;
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTextView;
    private static final String URL_VERIFIED_PARENT;
    private static final int VIEW_TYPE_EMPTY , VIEW_TYPE_OCCUPIED;
    static {
        URL_VERIFIED_PARENT = MyIntentHelper.URL_BASE + "manager/verified.php";
        VIEW_TYPE_EMPTY = 1;
        VIEW_TYPE_OCCUPIED = 2;
    }

    public VerifyAdapter(@NonNull List<Parent> parentList) {
        if (parentList == null) {
            this.parentList = new ArrayList<>();
        } else {
            this.parentList = parentList;
        }
    }

    @NonNull
    @Override
    public VerifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manager_verify_user_row, parent, false);
        context = parent.getContext();
        Activity activity = (Activity) context;
        recyclerView  = activity.findViewById(R.id.get_community_rc);
        emptyTextView = activity.findViewById(R.id.get_community_tv);
        return new VerifyViewHolder(itemview);
    }



    @Override
    public void onBindViewHolder(@NonNull VerifyViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);

        } else if (viewType == VIEW_TYPE_OCCUPIED) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            holder.bind(parentList.get(position));
            holder.verify.setOnClickListener(v -> {
                holder.verify.setEnabled(false);
                holder.verify.setClickable(false);
                selectedPosition = position;
                idSelected = parentList
                        .get(selectedPosition)
                        .getId_user();
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle(R.string.verified_parent)
                        .setMessage(R.string.verified_parent_alert)
                        .setIcon(R.drawable.warning)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            verifiedRequest(idSelected);
                            holder.verify.setEnabled(true);
                            holder.verify.setClickable(true);

                        })
                        .setNegativeButton(R.string.no, (dialog, which) -> {
                            holder.verify.setEnabled(true);
                            holder.verify.setClickable(true);
                        })
                        .show();

            });
            holder.refuse.setOnClickListener(v -> {
                holder.refuse.setEnabled(false);
                holder.refuse.setClickable(false);
                selectedPosition = position;
                idSelected = parentList
                        .get(selectedPosition)
                        .getId_user();
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle(R.string.unverified_parent)
                        .setMessage(R.string.unverified_parent_alert)
                        .setIcon(R.drawable.forbbiden)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            unverifiedRequest(idSelected);
                            holder.refuse.setEnabled(true);
                            holder.refuse.setClickable(true);
                        })
                        .setNegativeButton(R.string.no, (dialog, which) -> {
                            holder.refuse.setEnabled(true);
                            holder.refuse.setClickable(true);
                        })
                        .show();
            });
        }

    }


    private void unverifiedRequest(int idSelected) {
        ManagerVerifyParentTask task = new ManagerVerifyParentTask((ManagerVerifyParentActivity) context);
        task.execute(unverfiedRequestData(idSelected));

    }

    private void verifiedRequest(int idSelected) {
        ManagerVerifyParentTask task = new ManagerVerifyParentTask((ManagerVerifyParentActivity) context);
        task.execute(verfiedRequestData(idSelected));

    }

    private MyHttpManger.RequestData verfiedRequestData(int idSelected) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_VERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("verify", "v");
        params.put("id_user", String.valueOf(idSelected));
        requestData.setParams(params);
        return requestData;
    }

    private MyHttpManger.RequestData unverfiedRequestData(int idSelected) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_VERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("verify", "u");
        params.put("id_user", String.valueOf(idSelected));
        requestData.setParams(params);
        return requestData;
    }


    @Override
    public int getItemCount() {
        return parentList.size() != 0 ? parentList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (parentList.size() == 0){
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_OCCUPIED;
        }
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getIdSelected() {
        return idSelected;
    }

    public class VerifyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView firstname, lastname, gender, childName, stNoOfChild, phone_no;
        private AppCompatImageView verify, refuse;

        VerifyViewHolder(View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.community_row_tv_name_content);
            lastname = itemView.findViewById(R.id.community_row_tv_family_name_content);
            gender = itemView.findViewById(R.id.community_row_tv_gender_content);
            childName = itemView.findViewById(R.id.community_row_tv_degree);
            stNoOfChild = itemView.findViewById(R.id.community_row_tv_course);
            phone_no = itemView.findViewById(R.id.mvu_phone_no_content_tv);
            verify = itemView.findViewById(R.id.mvu_verify_imgv);
            refuse = itemView.findViewById(R.id.mvu_refuse_imgv);
        }

        public void bind(Parent parent) {
            firstname.setText(parent.getFirstname());
            lastname.setText(parent.getLastname());
            if (parent.getGender() == 0) {
                gender.setText(R.string.female);
            } else if (parent.getGender() == 1) {
                gender.setText(R.string.male);
            }
            phone_no.setText(parent.getPhoneNo());
            childName.setText(parent.getChildName());
            stNoOfChild.setText(parent.getStNoOfChild());


        }
    }
}
