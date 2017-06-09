package sorryjako.com.sorryjako;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.CommonConfetti;

import java.util.Random;

public class Victory extends AppCompatActivity {
    static final int floatingPeriod = 1000;
    static final float floatingFactor = 0.1f;

    static MediaPlayer mp;
    ImageButton restart;

    String scoreTOP;
    String scoreBOT;
    String winner;

    TextView tScoreBOT;
    TextView elseBOT;
    TextView winBOT;

    TextView tScoreTOP;
    TextView elseTOP;
    TextView winTOP;

    ViewGroup backTOP;
    ViewGroup backBOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.victory);

        mp = MediaPlayer.create(getApplicationContext(),R.raw.finish);
        mp.start();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if(bundle != null) {
            scoreBOT = (String) bundle.get("scoreBOT");
            scoreTOP = (String) bundle.get("scoreTOP");
            winner = (String) bundle.get("winner");
            //Toast.makeText(getApplicationContext(), "BOT: " + scoreBOT + " TOP: " + scoreTOP + " winner: " + winner, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Sorry jako, problém", Toast.LENGTH_SHORT).show();
        }

        tScoreBOT = (TextView) findViewById(R.id.id_victory_scoreBOT_TV);
        elseBOT = (TextView) findViewById(R.id.id_victory_elseBOT_TV);
        winBOT = (TextView) findViewById(R.id.id_victory_winBOT_TV);

        tScoreTOP = (TextView) findViewById(R.id.id_victory_scoreTOP_TV);
        elseTOP = (TextView) findViewById(R.id.id_victory_elseTOP_TV);
        winTOP = (TextView) findViewById(R.id.id_victory_winTOP_TV);

        backTOP = (ViewGroup) findViewById(R.id.id_victory_layoutTOP);
        backBOT = (ViewGroup) findViewById(R.id.id_victory_layoutBOT);

        restart = (ImageButton) findViewById(R.id.id_victory_restart_BT);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        Random random = new Random();
        int money = random.nextInt(46) + 5;


        if(winner.equals("B"))  {
            Spannable spanOne = new SpannableString(scoreBOT + ":" + scoreTOP);
            spanOne.setSpan(new ForegroundColorSpan(Color.parseColor("#EF0000")), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Spannable spanTwo = new SpannableString(scoreTOP + ":" + scoreBOT);
            spanTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#EF0000")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            winBOT.setText("Vítězství");
            winTOP.setText("Bude líp...");
            elseBOT.setText("ZÍSKÁVÁŠ DOTACI VE VÝŠI " + money +" MILIONŮ");
            elseTOP.setText(" ");
            tScoreBOT.setText(spanOne);
            tScoreTOP.setText(spanTwo);

//            CommonConfetti.explosion(backBOT, 0, 0, new int[] { Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW }).oneShot().setAccelerationX(10f);
        } else  {
            Spannable spanThree = new SpannableString(scoreBOT + ":" + scoreTOP);
            spanThree.setSpan(new ForegroundColorSpan(Color.parseColor("#EF0000")), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Spannable spanFour = new SpannableString(scoreTOP + ":" + scoreBOT);
            spanFour.setSpan(new ForegroundColorSpan(Color.parseColor("#EF0000")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            winBOT.setText("Bude líp...");
            winTOP.setText("Vítězství");
            elseBOT.setText(" ");
            elseTOP.setText("ZÍSKÁVÁŠ DOTACI VE VÝŠI " + money +" MILIONŮ");
            tScoreBOT.setText(spanThree);
            tScoreTOP.setText(spanFour);
        }
        biggerFaces.run();
    }

    @Override
    public void onBackPressed() {
    }

    final Runnable biggerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(restart.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleXBy(floatingFactor);
            a.scaleYBy(floatingFactor);
            a.setDuration(floatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(smallerFaces);
            }
            a.start();
        }
    };
    final Runnable smallerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(restart.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleXBy(-floatingFactor);
            a.scaleYBy(-floatingFactor);
            a.setDuration(floatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(biggerFaces);
            }
            a.start();
        }
    };
}
