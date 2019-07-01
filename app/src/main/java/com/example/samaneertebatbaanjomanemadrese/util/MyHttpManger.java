package com.example.samaneertebatbaanjomanemadrese.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.json.JSONObject;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.JavaNetCookieJar;


public class MyHttpManger {

    public static boolean isOnline(Context context) {
        // permission : ACCESS_NETWORK_STATE
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected())
            return true;
        else
            return false;
    }

    public static String getDataHttpURLConnection(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//lync kardan gooshi va server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//sakhtan reader
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
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


    public static String getDataHttpURLConnection(RequestData rp, String sess_id, String sess_name) {
        // permission : INTERNET
        String uri = rp.getUri();
        if (rp.getMethod().equals("GET")) {
            uri += "?" + rp.getEncodedParams();
            try {
                URL url = new URL(uri);
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .cookieJar(new CookieJar() {
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                }

                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url) {
                                    final ArrayList<Cookie> oneCookie = new ArrayList<>(1);
                                    if (sess_id != null && sess_name != null){
                                    oneCookie.add(createNonPersistentCookie(sess_id, sess_name));
                                    }
                                    return oneCookie;
                                }
                            })
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseStr = response.body().string();
                    return responseStr;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (rp.getMethod().equals("POST")) {
            JSONObject jsonobject = new JSONObject(rp.getParams());
            try {
                URL url = new URL(uri);
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .cookieJar(new CookieJar() {
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                }

                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url) {
                                    final ArrayList<Cookie> oneCookie = new ArrayList<>(1);
                                    if (sess_id != null && sess_name != null) {
                                        oneCookie.add(createNonPersistentCookie(sess_id, sess_name));
                                    }
                                    return oneCookie;
                                }
                            })
                            .build();
                    FormBody.Builder formbody = new FormBody.Builder();
                    RequestBody body;
                    Map<String, String> params = rp.getParams();
                    Set<String> set = params.keySet();
                    ArrayList<String> keyList = new ArrayList<String>();
                    for (String str : set) {
                        keyList.add(str);
                    }
                    for (int i = 0; i < set.size(); i++) {
                        formbody.add(keyList.get(i), params.get(keyList.get(i)));
                    }
                    body = formbody.build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseStr;
                    if (response.code() == 200 && response.message().equals("OK")) {
                        responseStr = response.body().string();
                        return responseStr;
                    }
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static Cookie createNonPersistentCookie(String sess_id, String sess_name) {
        return new Cookie.Builder()
                .domain("localhost")
                .path("/")
                .name(sess_name)
                .value(sess_id)
                .httpOnly()
                .secure()
                .build();
    }


    public static void initCooki() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
    }

    public static class RequestData {
        private String uri;
        private String method;
        private Map<String, String> params;
        private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        public RequestData() {
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

        public void setParameter(String key, String value) {
            params.put(key, value);
        }

        public String getEncodedParams() {
            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                try {
                    String value = URLEncoder.encode(params.get(key), "UTF-8");

                    if (sb.length() > 0) {
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
