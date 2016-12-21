package toe.tac.tic.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by ranjeet on 8/12/16.
 */

public class GameActivity extends Activity {

    private int c[][];
    private int i; // for row
    private int j; // for column
    private Button b[][];// store button index
    private TextView txtResult;
    private TextView txtPlayer1;
    private TextView txtPlayer2;
    private PlayerInfoSharePreference playerInfoSharePreference;
    private String player1="";
    private String player2="";
    private int x; // for row
    private int y; // for column
    private String modOfGame ="0"; //0- Easy, 1-Medium, 2- Hard
    private boolean resultSuccess= true;
    private RadioGroup radioGameOfMode;
    private RadioButton radioEasy;
    private RadioButton radioMedium;
    private LinearLayout llModeOfGame;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        playerInfoSharePreference = PlayerInfoSharePreference.getInstance(this);
        //Set up UI
        setupUI();
    }

    private void setupUI() {

        c = new int[4][4];
        b = new Button[4][4];

        txtResult = (TextView)findViewById(R.id.txtResult);
        txtPlayer1 = (TextView)findViewById(R.id.txtPlayer1);
        txtPlayer2 = (TextView)findViewById(R.id.txtPlayer2);

        radioGameOfMode = (RadioGroup) findViewById(R.id.radioGameOfMode);
        radioEasy = (RadioButton) findViewById(R.id.radioEasy);
        radioMedium = (RadioButton) findViewById(R.id.radioMedium);
        llModeOfGame = (LinearLayout) findViewById(R.id.llModeOfGame);
        // Mode of Game
        setUpGameOfMode();
        displayModeOfGame();
        setUpPlayerName();

        // find each button id and stor index of button static

     /*   //fisrt row
        b[1][1] = (Button)findViewById(R.id.one);
        b[1][2] = (Button)findViewById(R.id.two);
        b[1][3] = (Button)findViewById(R.id.three);

        //for secon row
        b[2][1] = (Button)findViewById(R.id.four);
        b[2][2] = (Button)findViewById(R.id.five);
        b[2][3] = (Button)findViewById(R.id.six);

        //for third row
        b[3][1]= (Button)findViewById(R.id.seven);
        b[3][2]= (Button)findViewById(R.id.eight);
        b[3][3]= (Button)findViewById(R.id.nine);*/


        b[1][3] = (Button) findViewById(R.id.one);
        b[1][2] = (Button) findViewById(R.id.two);
        b[1][1] = (Button) findViewById(R.id.three);


        b[2][3] = (Button) findViewById(R.id.four);
        b[2][2] = (Button) findViewById(R.id.five);
        b[2][1] = (Button) findViewById(R.id.six);


        b[3][3] = (Button) findViewById(R.id.seven);
        b[3][2] = (Button) findViewById(R.id.eight);
        b[3][1] = (Button) findViewById(R.id.nine);

        //set default value 2 for each button
        //beacuse in last when we check for games result

        for (int i = 1; i<=3; i++){
            for (int j=1; j<=3; j++){
                c[i][j] =2;
            }
        }

        // set text when user start game
        txtResult.setText("Start Game");

        // addlister for position for each button

        setupIndividualBtnClickLister();

        for (int i=1; i<=3; i++){
            for (int j=1; j<=3; j++){
                b[i][j].setOnClickListener(new ButtonClickLister(i,j));

                //for check if button is enable then set value
                if(!b[i][j].isEnabled()){
                    b[i][j].setText(" ");
                    b[i][j].setEnabled(true);
                }
            }
        }


    }

    private void setUpPlayerName() {

        if(TextUtils.isEmpty(playerInfoSharePreference.getPlayer1Name())){
            txtPlayer1.setText("Player1");
            player1 = "Player1";
        }else {
            txtPlayer1.setText(playerInfoSharePreference.getPlayer1Name());
            player1 = playerInfoSharePreference.getPlayer1Name();
        }
        if(TextUtils.isEmpty(playerInfoSharePreference.getPlayer2Name())) {
            txtPlayer2.setText("Player2");
            player2 = "Player2";
        }else{
            txtPlayer2.setText(playerInfoSharePreference.getPlayer2Name());
            player2 = playerInfoSharePreference.getPlayer2Name();
        }


    }

    private void displayModeOfGame() {

        if(playerInfoSharePreference.getSelectMode().equalsIgnoreCase(getResources().getString(R.string.with_android))) {
            llModeOfGame.setVisibility(View.VISIBLE);
            if(playerInfoSharePreference.getModeOfGame().equalsIgnoreCase("0")){
                radioEasy.setChecked(true);
            }else if(playerInfoSharePreference.getModeOfGame().equalsIgnoreCase("1")){
                radioMedium.setChecked(true);
            }
        }else{
            llModeOfGame.setVisibility(View.GONE);
        }


    }

    private void setUpGameOfMode() {
        // If user come first time nothing select default select Esay Mode
        if(TextUtils.isEmpty(playerInfoSharePreference.getModeOfGame())){
            playerInfoSharePreference.saveModeOfGame("0");
        }else{
            playerInfoSharePreference.saveModeOfGame(playerInfoSharePreference.getModeOfGame());
        }
        //change game of mode

        radioGameOfMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {
                radioSexButton = (RadioButton) findViewById(selectedId);
                if(radioSexButton.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.medium))){
                    modOfGame = "1"; // Medium
                    playerInfoSharePreference.saveModeOfGame("1");
                }else{
                    modOfGame = "0";// Easy
                    playerInfoSharePreference.saveModeOfGame("0");
                }
            }
        });
    }

    int counter;
     class ButtonClickLister implements View.OnClickListener {
         int i;
         int j;

        public ButtonClickLister(int i, int j) {
            this.i = i;
            this.j = j;

        }

         @Override
         public void onClick(View view) {
             if(llModeOfGame.isShown()){
                 radioEasy.setEnabled(false);
                 radioMedium.setEnabled(false);
             }
             //condition for two user and with android
             if(playerInfoSharePreference.getSelectMode().equalsIgnoreCase(getResources().getString(R.string.with_android))){
                 // play with android
                 //Toast.makeText(GameActivity.this, "In Progress", Toast.LENGTH_SHORT).show();
                //========Normal Mode============
                 if(playerInfoSharePreference.getModeOfGame().equalsIgnoreCase("0")) {
                     if (b[i][j].isEnabled()) {
                         b[i][j].setEnabled(false);
                         b[i][j].setText("O");
                         b[i][j].setTextColor(Color.BLACK);
                         b[i][j].setTextSize(getResources().getDimension(R.dimen.textSize));
                         c[i][j] = 0;
                         txtResult.setText("");
                         //llModeOfGame.setVisibility(View.INVISIBLE);
                         checkGameResult();
                         if (resultSuccess){
                             //in Every click we check user win or lost or draw game
                             if (checkBooleanGameResult()) {
                                 //add move for computer
                                 Random Dice = new Random();
                                 for (int k = 1; k <= 3; k++) {
                                     for (int l = 1; l <= 3; l++) {
                                         k = Dice.nextInt(3) + 1;
                                         l = Dice.nextInt(3) + 1;
                                         if (c[k][l] == 2 && b[k][l].isEnabled()) {
                                             x = k;
                                             y = l;
                                             break;
                                         }
                                     }
                                 }
                                 //Toast.makeText(GameActivity.this, "x==="+x+"==y=="+y+"", Toast.LENGTH_SHORT).show();
                                 // check for user select position and android select position then
                                 //if (checkButtonPostion(x, y, b)) {
                                 if (b[x][y].isEnabled()) {
                                     setValueInButton(x, y, b);
                                 } else {
                                     // for row1
                                     if (b[1][3].isEnabled()) {
                                         setValueInButton(1, 3, b);
                                     } else if (b[1][2].isEnabled()) {
                                         setValueInButton(1, 2, b);
                                     } else if (b[1][1].isEnabled()) {
                                         setValueInButton(1, 1, b);
                                     } else if (b[2][3].isEnabled()) { // for 2 rows
                                         setValueInButton(2, 3, b);
                                     } else if (b[2][2].isEnabled()) {
                                         setValueInButton(2, 2, b);
                                     } else if (b[2][1].isEnabled()) {
                                         setValueInButton(2, 1, b);
                                     } else if (b[3][3].isEnabled()) { // for 3 rows
                                         setValueInButton(3, 3, b);
                                     } else if (b[3][2].isEnabled()) {
                                         setValueInButton(3, 2, b);
                                     } else if (b[3][1].isEnabled()) {
                                         setValueInButton(3, 1, b);
                                     }
                                 }
                             }
                        }
                     }
                 }else if(playerInfoSharePreference.getModeOfGame().equalsIgnoreCase("1")) {
                     //=========medium Mode===============
                     if (b[i][j].isEnabled()) {
                         b[i][j].setEnabled(false);
                         b[i][j].setText("O");
                         b[i][j].setTextColor(Color.BLACK);
                         b[i][j].setTextSize(getResources().getDimension(R.dimen.textSize));
                         c[i][j] = 0;
                         txtResult.setText("");
                         //llModeOfGame.setVisibility(View.INVISIBLE);
                         checkGameResult();
                         if (resultSuccess) {
                             if (checkBooleanGameResult()) {

                                 takeTurn();
                             }
                         }
                     }
                 }else if(playerInfoSharePreference.getModeOfGame().equalsIgnoreCase("2")){
                    //// TODO: 14/12/16
                 }



             }else{
                 // for two user
             counter++;
             if (counter % 2 == 1) {
                 if (b[i][j].isEnabled()) {
                     b[i][j].setEnabled(false);
                     b[i][j].setText("O");
                     b[i][j].setTextColor(Color.BLACK);
                     b[i][j].setTextSize(getResources().getDimension(R.dimen.textSize));
                     c[i][j] = 0;
                     txtResult.setText("");
                     //llModeOfGame.setVisibility(View.INVISIBLE);
                     //in Every click we check user win or lost or draw game
                     checkGameResult();
                 }
             } else {
                 if (b[i][j].isEnabled()) {
                     b[i][j].setEnabled(false);
                     b[i][j].setText("X");
                     b[i][j].setTextColor(Color.RED);
                     b[i][j].setTextSize(getResources().getDimension(R.dimen.textSize));
                     c[i][j] = 1;
                     //in Every click we check user win or lost or draw game
                     checkGameResult();
                 }

             }


                 // TODO: 8/12/16  if next click of device then written code here
             }
         }
     }

    private void takeTurn() {

        if(c[1][1]==2 &&
                ((c[1][2]==0 && c[1][3]==0) ||
                        (c[2][2]==0 && c[3][3]==0) ||
                        (c[2][1]==0 && c[3][1]==0))) {
            markSquare(1,1);
        } else if (c[1][2]==2 &&
                ((c[2][2]==0 && c[3][2]==0) ||
                        (c[1][1]==0 && c[1][3]==0))) {
            markSquare(1,2);
        } else if(c[1][3]==2 &&
                ((c[1][1]==0 && c[1][2]==0) ||
                        (c[3][1]==0 && c[2][2]==0) ||
                        (c[2][3]==0 && c[3][3]==0))) {
            markSquare(1,3);
        } else if(c[2][1]==2 &&
                ((c[2][2]==0 && c[2][3]==0) ||
                        (c[1][1]==0 && c[3][1]==0))){
            markSquare(2,1);
        } else if(c[2][2]==2 &&
                ((c[1][1]==0 && c[3][3]==0) ||
                        (c[1][2]==0 && c[3][2]==0) ||
                        (c[3][1]==0 && c[1][3]==0) ||
                        (c[2][1]==0 && c[2][3]==0))) {
            markSquare(2,2);
        } else if(c[2][3]==2 &&
                ((c[2][1]==0 && c[2][2]==0) ||
                        (c[1][3]==0 && c[3][3]==0))) {
            markSquare(2,3);
        } else if(c[3][1]==2 &&
                ((c[1][1]==0 && c[2][1]==0) ||
                        (c[3][2]==0 && c[3][3]==0) ||
                        (c[2][2]==0 && c[1][3]==0))){
            markSquare(3,1);
        } else if(c[3][2]==2 &&
                ((c[1][2]==0 && c[2][2]==0) ||
                        (c[3][1]==0 && c[3][3]==0))) {
            markSquare(3,2);
        }else if( c[3][3]==2 &&
                ((c[1][1]==0 && c[2][2]==0) ||
                        (c[1][3]==0 && c[2][3]==0) ||
                        (c[3][1]==0 && c[3][2]==0))) {
            markSquare(3,3);
        } else {
            Random rand = new Random();

            int a = rand.nextInt(4);
            int b = rand.nextInt(4);
            while(a==0 || b==0 || c[a][b]!=2) {
                a = rand.nextInt(4);
                b = rand.nextInt(4);
            }
            markSquare(a,b);
        }

    }

    private void markSquare(int x1, int y1) {
        b[x1][y1].setEnabled(false);
        b[x1][y1].setText("X");
        b[x1][y1].setTextColor(Color.RED);
        b[x1][y1].setTextSize(getResources().getDimension(R.dimen.textSize));
        c[x1][y1] = 1;
        checkGameResult();
    }

    private void setValueInButton(int x, int y, Button[][] b) {
        b[x][y].setEnabled(false);
        b[x][y].setText("X");
        b[x][y].setTextColor(Color.RED);
        b[x][y].setTextSize(getResources().getDimension(R.dimen.textSize));
        c[x][y] = 1;
        //in Every click we check user win or lost or draw game
        //if (checkBooleanGameResult()) {
            checkGameResult();
        //}
    }


    private void checkGameResult() {
        // check for player1 to win
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)
            || (c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)
            || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)
            || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)
            || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)
            || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)
            || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)
            || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)) {
           // for player 1 for 0 case
            //ShowResultAlert(playerInfoSharePreference.getPlayer1Name() +" win game!");
            resultSuccess = false;
            ShowResultAlert(player1 +" win game!");
        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)
                || (c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            // for player 2 for 1 case
            //ShowResultAlert(playerInfoSharePreference.getPlayer2Name() +" win game!");
            ShowResultAlert(player2 +" win game!");
            resultSuccess = false;
        } else {
            boolean empty = false;
            for(i=1; i<=3; i++) {
                for(j=1; j<=3; j++) {
                    if(c[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                //txtResult.setText("Game over. It's a draw!");
                ShowResultAlert("It's a draw!");
                resultSuccess = false;
            }
        }



    }

    private void ShowResultAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                GameActivity.this).create();
        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private boolean checkBooleanGameResult() {
        boolean empty = false;
        // check for player1 to win
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)
                || (c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)
                || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)
                || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)
                || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)
                || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)) {
            // for player 1 for 0 case
            //ShowResultAlert(playerInfoSharePreference.getPlayer1Name() +" win game!");
            //ShowResultAlert(player1 +" win game!");
            empty = true;
        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)
                || (c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            // for player 2 for 1 case
            //ShowResultAlert(playerInfoSharePreference.getPlayer2Name() +" win game!");
            //ShowResultAlert(player2 +" win game!");
            empty = true;
        } else {
            //boolean empty = false;
            for(i=1; i<=3; i++) {
                for(j=1; j<=3; j++) {
                    if(c[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                //txtResult.setText("Game over. It's a draw!");
                //ShowResultAlert("It's a draw!");
                //empty = true;// may be change
                empty = false;
            }
        }
        return  empty;

    }


    private void setupIndividualBtnClickLister() {

        /*b[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b[1][3].isEnabled()) {
                    b[1][3].setEnabled(false);
                    b[1][3].setText("O");
                    c[1][3] = 0;
                    txtResult.setText("");
                }
            }
        });


        b[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b[1][2].isEnabled()) {
                    b[1][2].setEnabled(false);
                    b[1][2].setText("O");
                    c[1][2] = 0;
                    txtResult.setText("");
                }
            }
        });


        b[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b[1][1].isEnabled()) {
                    b[1][1].setEnabled(false);
                    b[1][1].setText("O");
                    c[1][1] = 0;
                    txtResult.setText("");
                }
            }
        });*/

    }





}
