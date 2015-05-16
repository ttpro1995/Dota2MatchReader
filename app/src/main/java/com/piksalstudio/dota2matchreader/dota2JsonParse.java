package com.piksalstudio.dota2matchreader;

import android.util.Log;

import com.piksalstudio.dota2matchreader.Player.PlayerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by haha on 5/16/2015.
 */
public class dota2JsonParse {
    private String TAG = dota2JsonParse.class.getSimpleName();
      private String RawJson;
      private PlayerInfo[] playerInfo;
    public dota2JsonParse(String rawJson) {
        RawJson = rawJson;
        playerInfo = new PlayerInfo[10];
        for (int i = 0;i<10;i++)
        {
            playerInfo[i] = new PlayerInfo();
        }
    }

    public void ParseMatchJson()
    {
        JSONObject reader = null;

        try {
            reader = new JSONObject(RawJson);
            JSONObject result = reader.getJSONObject("result");
            JSONArray players = result.getJSONArray("players");

           JSONObject player = players.getJSONObject(0);
           String id = player.getString("account_id");
            Log.i(TAG+" account_id",id);
            //TODO: continue here

        }
        catch (JSONException e)
        {
            e.getStackTrace();
        }
    }



}
