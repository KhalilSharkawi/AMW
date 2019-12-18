package com.MS.applications.UnlimitedServicesDriver.Api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;

import com.MS.applications.UnlimitedServicesDriver.R;
import com.MS.applications.UnlimitedServicesDriver.Utils.CustomDialogClass;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static com.MS.applications.UnlimitedServicesDriver.Activities.BaseActivity.curr_lang;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.ACCEPT;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.CHARSET_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.CONTENT_TYPE;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.JSON_CONTENT_TYPE;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.LOCALIZATION;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.REQUEST_METHOD;

public class SendGetJsonApi  {

    private static String ApiUrl;
    private static JSONObject JsonSend;
    private static CallBackListener callBackListener;
    private static HttpURLConnection urlConnection;
    private static int ConnectTimeout = 15000;
    private static int ReadTimeout = 30000;
    private static Context context;
    public static CustomDialogClass cdd;
    private static boolean isWithProgress = true;

    private static String WebSite()
    {
        return "http://mws.somee.com/";
    }

    public SendGetJsonApi (Context context, String apiUrl, JSONObject jsonSend, CallBackListener callBackListener) {

        new SendGetJsonApi(context, apiUrl, jsonSend, callBackListener, true);
    }

    public SendGetJsonApi (Context mContext, String mApiUrl, JSONObject mJsonSend, CallBackListener mCallBackListener, boolean mIsWithProgress) {
        context = mContext;
        ApiUrl = mApiUrl;
        JsonSend = mJsonSend;
        callBackListener = mCallBackListener;
        isWithProgress = mIsWithProgress;
    }

    public void Execute()
    {
        asyncTask atask = new asyncTask();
        atask.execute();
    }

    private static class asyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isWithProgress){
                try {
                    cdd = new CustomDialogClass(context, "spinner");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(cdd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                    cdd.setCancelable(false);
                    cdd.show();
                }
                catch (Exception ignored){}
            }
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            String link = WebSite() + ApiUrl;
            try {
                URL url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestMethod(REQUEST_METHOD);


                urlConnection.setRequestProperty(LOCALIZATION, curr_lang);
                urlConnection.setConnectTimeout(ConnectTimeout);
                urlConnection.setReadTimeout(ReadTimeout);
                urlConnection.setRequestProperty(CONTENT_TYPE, JSON_CONTENT_TYPE);
                urlConnection.setRequestProperty(ACCEPT, JSON_CONTENT_TYPE);
                urlConnection.connect();
                OutputStream os = urlConnection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
                osw.write(JsonSend.toString());
                osw.flush();
                osw.close();
                InputStream in;
                int status = urlConnection.getResponseCode();
                if (status == 200) {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                } else {
                    in = urlConnection.getErrorStream();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (Exception e) {
                return context.getResources().getString(R.string.went_wrong);
            } finally {
                urlConnection.disconnect();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String resultjson)
        {
            if(isWithProgress){
                try {
                    cdd.dismiss();
                }catch (Exception ignored){}
            }
            if ( context instanceof Activity) {
                Activity activity = (Activity)context;
                if ( activity.isFinishing() ) {
                    return;
                }
            }
            callBackListener.onFinish(resultjson);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            if ( context instanceof Activity) {
                Activity activity = (Activity)context;
                if ( activity.isFinishing() ) {
                    return;
                }
            }
            callBackListener.onProgress(values[0]);
        }
    }
}
