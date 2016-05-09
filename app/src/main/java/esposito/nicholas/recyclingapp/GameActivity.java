package esposito.nicholas.recyclingapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    ImageView green,black,blue,trash,rubbish,okview;
    TextView scoreView;
    public static final String IMAGEVIEW_TAG = "BANANA";
    public static final String BLUEBIN_TAG = "Blue";
    public static final String GREENBIN_TAG = "Green";
    public static final String BLACKBIN_TAG = "Black";
    public static final String TRASHCAN_TAG = "Trash";
    public String answer = "Black";
    private int index = 0;
    private int score = 0;
    String trashObjectName = "Error";
    CountDownTimer timer,timer1;
    ErrorCollection errorCollection = ErrorCollection.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        errorCollection.emptyErrors();
        green = (ImageView) findViewById(R.id.greenBasketImg);
        black = (ImageView) findViewById(R.id.organicBasketImg);
        blue = (ImageView) findViewById(R.id.cardboardBag);
        trash = (ImageView)findViewById(R.id.TrashImg);
        rubbish = (ImageView) findViewById(R.id.trashObject);
        scoreView = (TextView) findViewById(R.id.ScoreView);
        okview = (ImageView) findViewById(R.id.okview);
        final TextView countdownView = (TextView) findViewById(R.id.timerView);
        //Assign a touch listener to the rubbish object to make it draggable
        rubbish.setOnTouchListener(new MyTouchListener());
        rubbish.setTag(IMAGEVIEW_TAG);
        Context context = this.getApplicationContext();
        //Assign all the bins with a listener for dragging operation and a String tag to understand on which bin the object has landed
        green.setOnDragListener(new MyDragListener(context));
        green.setTag(GREENBIN_TAG);
        black.setOnDragListener(new MyDragListener(context));
        black.setTag(BLACKBIN_TAG);
        blue.setOnDragListener(new MyDragListener(context));
        blue.setTag(BLUEBIN_TAG);
        trash.setOnDragListener(new MyDragListener(context));
        trash.setTag(TRASHCAN_TAG);
        timer = new CountDownTimer(11000,1000) {//A countdoun timer is set for the player allowed playing time
            @Override
            public void onTick(long millisUntilFinished) {
                countdownView.setText("Timer: "+(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {//When the timer finish a dialog pops up
                countdownView.setText("Timer: 0");
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(GameActivity.this);
                builderSingle.setTitle("Time is over!");
                builderSingle.setMessage("You scored: "+score);
                if(errorCollection.getErrors().size()>0){
                builderSingle.setPositiveButton("See my errors", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                GameActivity.this);
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GameActivity.this, R.layout.error_list,R.id.error_text);
                        arrayAdapter.addAll(errorCollection.getErrors());
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        finish();
                                    }
                                });
                        builderInner.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "http://www.bathnes.gov.uk/services/bins-rubbish-and-recycling/recycling-and-rubbish-collections";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                finish();
                                startActivity(i);
                            }
                        });
                        builderInner.show();
                    }
                });}
                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builderSingle.show();
            }
        };
        timer.start();

        timer1 = new CountDownTimer(400,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                okview.setVisibility(View.GONE);
            }
        };
    }

    class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("IMAGE", view.getTag().toString());//Assign metadata to the image so that the bin can see what object has landed
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);//Draw the shadow of the object being dragged on the screen
                view.startDrag(data, shadowBuilder, view, 0);//start the drag operation when the object is touched
                return true;//drag operation active
            } else {
                return false;//if the object is not touched don't start a drag operation
            }
        }
    }


    class MyDragListener implements View.OnDragListener
    {
        Context context;

        public MyDragListener(Context context)
        {
            this.context = context;
        }
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //v.setBackgroundColor(Color.parseColor("#22228C"));//when the drag operation starts change background to all the listeners
                    break;
                case DragEvent.ACTION_DRAG_ENTERED://When the dragged object zoom
                    v.setScaleX((float) 1.2);
                    v.setScaleY((float) 1.2);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundColor(Color.BLUE);//When the dragged object exits one of the listeners area change background
                    v.setScaleX(1);
                    v.setScaleY(1);
                    break;
                case DragEvent.ACTION_DROP://When an object is dropped on one of the listeners
                    ClipData.Item item = event.getClipData().getItemAt(0);//Get the object's metadata
                    String dragdata = item.getText().toString();
                    view.setVisibility(View.INVISIBLE);//chenge the visibility of the object to invisible (has been trashed)
                    if(v.getTag().toString().contains(answer))//If the rubbish is trashed in the correct bin increase score
                    {  //Toast.makeText(context,"Great!",Toast.LENGTH_SHORT).show();
                        score++;
                        scoreView.setText("Score: "+score);
                        okview.setImageResource(R.drawable.earthsmile);
                        okview.setVisibility(View.VISIBLE);
                        MediaPlayer mp =  MediaPlayer.create(getApplicationContext(), R.raw.correct);
                        mp.start();
                        timer1.start();
                    }
                    else
                    {Toast.makeText(context, dragdata.toLowerCase() + "s should be trashed in the " + answer+ " bin!", Toast.LENGTH_SHORT).show();
                        okview.setImageResource(R.drawable.sadearth);
                        okview.setVisibility(View.VISIBLE);
                        timer1.start();
                        errorCollection.addError(dragdata.toLowerCase() + "s should be trashed in the " + answer+ " bin!");
                        MediaPlayer mp1 = MediaPlayer.create(getApplicationContext(), R.raw.error);
                    mp1.start();
                   }
                    changeImage();//get the new trash object
                    break;
                case DragEvent.ACTION_DRAG_ENDED://Bring everything back to the original state as the object stops being dragged
                    view.setVisibility(View.VISIBLE);
                    v.setScaleX(1);
                    v.setScaleY(1);
                    view.animate();
                default:
                    break;
            }
            return true;//this listener is active
        }
    }

    private void changeImage(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TrashObjects");//Get the trash objects
        query.whereEqualTo("type", 1);//Get all the objects
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    if (index == objectList.size())//the game keeps going untill the last question in the list has been reached
                    {
                        index = 0;
                    } else {
                        trashObjectName = objectList.get(index).get("trashObjectName").toString();//get the name of the current object
                        answer = (String) objectList.get(index).get("answer");//get the correct bin the current object has to be trashed
                        ParseFile fileObject = (ParseFile) objectList.get(index).get("trashImage");//A picture is retrieved from the database
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    rubbish.setImageBitmap(bmp);
                                    rubbish.setTag(trashObjectName);//Image is loaded and assigned a name
                                } else {
                                    Log.d("test", "There was a problem downloading the data.");
                                }
                            }
                        });
                        index++;//increment the index for the next time the object has to be changed
                    }
                } else {
                    Log.d("Error", "Error: " + e.getMessage());
                    Toast.makeText(GameActivity.this,"Errore "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
