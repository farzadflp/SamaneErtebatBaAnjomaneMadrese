package com.example.samaneertebatbaanjomanemadrese.adapters;

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
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.task.ManagerVerifyParentTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.VerifyViewHolder>{
    private List<Parent> parentList;
    private int selectedPosition = -1;
    private int idSelected;
    private Context context;
    public static final String URL_VERIFIED_PARENT , URL_UNVERIFIED_PARENT ;
    static {
        URL_VERIFIED_PARENT = "http://192.168.1.34:8888/get_msgs.php";
        URL_UNVERIFIED_PARENT = "http://192.168.1.34:8888/get_msgs.php";

    }
    public VerifyAdapter(@NonNull List<Parent> parentList) {
        if (parentList == null){
            this.parentList = new ArrayList<>();
        }else {
            this.parentList = parentList;
        }
    }
    @NonNull
    @Override
    public VerifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manager_verify_user_row , parent , false);
        context = parent.getContext();
        return new VerifyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull VerifyViewHolder holder, int position) {
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
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {
                    })
                    .show();

        });
        holder.refuse.setOnClickListener(v -> {
            holder.verify.setEnabled(false);
            holder.verify.setClickable(false);
            selectedPosition = position;
            idSelected = parentList
                    .get(selectedPosition)
                    .getId_user();
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle(R.string.unverified_parent)
                    .setMessage(R.string.unverified_parent_alert)
                    .setIcon(R.drawable.warning)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {

                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {
                        unverifiedRequest(idSelected);
                    })
                    .show();
        });
    }

    private void unverifiedRequest(int idSelected) {
        ManagerVerifyParentTask task = new ManagerVerifyParentTask((ManagerVerifyParentActivity) context);
        task.execute(creatVerfiedRequestData(idSelected));

    }

    private void verifiedRequest(int idSelected) {
        ManagerVerifyParentTask task = new ManagerVerifyParentTask((ManagerVerifyParentActivity) context);
        task.execute(creatVerfiedRequestData(idSelected));

    }

    private MyHttpManger.RequestData creatVerfiedRequestData(int idSelected) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_VERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_user", String.valueOf(idSelected));
        requestData.setParams(params);
        return requestData;
    }
    private MyHttpManger.RequestData creatUnverfiedRequestData(int idSelected) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_UNVERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_user", String.valueOf(idSelected));
        requestData.setParams(params);
        return requestData;
    }

    @Override
    public int getItemCount() {
        return parentList.size();
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getIdSelected() {
        return idSelected;
    }

    public class VerifyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView firstname , lastname , gender ,childName , stNoOfChild , phone_no ;
        private AppCompatImageView verify , refuse;
        VerifyViewHolder(View itemView){
            super(itemView);
            firstname = itemView.findViewById(R.id.mvu_name_content_tv);
            lastname = itemView.findViewById(R.id.mvu_family_content_name_tv);
            gender = itemView.findViewById(R.id.mvu_gender_content_tv);
            childName = itemView.findViewById(R.id.mvu_child_name_content_tv);
            stNoOfChild = itemView.findViewById(R.id.mvu_st_no_child_content_tv);
            phone_no = itemView.findViewById(R.id.mvu_phone_no_content_tv);
            verify= itemView.findViewById(R.id.mvu_verify_imgv);
            refuse = itemView.findViewById(R.id.mvu_refuse_imgv);
        }
        public void bind(Parent parent){
            firstname.setText(parent.getFirstname());
            lastname.setText(parent.getLastname());
            if (parent.getGender() == 0){
                gender.setText(R.string.female);
            } else if (parent.getGender() == 1){
                gender.setText(R.string.male);
            }
            childName.setText(parent.getChildName());
            stNoOfChild.setText(parent.getStNoOfChild());

        }
    }
}
