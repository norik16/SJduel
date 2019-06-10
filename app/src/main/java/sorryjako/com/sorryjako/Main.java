package sorryjako.com.sorryjako;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

import com.google.ads.consent.*;

import com.google.firebase.analytics.FirebaseAnalytics;


public class Main extends AppCompatActivity {
    static final int floatingPeriod = 1000;
    static final float floatingFactor = 0.1f;

    static MediaPlayer mp;
    int duration = 0;
    ImageButton play;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_app_updates, null);
        CheckBox mCheckBox = mView.findViewById(R.id.checkBox);

        mBuilder.setTitle("Zásady ochrany soukromí (GDPR)");
        final SpannableString s = new SpannableString("https://ronaldluc.com/data/sorry_GDPR_jako.pdf");
        Linkify.addLinks(s, Linkify.ALL);
        mBuilder.setMessage(s);
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(getDialogStatus()) {
                    dialogInterface.dismiss();
                } else {
                    finish();
                }

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        ((TextView)mDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    storeDialogStatus(true);
                }else{
                    storeDialogStatus(false);
                }
            }
        });

        if(getDialogStatus()){
            mDialog.hide();
        }else{
            mDialog.show();
        }

        /* ConsentInformation consentInformation = ConsentInformation.getInstance(getApplicationContext());
        String[] publisherIds = {"pub-0123456789012345"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
            }
        }); */

//        if(mp != null) {
//            mp.release();
//        }
//        mp = MediaPlayer.create(getApplicationContext(), R.raw.libuse);
//        mp.start();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        play = (ImageButton) findViewById(R.id.id_main_play_BT);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.reset();
                Intent i = new Intent(getApplicationContext(), CountDown.class);
                startActivityForResult(i, 1);
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Game opened");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);

        biggerFaces.run();
    }
    final Runnable biggerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(play.animate());
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
            makeBigger(play.animate());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if(data.getStringExtra("winner") != null) {
                    Intent ii =new Intent(getApplicationContext(), Victory.class);
                    ii.putExtra("winner", data.getStringExtra("winner"));
                    ii.putExtra("scoreTOP", data.getStringExtra("scoreTOP"));
                    ii.putExtra("scoreBOT", data.getStringExtra("scoreBOT"));
                    startActivityForResult(ii, 2);
                }
            }
        }
        if(requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Intent iii = new Intent(getApplicationContext(), CountDown.class);
                startActivityForResult(iii, 1);
            } else {
//                if(mp != null) {
//                    mp.release();
//                }
//                mp = MediaPlayer.create(getApplicationContext(), R.raw.libuse);
//                mp.start();
            }
        }
    }

    private void storeDialogStatus(boolean isChecked){
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus(){
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.libuse);
        mp.start();
        //mp.seekTo(duration);
    }


    @Override
    public void onPause()
    {
        super.onPause();
        if(mp != null) {
//            duration = mp.getDuration();
            mp.stop();
            mp.reset();
            mp.release();
        }

    }
}