package esposito.nicholas.recyclingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    TextView text1,text2;
    ImageView Img,back,home,next;
    int index = -1;
    int resourceID[] = {R.drawable.firstpage,R.drawable.page2,R.drawable.thirdpage,R.drawable.figure4,R.drawable.figure5,R.drawable.figure6,R.drawable.figure7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        final RelativeLayout instructionsLayout = (RelativeLayout) findViewById(R.id.Instructions_layout);
        text1 = (TextView)findViewById(R.id.instruction_text_1);
        text2 = (TextView)findViewById(R.id.instruction_text_2);
        Img = (ImageView) findViewById(R.id.instruction_img);
        back = (ImageView) findViewById(R.id.backArrowInstructions);
        home = (ImageView) findViewById(R.id.HomeButtonInstructions);
        next = (ImageView) findViewById(R.id.nextArrowInstructions);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home.getVisibility() == View.INVISIBLE){
                    finish();
                }
                else {
                    if(index==0) {
                        instructionsLayout.setBackgroundResource(R.drawable.whiteb);
                        text1.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                        Img.setVisibility(View.VISIBLE);
                        home.setVisibility(View.INVISIBLE);
                        index--;
                    }
                    else
                    {index--;
                    instructionsLayout.setBackgroundResource(resourceID[index]);}
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home.getVisibility() == View.INVISIBLE)
                {home.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.INVISIBLE);
                    text2.setVisibility(View.INVISIBLE);
                    Img.setVisibility(View.INVISIBLE);
                }
                index++;
                instructionsLayout.setBackgroundResource(resourceID[index]);
                if(index== (resourceID.length-1)){next.setVisibility(View.INVISIBLE);}


            }
        });

    }
}
