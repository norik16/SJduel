package sorryjako.com.sorryjako;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Game extends AppCompatActivity {
    static final int numberOfLines = 100;
    static final int globalDelay = 1000;
    static final int facesFloatingPeriod = 1200;
    static final float facesFloatingFactor = 0.06f;
    static MediaPlayer mp;
    static MediaPlayer mpZeman;
    static MediaPlayer mpBabis;
    ImageButton babisTOP;
    ImageButton babisBOT;
    ImageButton zemanTOP;
    ImageButton zemanBOT;
    
    ImageButton aPerson;
    final Runnable biggerFaces = new Runnable() {
        @Override
        public void run() {
            makeSmaller(babisTOP.animate());
            makeSmaller(babisBOT.animate());
            makeSmaller(zemanTOP.animate());
            makeSmaller(zemanBOT.animate());
        }

        private void makeSmaller(ViewPropertyAnimator a) {
            a.scaleX(1+facesFloatingFactor);
            a.scaleY(1+facesFloatingFactor);
            a.setDuration(facesFloatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(smallerFaces);
            }
            a.start();
        }
    };
    final Runnable smallerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(babisTOP.animate());
            makeBigger(babisBOT.animate());
            makeBigger(zemanTOP.animate());
            makeBigger(zemanBOT.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleX(1-facesFloatingFactor);
            a.scaleY(1-facesFloatingFactor);
            a.setDuration(facesFloatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(biggerFaces);
            }
            a.start();
        }
    };

    final Runnable sBigBoing = new Runnable() {
        @Override
        public void run() {
            makeSmaller(aPerson.animate());
        }

        private void makeSmaller(ViewPropertyAnimator a) {
            a.scaleX(1+facesFloatingFactor*2.1f);
            a.scaleY(1+facesFloatingFactor*2.1f);
            a.setDuration(facesFloatingPeriod/10);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(sSmallBoing);
            }
            a.start();
        }
    };
    final Runnable sSmallBoing = new Runnable() {
        @Override
        public void run() {
            makeBigger(aPerson.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleX(1);
            a.scaleY(1);
            a.setDuration(facesFloatingPeriod/10);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(biggerFaces);
            }
            a.start();
        }
    };
