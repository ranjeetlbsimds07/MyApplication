package toe.tac.tic.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by ranjeet on 9/12/16.
 */

public class OptionActivity extends Activity implements View.OnClickListener {

    private Button btnSubmit;
    private EditText edtPlayer1;
    private EditText edtPlayer2;
    private EditText edtAndroid;
    private String player1="";
    private String player2="";
    private PlayerInfoSharePreference playerInfoSharePreference;
    private RadioGroup radioSelectGame;
    private RadioButton txtStartWithAndroid;
    private RadioButton txtStartWithTwoPlayer;
    private RadioButton radioSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_option);

        playerInfoSharePreference = PlayerInfoSharePreference.getInstance(this);
        btnSubmit= (Button)findViewById(R.id.btnSubmit);
        edtPlayer1= (EditText) findViewById(R.id.edtPlayer1);
        edtPlayer2= (EditText) findViewById(R.id.edtPlayer2);
        edtAndroid= (EditText) findViewById(R.id.edtAndroid);

        radioSelectGame = (RadioGroup) findViewById(R.id.radioSelectGame);
        txtStartWithAndroid= (RadioButton)findViewById(R.id.txtStartWithAndroid);
        txtStartWithTwoPlayer= (RadioButton)findViewById(R.id.txtStartWithTwoPlayer);

        if(!TextUtils.isEmpty(playerInfoSharePreference.getPlayer1Name())) {
            edtPlayer1.setText(playerInfoSharePreference.getPlayer1Name());
        }else{
            edtPlayer1.setText("");
        }
        if(!TextUtils.isEmpty(playerInfoSharePreference.getPlayer2Name())) {
            edtPlayer2.setText(playerInfoSharePreference.getPlayer2Name());
        }else{
            edtPlayer2.setText("");
        }

        if(playerInfoSharePreference.getSelectMode().equalsIgnoreCase(getResources().getString(R.string.with_android))){
            txtStartWithAndroid.setChecked(true);
            edtAndroid.setVisibility(View.VISIBLE);
            edtPlayer2.setVisibility(View.GONE);
        }else{
            txtStartWithTwoPlayer.setChecked(true);
            edtAndroid.setVisibility(View.GONE);
            edtPlayer2.setVisibility(View.VISIBLE);

        }
        btnSubmit.setOnClickListener(this);
        txtStartWithAndroid.setOnClickListener(this);
        txtStartWithTwoPlayer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:

                int selectedId = radioSelectGame.getCheckedRadioButtonId();
                radioSelectButton = (RadioButton) findViewById(selectedId);
                //Toast.makeText(OptionActivity.this, radioSelectButton.getText().toString().trim()+"", Toast.LENGTH_SHORT).show();
                if(radioSelectButton.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.start_game_with_two_player))) {

                    if (!TextUtils.isEmpty(edtPlayer1.getText().toString().trim())) {
                        player1 = edtPlayer1.getText().toString().trim();
                    }else{
                        player1 = "Player1";
                    }
                    if (!TextUtils.isEmpty(edtPlayer2.getText().toString().trim())) {
                        player2 = edtPlayer2.getText().toString().trim();
                    }else{
                        player2 = "Player2";
                    }
                    playerInfoSharePreference.savePlayer1Name(player1);
                    playerInfoSharePreference.savePlayer2Name(player2);
                    playerInfoSharePreference.saveSelectMode(getResources().getString(R.string.two_player));
                } else{
                    // for Android Device
                    player2 = getResources().getString(R.string.android);
                    if (!TextUtils.isEmpty(edtPlayer1.getText().toString().trim())) {
                        player1 = edtPlayer1.getText().toString().trim();
                    }else{
                        player1 = "Player1";
                    }
                    playerInfoSharePreference.savePlayer1Name(player1);
                    playerInfoSharePreference.savePlayer2Name(player2);
                    playerInfoSharePreference.saveSelectMode(getResources().getString(R.string.with_android));
                }
                finish();
                break;

            case R.id.txtStartWithAndroid:

                edtPlayer2.setText(R.string.android);
                edtAndroid.setVisibility(View.VISIBLE);
                edtPlayer2.setVisibility(View.GONE);
                break;
            case R.id.txtStartWithTwoPlayer:
                edtAndroid.setVisibility(View.GONE);
                edtPlayer2.setVisibility(View.VISIBLE);
                edtPlayer2.setText("");
                break;
            default:
                break;
        }
    }

}
