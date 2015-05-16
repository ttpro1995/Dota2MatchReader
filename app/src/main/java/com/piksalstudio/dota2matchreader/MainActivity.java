package com.piksalstudio.dota2matchreader;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.piksalstudio.dota2matchreader.constant.Dota2API;
import com.piksalstudio.dota2matchreader.constant.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetMatchInfoJson().execute("1468017320");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetMatchInfoJson extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String DOTA2_MATCH_BASE_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
            String MATCH_ID =  "match_id";
            String KEY = "key";

            Uri Uri_builder= Uri.parse(DOTA2_MATCH_BASE_URL).buildUpon()
                            .appendQueryParameter(MATCH_ID,strings[0])
                            .appendQueryParameter(KEY, Dota2API.API_KEY).build();

            URL url = null;
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader =null;
            try {
                //connect to dota 2
                url = new URL(Uri_builder.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                Log.v("URL",url.toString());//Log url

                // Read the input stream into a String
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                //write json to variable
                Match.match_json = buffer.toString();

            }
            catch (MalformedURLException e)
            {
                e.getStackTrace();
            }
            catch (IOException e)
            {
                e.getStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.v ("Match Json",Match.match_json);//log json
            dota2JsonParse parser = new dota2JsonParse(Match.match_json);
            parser.ParseMatchJson();
        }
    }

}
