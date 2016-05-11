package esposito.nicholas.recyclingapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class IntroVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_video);
        final VideoView videoHolder = (VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.recyclingamevideo);
        videoHolder.setVideoURI(video);
        videoHolder.start();
        TextView skip = (TextView) findViewById(R.id.skipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoHolder.stopPlayback();
                finish();
                startActivity(new Intent(IntroVideoActivity.this,Introduction.class));
            }
        });
            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                    startActivity(new Intent(IntroVideoActivity.this,Introduction.class));//If app has previously been opened don't show video
                }
            });
    }

}
