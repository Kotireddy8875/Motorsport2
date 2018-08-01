package com.example.kotireddy.motorsport;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpurlConnection {
    public HttpurlConnection(){

    }
    public static URL buildUrl(String s) throws MalformedURLException {
        Uri teamUri = Uri.parse(s).buildUpon().build();
        URL url = null;
        url = new URL(teamUri.toString());
        return url;
    }
    public String getHttpresponse(URL url) {

        StringBuilder stringBuilder = null;
        String response;
        HttpURLConnection httpURLConnection;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            response = null;
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();
            String getLine="";
            while ((getLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(getLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
