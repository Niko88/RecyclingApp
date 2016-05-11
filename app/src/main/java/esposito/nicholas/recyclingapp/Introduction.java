package esposito.nicholas.recyclingapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;

public class Introduction extends AppCompatActivity {

    ImageView mImageViewFilling;
    Boolean isSoundOn = true;
    MediaPlayer mp;
    private LocalDB db = new LocalDB(Introduction.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreferences.getAccess(Introduction.this).length() != 0) {//Check if the app has previously been opened
            setContentView(R.layout.activity_introduction);
            Button play = (Button) findViewById(R.id.playButton);
            Button instructions = (Button) findViewById(R.id.instructions_button);
            final ImageView soundToggler = (ImageView) findViewById(R.id.sound_toggle);
            mp = MediaPlayer.create(getApplicationContext(), R.raw.rainfall);//a sound is played
            soundToggler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSoundOn) {
                        soundToggler.setImageResource(R.drawable.sound_on);
                        isSoundOn = false;
                        mp.pause();
                    } else {
                        soundToggler.setImageResource(R.drawable.sound_off);
                        isSoundOn = true;
                        mp.start();
                    }
                }
            });

            mImageViewFilling = (ImageView) findViewById(R.id.animation);
            mImageViewFilling.setBackgroundResource(R.drawable.home_animation);
            AnimationDrawable frameAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
            frameAnimation.start();
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Introduction.this, GameActivity.class);
                    i.putExtra("SOUNDSTATUS", isSoundOn);
                    startActivity(i);
                }
            });

            instructions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Introduction.this, InstructionsActivity.class));
                }
            });
        }
        else{
            SaveSharedPreferences.setUserName(getApplication(),"FirstAccess");
                finish();
                startActivity(new Intent(Introduction.this,IntroVideoActivity .class));//If app has not previously been opened show video

        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(isSoundOn)
        mp.start();
        TextView highscoreLabel = (TextView) findViewById(R.id.highscoreLabel);
        if(db.getHighScore()!=null)
        {
            highscoreLabel.setText("High score: "+ db.getHighScore());
            highscoreLabel.setVisibility(View.VISIBLE);}
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSoundOn)
        mp.pause();

    }
}
