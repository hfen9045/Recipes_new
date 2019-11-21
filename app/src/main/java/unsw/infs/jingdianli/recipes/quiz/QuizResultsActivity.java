package unsw.infs.jingdianli.recipes.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.activities.MainActivity;

public class QuizResultsActivity extends AppCompatActivity {

    ImageView flower;
    Button bt;
    int correctCount;
    int questionCount;
    int topic;
    boolean[] correctArray;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_quiz_results);


        Intent intent = getIntent();
        correctCount = intent.getIntExtra("KEYCORRECT", 0);
        questionCount = intent.getIntExtra("KEYTOTAL", 0);
        topic = intent.getIntExtra("TOPIC_REVIEW", -1);
        correctArray = intent.getBooleanArrayExtra("KEYCORRECTARRAY");

        TextView tv_result = findViewById( R.id.result_display_textview);
        flower = findViewById( R.id.cong);
        if(correctCount == 5){
            String message = "Bingo!!! Your result is " + correctCount + "/" + questionCount;
            tv_result.setText(message);
            flower.setVisibility( View.VISIBLE);
        }else {
            String message = "Your result is " + correctCount + "/" + questionCount;
            tv_result.setText(message);
            flower.setVisibility( View.INVISIBLE);
        }

        bt= findViewById( R.id.share_btn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                Intent myIntent = new Intent ( Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "I have finished topic " +topic+ " Green Quiz in Green Books apps"+" My score is: "+correctCount + "/" + questionCount;
                String shareSub = "GreenBook is awesome!";
                myIntent.putExtra( Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra( Intent.EXTRA_TEXT,shareBody);
                startActivity( Intent.createChooser(myIntent,"Share using"));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent ( QuizResultsActivity.this, MainActivity.class));
        finish();
    }

    public void toReview( View view){

        Intent intent = new Intent (this, QuizReviewActivity.class);

        intent.putExtra("KEYCORRECT", correctCount);
        intent.putExtra("KEYTOTAL", questionCount);
        intent.putExtra("TOPIC_REVIEW", topic);
        intent.putExtra("KEYCORRECTARRAY", correctArray);
        startActivity(intent);
    }
}
