package com.hahattpro.dota2matchreader;

import android.util.Log;

import com.hahattpro.dota2matchreader.Player.PlayerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by haha on 5/16/2015.
 */

// sample https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?match_id=1468017320&key=FF2CB61BA9935DA162C67002D8F753FE
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

            for (int i=0;i<10;i++) {
                JSONObject player = players.getJSONObject(i);
                PlayerInfo tmp = playerInfo[i];
                tmp.account_id = player.getString("account_id");
                tmp.hero_id=player.getInt("hero_id");
                tmp.kills=player.getInt("kills");
                tmp.deaths=player.getInt("deaths");

            }

        }
        catch (JSONException e)
        {
            e.getStackTrace();
        }
    }

    public void LogMatch(){
        for (int i = 0 ;i <10;i++)
        {
            PlayerInfo tmp = playerInfo[i];
            Log.i(TAG+"player "+i,"account_id "+tmp.account_id);
            Log.i(TAG+"player "+i,"hero_id"+tmp.hero_id);
            Log.i(TAG+"player "+i,"kill"+tmp.kills);
            Log.i(TAG+"player "+i,"death"+tmp.deaths);
        }
    }



}
