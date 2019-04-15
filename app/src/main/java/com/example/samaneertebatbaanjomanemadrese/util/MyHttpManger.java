package com.example.samaneertebatbaanjomanemadrese.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;



public class MyHttpManger {

    public static boolean isOnline( Context context){
        // permission : ACCESS_NETWORK_STATE
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if(netinfo != null && netinfo.isConnected())
            return true;
        else
            return false;
    }

    //api haye zire 22
    public static String getDataHttpClient(String uri){
        // permission : INTERNET
        AndroidHttpClient client = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet method = new HttpGet(uri);
        try {
            HttpResponse response = client.execute(method);
            int statuscode = response.getStatusLine().getStatusCode();
            if((statuscode/100) == 4){
                return "HttpClient ERROR " + statuscode;
            }
            else{
                String content = MyHttpUtils.converInputStreamToString(response.getEntity().getContent());
                client.close();
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
            client.close();
            return null;
        }
    }


    public static String getDataHttpURLConnection(String uri){
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//lync kardan gooshi va server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//sakhtan reader
            StringBuilder  sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                 sb.append(line);
            }
            return line;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*

    public static String getDataHttpURLConnection(RequestPackage rp){
        // permission : INTERNET
        String uri = rp.getUri();
        if(rp.getMethod().equals("GET")){
            uri += "?" + rp.getEncodedParams();
        }

        JSONObject  jsonobject = new JSONObject(rp.getParams());

        try {
            URL url = new URL(uri);
            try {
                OkHttpClient client = new OkHttpClient();
                HttpURLConnection connection = client.open(url);
                connection.setRequestMethod(rp.getMethod());
                if(rp.getMethod().equals("POST")){
                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(rp.getEncodedParams());
                    //writer.write("params=" + jsonobject.toString());
                    writer.flush();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return null;
    }


*/

    public static class RequestData{
        private String uri;
        private String method;
        private Map<String, String> params;

        public RequestData(){
            this.uri = "";
            this.method = "GET";
            this.params = new HashMap<String, String>();
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        public void setParameter(String key, String value){
            params.put(key, value);
        }

        public String getEncodedParams(){
            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                try {
                    String value = URLEncoder.encode( params.get(key), "UTF-8");

                    if(sb.length() > 0){
                        sb.append('&');
                    }
                    sb.append(key + "=" + value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

    }

}
