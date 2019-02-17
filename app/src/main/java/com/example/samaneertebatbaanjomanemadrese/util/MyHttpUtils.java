package com.example.samaneertebatbaanjomanemadrese.util;

import android.net.Uri;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MyHttpUtils {
    public static String converInputStreamToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//api zire 22
    public static String getDataHttpClient(String uri) {
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(uri);
        try {
            HttpResponse resp = client.execute(method);
            String content = MyHttpUtils.converInputStreamToString(resp.getEntity().getContent());
            return content;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getDataHttpConnection(RequestData requestData) {
        String uri = requestData.getUri();
        if ("GET".equals(requestData.getMethod()) && !requestData.getEncodedParams().isEmpty())
            uri += "?" + requestData.getEncodedParams();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestData.getMethod());
            if ("POST".equals(requestData.getMethod()) && !requestData.getEncodedParams().isEmpty()){
                con.setDoInput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(requestData.getEncodedParams());
                writer.flush();
                writer.close();
            }
            String result = converInputStreamToString(con.getInputStream());
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class RequestData {
        private String uri = "", method = "GET";
        private Map<String, String> params;

        public RequestData() {
            this("", "GET");
        }

        public RequestData(String uri, String method) {
            this.uri = uri;
            this.method = method;
            params = new HashMap<>();
        }

        public String getUri() {
            return uri;
        }

        public String getMethod() {
            return method;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setParams(Map<String, String> params) {
            if (params == null) {
                this.params = new HashMap<>();
            }
            this.params = params;
        }

        public void setParameter(String key, String value) {
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(key, value);
        }

        public String getEncodedParams() {
            StringBuilder sb = new StringBuilder();
            try {
                for (String key :
                        params.keySet()) {
                    if (sb.length() > 0){
                        sb.append("&");
                    }
                    String value = params.get(key);
                    sb.append(key);
                    sb.append("=");
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                }
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

    }


}
