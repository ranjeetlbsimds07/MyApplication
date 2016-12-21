package toe.tac.tic.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btStartGame;
    private Button Exit;
    private Button btnOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btStartGame = (Button)findViewById(R.id.btStartGame);
        Exit = (Button)findViewById(R.id.Exit);
        btnOption = (Button)findViewById(R.id.btnOption);

        btStartGame.setOnClickListener(this);
        Exit.setOnClickListener(this);
        btnOption.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btStartGame:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.Exit:
                finish();
                break;
            case R.id.btnOption:
                Intent intentOption = new Intent(this, OptionActivity.class);
                startActivity(intentOption);
                break;
            default:
                break;
        }

    }
}
