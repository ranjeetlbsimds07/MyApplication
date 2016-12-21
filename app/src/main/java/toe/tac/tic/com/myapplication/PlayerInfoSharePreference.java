package toe.tac.tic.com.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ranjeet on 9/12/16.
 */

public class PlayerInfoSharePreference {
    private static PlayerInfoSharePreference playerInfoSharePreference;
    private SharedPreferences sharedPreferences;
    private final String PLAYER1 = "player1";
    private final String PLAYER2 = "player2";
    private final String SELECTMODE = "selectMode";
    private final String MODEOFGAME = "modeofGame";

    public static PlayerInfoSharePreference getInstance(Context context) {
        if (playerInfoSharePreference == null) {
            playerInfoSharePreference = new PlayerInfoSharePreference(context);
        }
        return playerInfoSharePreference;
    }

    private PlayerInfoSharePreference(Context context) {
        sharedPreferences = context.getSharedPreferences("PlayerInfoNamedPreference",Context.MODE_PRIVATE);
    }

    public void savePlayer1Name(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(PLAYER1, value);
        prefsEditor.commit();
    }

    public String getPlayer1Name() {

        if (sharedPreferences!= null) {
            return sharedPreferences.getString(PLAYER1, "");
        }
        return "";
    }


    public void savePlayer2Name(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(PLAYER2, value);
        prefsEditor.commit();
    }

    public String getPlayer2Name() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(PLAYER2, "");
        }
        return "";
    }

    public void saveSelectMode(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(SELECTMODE, value);
        prefsEditor.commit();
    }

    public String getSelectMode() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(SELECTMODE, "");
        }
        return "";
    }

    public void saveModeOfGame(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(MODEOFGAME, value);
        prefsEditor.commit();
    }

    public String getModeOfGame() {

            return sharedPreferences.getString(MODEOFGAME, "");
    }

}
