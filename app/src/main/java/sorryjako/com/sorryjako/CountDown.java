package sorryjako.com.sorryjako;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.List;

import javax.xml.datatype.Duration;

public class CountDown extends AppCompatActivity {

    static final int countFrom = 4;
    static final int popDuration = 250;

    int lastChange = countFrom;

    Animation a;
    ViewPropertyAnimator popup, popdown;
//    AnimationSet set = new AnimationSet(true);

    TextView count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down);

        count = (TextView) findViewById(R.id.id_countdown_count_VT);

        final Runnable popDown = new Runnable() {
            @Override
            public void run() {
                count.animate().setDuration(popDuration).scaleXBy(-0.5f).scaleYBy(-0.5f);
            }
        };

        final Runnable popUp = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    count.animate().setDuration(0).scaleXBy(0.5f).scaleYBy(0.5f).withEndAction(popDown);
                }
            }
        };

        count.setText(Long.toString(countFrom));
        popUp.run();

        new CountDownTimer(countFrom*1000, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 <= lastChange) {
                    count.setText(Long.toString(lastChange));
                    popUp.run();
                    lastChange--;
                }
            }

            @Override
            public void onFinish() {
//                Intent i = new Intent(getApplicationContext(), Game.class);
//                startActivityForResult(i, 1);
                count.setText("Fight!");
                count.animate().scaleXBy(0.5f).scaleYBy(0.5f).setDuration(0);
                Intent i = new Intent(getApplicationContext(), Game.class);
                startActivityForResult(i, 5);
            }
        }.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 5) {
            if(resultCode == RESULT_OK) {
                Intent ii = new Intent();
                ii.putExtra("winner", data.getStringExtra("winner"));
                ii.putExtra("scoreTOP", data.getStringExtra("scoreTOP"));
                ii.putExtra("scoreBOT", data.getStringExtra("scoreBOT"));
                setResult(RESULT_OK, ii);
                finish();
            }
        }
    }
}
