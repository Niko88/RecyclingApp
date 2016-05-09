package esposito.nicholas.recyclingapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;

public class Introduction extends AppCompatActivity {

    ImageView mImageViewFilling;
    Button highscores;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Button play = (Button) findViewById(R.id.playButton);
        highscores = (Button) findViewById(R.id.higher_scores_button);
        Button instructions = (Button) findViewById(R.id.instructions_button);
        mImageViewFilling = (ImageView) findViewById(R.id.animation);
        mImageViewFilling.setBackgroundResource(R.drawable.home_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
        frameAnimation.start();
        //((AnimationDrawable) mImageViewFilling.getBackground()).start();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.rainfall);//a sound is played


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Introduction.this,GameActivity.class));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();

    }
}
