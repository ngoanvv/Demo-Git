package com.example.diep_chelsea.readjson;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;


public class MainActivity extends Activity {
    TextView monhoc,noihoc,website,fanpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monhoc = (TextView) findViewById(R.id.monhoc);
        noihoc = (TextView) findViewById(R.id.noihoc);
        website = (TextView) findViewById(R.id.website);
        fanpage = (TextView) findViewById(R.id.fanpage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readJson().execute("http://api.openweathermap.org/data/2.5/weather?q=HaNoi,vn");
            }
        });
    }
    class readJson extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String chuoi = getXmlFromUrl(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s);
//                String mh = root.getString("monhoc");
//                monhoc.setText(mh);
//                String nh = root.getString("noihoc");
//                noihoc.setText(nh);
//                String web = root.getString("website");
//                website.setText(web);
//                String fb = root.getString("fanpage");
//                fanpage.setText(fb);
                JSONObject temp = root.getJSONObject("main");
                String a = temp.getString("temp");
                monhoc.setText(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            // defaultHttpClient lấy toàn bộ dữ liệu trong http đổ vào 1 chuỗi String
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            // set UTF-8 cho ra chữ unikey
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }

}