//    final Runnable sEndBoing = new Runnable() {
//        @Override
//        public void run() {
//            aPerson.animate().scaleX()
//            makeBigger(aPerson.animate());
//        }
//
//        private void makeBigger(ViewPropertyAnimator a) {
//            a.scaleXBy(-facesFloatingFactor*3);
//            a.scaleYBy(-facesFloatingFactor*3);
//            a.setDuration(facesFloatingPeriod/10);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                a.withEndAction(biggerFaces);
//            }
//            a.start();
//        }
//    };

    AutoResizeTextView textTOP;
    AutoResizeTextView textBOT;
    TextView tScoreTOP;
    TextView tScoreBOT;
    TextView tScoreHisTOP;
    TextView tScoreHisBOT;

    ImageView resultTOP;
    ImageView resultBOT;
    LinearLayout linearLayout;
    int scoreTOP;
    int scoreBOT;
    int lastLine;
    int[] usedLines;
    String actPerson;
    SQLiteDatabase sqLiteDatabase;
    Database database;
    Cursor cursor;

    //win: TOP - 1, BOT - 0
    int truefalse = 0;

    LinearLayout blueBar;
    LinearLayout redBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);


        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        babisTOP = (ImageButton) findViewById(R.id.id_game_babisTOP_BT);
        babisBOT = (ImageButton) findViewById(R.id.id_game_babisBOT_BT);
        zemanTOP = (ImageButton) findViewById(R.id.id_game_zemanTOP_BT);
        zemanBOT = (ImageButton) findViewById(R.id.id_game_zemanBOT_BT);

        textBOT = (AutoResizeTextView) findViewById(R.id.id_game_textBOT_TV);
        textTOP = (AutoResizeTextView) findViewById(R.id.id_game_textTOP_TV);
        tScoreBOT = (TextView) findViewById(R.id.id_game_scoreBOT_TV);
        tScoreTOP = (TextView) findViewById(R.id.id_game_scoreTOP_TV);
        tScoreHisBOT = (TextView) findViewById(R.id.id_game_hisScoreBOT_TV);
        tScoreHisTOP = (TextView) findViewById(R.id.id_game_hisScoreTOP_TV);

        redBar = (LinearLayout) findViewById(R.id.id_game_redBar_LL);
        blueBar = (LinearLayout) findViewById(R.id.id_game_blueBar_LL);

        resultBOT = (ImageView) findViewById(R.id.id_game_resultBOT_IV);
        resultTOP = (ImageView) findViewById(R.id.id_game_resultTOP_IV);

        if(mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(getApplicationContext(), R.raw.next_question);
        mp.start();

        //Setting text
        tScoreBOT.setText("0");
        tScoreTOP.setText("0");
        tScoreHisBOT.setText("0");
        tScoreHisTOP.setText("0");
        textBOT.setText("Sorry Jako");
        textTOP.setText("Sorry Jako");
        actPerson = "B";


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
                if (actPerson.equals("B")) {
                    addScore("TOP");
                    truefalse = 1;
                    if(mp != null) {
                        mp.release();
                    }
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.next_question);
                    mp.start();
                    resultTOP.setImageResource(R.mipmap.red_true);
                    resultBOT.setImageResource(R.mipmap.blue_fault);
                } else {
                    if(mpBabis != null) {
                        mp.release();
                    }
                    mpBabis = MediaPlayer.create(getApplicationContext(), R.raw.babis);
                    mpBabis.start();
                    truefalse = 0;
                    addScore("BOT");
                    resultTOP.setImageResource(R.mipmap.red_fault);
                    resultBOT.setImageResource(R.mipmap.blue_true);
                }
                aPerson = babisTOP;
                sBigBoing.run();
                getLine("babisTOP");
            }
        });

        babisBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Game/onClick", "clicked...");
                if (actPerson.equals("B"))  {
                    addScore("BOT");
                    truefalse = 0;
                    if(mp != null) {
                        mp.release();
                    }
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.next_question);
                    mp.start();
                    resultTOP.setImageResource(R.mipmap.red_fault);
                    resultBOT.setImageResource(R.mipmap.blue_true);
                }
                else{
                    if(mpBabis != null) {
                        mp.release();
                    }
                    mpBabis = MediaPlayer.create(getApplicationContext(), R.raw.babis);
                    mpBabis.start();
                    truefalse = 1;
                    addScore("TOP");
                    resultTOP.setImageResource(R.mipmap.red_true);
                    resultBOT.setImageResource(R.mipmap.blue_fault);
                }
                aPerson = babisBOT;
                sBigBoing.run();
                getLine("babisBOT");
            }
        });

        zemanTOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actPerson.equals("B")) {
                    addScore("BOT");
                    truefalse = 0;
                    if(mpZeman != null) {
                        mp.release();
                    }
                    mpZeman = MediaPlayer.create(getApplicationContext(), R.raw.zeman);
                    mpZeman.start();
                    resultTOP.setImageResource(R.mipmap.red_fault);
                    resultBOT.setImageResource(R.mipmap.blue_true);
                } else{
                    addScore("TOP");
                    truefalse = 1;
                    if(mp != null) {
                        mp.release();
                    }
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.next_question);
                    mp.start();
                    resultTOP.setImageResource(R.mipmap.red_true);
                    resultBOT.setImageResource(R.mipmap.blue_fault);
                }
                aPerson = zemanTOP;
                sBigBoing.run();
                getLine("zemanTOP");
            }
        });

        zemanBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actPerson.equals("B")) {
                    addScore("TOP");
                    truefalse = 1;
                    if(mpZeman != null) {
                        mp.release();
                    }
                    mpZeman = MediaPlayer.create(getApplicationContext(), R.raw.zeman);
                    mpZeman.start();
                    resultTOP.setImageResource(R.mipmap.red_true);
                    resultBOT.setImageResource(R.mipmap.blue_fault);
                }
                else{
                    addScore("BOT");
                    truefalse = 0;
                    if(mp != null) {
                        mp.release();
                    }
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.next_question);
                    mp.start();
                    resultTOP.setImageResource(R.mipmap.red_fault);
                    resultBOT.setImageResource(R.mipmap.blue_true);
                }

                aPerson = zemanBOT;
                sBigBoing.run();
                getLine("zemanBOT");
            }
        });

        biggerFaces.run();

        getLine();
    }

    @Override
    public void onBackPressed() {
    }

    protected void getLine() {
        getLine("nothing");
    }

    protected void getLine(final String person) {
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

            Log.e("Game/getLine", person);

            if (! "nothing".equals(person)) {
                zemanBOT.setEnabled(false);
                zemanTOP.setEnabled(false);
                babisBOT.setEnabled(false);
                babisTOP.setEnabled(false);

                resultBOT.setVisibility(View.VISIBLE);
                resultTOP.setVisibility(View.VISIBLE);


                final Integer finalId = id;
                new CountDownTimer(globalDelay, globalDelay/3) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (millisUntilFinished / (globalDelay/3) % 2 == 0)
                            switch (person) {
                                case "babisTOP":
//                                    textTOP.setText(" ");
//                                    textBOT.setText(" ");
//                                    if(truefalse == 0) {
//                                        redBar.setBackgroundResource(R.mipmap.red_fault);
//                                        blueBar.setBackgroundResource(R.mipmap.blue_true);
//                                    } else {
//                                        redBar.setBackgroundResource(R.mipmap.red_true);
//                                        blueBar.setBackgroundResource(R.mipmap.blue_fault);
//                                    }
                                    babisTOP.setImageResource(R.mipmap.babis_open);
                                    break;
                                case "babisBOT":
//                                    textTOP.setText(" ");
//                                    textBOT.setText(" ");
//                                    if(truefalse == 0) {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_true);
//                                        redBar.setBackgroundResource(R.mipmap.red_fault);
//                                    } else {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_fault);
//                                        redBar.setBackgroundResource(R.mipmap.red_true);
//                                    }
                                    babisBOT.setImageResource(R.mipmap.babis_open);
                                    break;
                                case "zemanTOP":
//                                    textTOP.setText(" ");
//                                    textBOT.setText(" ");
//                                    if(truefalse == 0) {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_true);
//                                        redBar.setBackgroundResource(R.mipmap.red_fault);
//                                    } else {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_fault);
//                                        redBar.setBackgroundResource(R.mipmap.red_true);
//                                    }
                                    zemanTOP.setImageResource(R.mipmap.zeman_open);
//                                    redBar.setBackgroundResource(R.mipmap.red_true);
                                    break;
                                case "zemanBOT":
//                                    textTOP.setText(" ");
//                                    textBOT.setText(" ");
//                                    if(truefalse == 0) {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_true);
//                                        redBar.setBackgroundResource(R.mipmap.red_fault);
//                                    } else {
//                                        blueBar.setBackgroundResource(R.mipmap.blue_fault);
//                                        redBar.setBackgroundResource(R.mipmap.red_true);
//                                    }
                                    zemanBOT.setImageResource(R.mipmap.zeman_open);
                                    break;
                            }
                        else
                            switch (person) {
                                case "babisTOP":
                                    babisTOP.setImageResource(R.mipmap.babis_close);
//                                    redBar.setBackgroundResource(R.mipmap.bg_red);
//                                    blueBar.setBackgroundResource(R.mipmap.bg_blue);
                                    break;
                                case "babisBOT":
                                    babisBOT.setImageResource(R.mipmap.babis_close);
//                                    blueBar.setBackgroundResource(R.mipmap.bg_blue);
//                                    redBar.setBackgroundResource(R.mipmap.bg_red);
                                    break;
                                case "zemanTOP":
                                    zemanTOP.setImageResource(R.mipmap.zeman_close);
//                                    redBar.setBackgroundResource(R.mipmap.bg_red);
//                                    blueBar.setBackgroundResource(R.mipmap.bg_blue);
                                    break;
                                case "zemanBOT":
                                    zemanBOT.setImageResource(R.mipmap.zeman_close);

                                    break;
                            }
                        Log.e("Game/clock", "ticked");
                    }

                    @Override
                    public void onFinish() {
                        babisTOP.setImageResource(R.mipmap.babis_close);
                        babisBOT.setImageResource(R.mipmap.babis_close);
                        zemanTOP.setImageResource(R.mipmap.zeman_close);
                        zemanBOT.setImageResource(R.mipmap.zeman_close);

                        blueBar.setBackgroundResource(R.mipmap.bg_blue);
                        redBar.setBackgroundResource(R.mipmap.bg_red);

                        zemanBOT.setEnabled(true);
                        zemanTOP.setEnabled(true);
                        babisBOT.setEnabled(true);
                        babisTOP.setEnabled(true);

                        resultBOT.setVisibility(View.INVISIBLE);
                        resultTOP.setVisibility(View.INVISIBLE);

                        lastLine = cursor.getInt(2);
                        textTOP.setText(cursor.getString(0));
                        textBOT.setText(cursor.getString(0));
                        actPerson = cursor.getString(1);
                        usedLines[finalId] = 1;
                    }
                }.start();
            } else {
                lastLine = cursor.getInt(2);
                textTOP.setText(cursor.getString(0));
                textBOT.setText(cursor.getString(0));
                actPerson = cursor.getString(1);
                usedLines[id] = 1;
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

        if (scoreTOP >= 10) {
            Intent i = new Intent();
            i.putExtra("winner", "A");
            i.putExtra("scoreTOP", Integer.toString(scoreTOP));
            i.putExtra("scoreBOT", Integer.toString(scoreBOT));
            setResult(RESULT_OK, i);
            finish();
        } else if(scoreBOT >= 10){
            Intent ii = new Intent();
            ii.putExtra("winner", "B");
            ii.putExtra("scoreTOP", Integer.toString(scoreTOP));
            ii.putExtra("scoreBOT", Integer.toString(scoreBOT));
            setResult(RESULT_OK, ii);
            finish();
        }
    }


}
