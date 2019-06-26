package com.example.samaneertebatbaanjomanemadrese.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.samaneertebatbaanjomanemadrese.ChatActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.util.MessageJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private List<Inbox> inboxList;
    private int selectedPosition = -1;
    public static final String URL_GET_MSGS;
    private Context context ;
    private String topicSelected;
    static {
        URL_GET_MSGS = "http://192.168.1.34:8888/get_msgs.php";
    }


    public InboxAdapter(@NonNull List<Inbox> inboxList) {
        if (inboxList == null) {
            this.inboxList = new ArrayList<>();
        } else {
            this.inboxList = inboxList;
        }
    }


    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_row, parent, false);
        context = parent.getContext();
        return new InboxViewHolder(itemview);
    }


    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        holder.bind(inboxList.get(position));
        holder.itemView.setEnabled(true);
        holder.itemView.setClickable(true);

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffa800"));
        } else
            holder.itemView.setBackgroundColor(Color.parseColor("#ECF0F1"));

        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);

            selectedPosition = position;
            int conversationSelected = inboxList
                    .get(selectedPosition)
                    .getId_conversation();
            topicSelected = inboxList
                    .get(selectedPosition)
                    .getTopic();
            context = holder.itemView.getContext();
            getMessageOfConversationSelected(conversationSelected);
            notifyDataSetChanged();
        });

    }

    private void getMessageOfConversationSelected(int conversationSelected) {

        GetMsgTask task = new GetMsgTask();
        task.execute(getRequestData(conversationSelected));
        /*
        GetMessageTask task = new GetMessageTask((InboxActivity) context);
        task.execute(getRequestData(conversationSelected));
        */
    }

    private RequestData getRequestData(int conversationSelected) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_GET_MSGS);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_conversation", String.valueOf(conversationSelected));
        requestData.setParams(params);
        return requestData;
    }


    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public String getTopicSelected() {
        return topicSelected;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView avatar;
        private AppCompatTextView username_two, topic, date_time_conv, category;

        public InboxViewHolder(View itemView) {
            super(itemView);
            username_two = itemView.findViewById(R.id.inbox_row_tv_username);
            avatar = itemView.findViewById(R.id.inbox_row_imgv_avatar);
            topic = itemView.findViewById(R.id.inbox_row_tv_topic);
            date_time_conv = itemView.findViewById(R.id.inbox_row_tv_date);
            category = itemView.findViewById(R.id.inbox_row_tv_category);
        }

        public void bind(Inbox inbox) {
            username_two.setText(inbox.getUsername_two());
            topic.setText(inbox.getTopic());
            date_time_conv.setText(inbox.getDate_time_conv());
            category.setText(inbox.getCategory());
            //avatar.setImageBitmap(inbox.getBitmap());
            avatar.setImageResource(R.drawable.conversation);
        }
    }
    

    public class GetMsgTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyHttpManger.RequestData... params) {
            MyHttpManger.RequestData uri = params[0];
            return MyHttpManger.getDataHttpURLConnection(uri
                    , MyIntentHelper.getSessionId(context)
                    , MyIntentHelper.getSessionName(context));
        }

        @Override
        protected void onPostExecute(String response) {

            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                ArrayList<Message> msgs = new MessageJsonParser().messageParseJson(response);
                if(success){
                    successProcess(msgs , topicSelected);
                } else {
                    unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private void unsuccessProcess() {
            Toast.makeText(context , R.string.error , Toast.LENGTH_LONG);
        }

        private void successProcess(ArrayList<Message> msgs , String topic) {
            Intent intent = new Intent(context , ChatActivity.class);
            intent.putExtra("msg", msgs);
            intent.putExtra("topic" , topic);
            selectedPosition = -1;
            notifyDataSetChanged();
            context.startActivity(intent);
        }
    }

}
