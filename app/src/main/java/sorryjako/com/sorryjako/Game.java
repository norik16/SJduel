package sorryjako.com.sorryjako;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Game extends AppCompatActivity {
    ImageButton babisTOP;
    ImageButton babisBOT;
    ImageButton zemanTOP;
    ImageButton zemanBOT;
    TextView textTOP;
    TextView textBOT;
    TextView tScoreTOP;
    TextView tScoreBOT;
    TextView tScoreHisTOP;
    TextView tScoreHisBOT;
    LinearLayout linearLayout;

    int scoreTOP;
    int scoreBOT;
    int lastLine;
    int[] usedLines;
    String actPerson;

    SQLiteDatabase sqLiteDatabase;
    Database database;
    Cursor cursor;

    static final int numberOfLines = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);


        babisTOP = (ImageButton) findViewById(R.id.id_game_babisTOP_BT);
        babisBOT = (ImageButton) findViewById(R.id.id_game_babisBOT_BT);
        zemanTOP = (ImageButton) findViewById(R.id.id_game_zemanTOP_BT);
        zemanBOT = (ImageButton) findViewById(R.id.id_game_zemanBOT_BT);

        textBOT = (TextView) findViewById(R.id.id_game_textBOT_TV);
        textTOP = (TextView) findViewById(R.id.id_game_textTOP_TV);
        tScoreBOT = (TextView) findViewById(R.id.id_game_scoreBOT_TV);
        tScoreTOP = (TextView) findViewById(R.id.id_game_scoreTOP_TV);
        tScoreHisBOT = (TextView) findViewById(R.id.id_game_hisScoreBOT_TV);
        tScoreHisTOP = (TextView) findViewById(R.id.id_game_hisScoreTOP_TV);

        //Setting text
        tScoreBOT.setText("0");
        tScoreTOP.setText("0");
        tScoreHisBOT.setText("0");
        tScoreHisTOP.setText("0");

        lastLine = 2;
        scoreTOP = 0;
        scoreBOT = 0;

        usedLines = new int[numberOfLines+4];

        for (int i = 0; i <= numberOfLines; i++) {
            usedLines[i] = 0;
        }

        database = new Database(getApplicationContext());
        sqLiteDatabase = database.getReadableDatabase();

        babisTOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Game/onClick", "clicked...");
                if (actPerson.equals("B"))
                    addScore("TOP");
                else {
                    addScore("BOT");
//                    babisTOP.setImageResource(R.mipmap.babis_open);
//                    babisTOP.invalidate();
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    babisTOP.setImageResource(R.mipmap.babis_close);
//                    try {
//                        tScoreTOP.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
//                        for (int i = 0; i < 10; i++) {
//                            TimeUnit.MILLISECONDS.sleep(100);
//                            babisTOP.setImageResource(R.mipmap.babis_open);
//                            TimeUnit.MILLISECONDS.sleep(100);
//                            babisTOP.setImageResource(R.mipmap.babis_close);
//                        }
//                        tScoreTOP.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//                    } catch (InterruptedException e) {
//                        Log.e("Game/clicked", "Babish exeption");
//                        e.printStackTrace();
//                    }
                }
                getLine();
            }
        });

        babisBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Game/onClick", "clicked...");
                if (actPerson.equals("B"))
                    addScore("BOT");
                else
                    addScore("TOP");
                getLine();
            }
        });

        zemanTOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actPerson.equals("B"))
                    addScore("BOT");
                else
                    addScore("TOP");
                getLine();
            }
        });

        zemanBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actPerson.equals("B"))
                    addScore("TOP");
                else
                    addScore("BOT");
                getLine();
            }
        });
        getLine();
    }

    protected void getLine() {
        Log.e("Game/getLine", "entering...");

        Integer id;
        Random r = new Random();
        id = r.nextInt(numberOfLines) + 1;

        Log.e("Game/getLine", id.toString());


        cursor = database.getInformations(sqLiteDatabase, id);

        if (!cursor.moveToFirst())
            Log.e("Game/getLine", "Its not here!");
        else {
            while ((cursor.getInt(2) == 2 && lastLine == 2) || usedLines[id] != 0) {
                id = r.nextInt(numberOfLines) + 1;

                cursor = database.getInformations(sqLiteDatabase, id);
                cursor.moveToFirst();
            }

            lastLine = cursor.getInt(2);
            textTOP.setText(cursor.getString(0));
            textBOT.setText(cursor.getString(0));
            actPerson = cursor.getString(1);
            usedLines[id] = 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void addScore(String side) {
        if (side.equals("TOP")) {
            scoreTOP++;
            tScoreTOP.setText(String.format(Locale.getDefault(), "%3d", scoreTOP));
            tScoreHisBOT.setText(String.format(Locale.getDefault(), "%3d", scoreTOP));
        } else {
            scoreBOT++;
            tScoreBOT.setText(String.format(Locale.getDefault(), "%3d", scoreBOT));
            tScoreHisTOP.setText(String.format(Locale.getDefault(), "%3d", scoreBOT));
        }

        try {
            Thread.sleep(250);
            //TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (scoreTOP >= 10) {
            Intent i = new Intent();
            i.putExtra("scoreTOP", scoreTOP);
            i.putExtra("scoreBOT", scoreBOT);
            setResult(RESULT_OK, i);
            finish();
        } else if(scoreBOT >= 10){
            Intent ii = new Intent();
            ii.putExtra("scoreTOP", scoreTOP);
            ii.putExtra("scoreBOT", scoreBOT);
            setResult(RESULT_OK, ii);
            finish();
        }
    }
}
