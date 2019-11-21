package unsw.infs.jingdianli.recipes.quiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.activities.MainActivity;


public class QuizTestActivity extends AppCompatActivity {

    private QuizViewModel mQuizViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_quiz_questions);
        Intent intent = getIntent();
        final int topic = intent.getIntExtra("TOPIC_TEST", -1);

        RecyclerView recyclerView = findViewById( R.id.recyclerview_quiz);
        final QuizTestAdapter adapter = new QuizTestAdapter (this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        mQuizViewModel = ViewModelProviders.of(this).get( QuizViewModel.class);
        mQuizViewModel.getAllQuiz(topic).observe(this, new Observer<List<Quiz>> () {
            @Override
            public void onChanged(@Nullable final List<Quiz> quizzes ) {

                adapter.setQuiz( quizzes );
            }
        });

        Button fab = findViewById( R.id.submit_answer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                int[][] answerCheckArray = QuizTestAdapter.checkArray;

                int correctCount = 0;
                int questionCount = answerCheckArray.length;

                boolean[] correctArray = new boolean[answerCheckArray.length];
                for (int i = 0; i < correctArray.length; i++) {
                    correctArray[i] = false;
                }

                String s = "";
                for (int i = 0; i < answerCheckArray.length; i++) {
                    if (i != 0) {
                        s += "\n";
                    }
                    switch (answerCheckArray[i][0]) {
                        case R.id.radio_btn_a:
                            s += "Q" + (i + 1) + ": selection A |";
                            break;
                        case R.id.radio_btn_b:
                            s += "Q" + (i + 1) + ": selection B |";
                            break;
                        case R.id.radio_btn_c:
                            s += "Q" + (i + 1) + ": selection C |";
                            break;
                        case R.id.radio_btn_d:
                            s += "Q" + (i + 1) + ": selection D |";
                            break;
                        default:
                            s += "Q" + (i + 1) + ": selection none |";
                    }
                    switch (answerCheckArray[i][1]) {
                        case R.id.radio_btn_a:
                            s += " answer A";
                            break;
                        case R.id.radio_btn_b:
                            s += " answer B";
                            break;
                        case R.id.radio_btn_c:
                            s += " answer C";
                            break;
                        case R.id.radio_btn_d:
                            s += " answer D";
                            break;
                        case -1:
                            s += " empty";
                            break;
                        default:
                            s += " should not appear";
                    }

                    if (answerCheckArray[i][0] == answerCheckArray[i][1]) {
                        correctCount++;
                        correctArray[i] = true;
                    }
                }

                s += "\n" + correctCount;
                s += "\n" + questionCount;

                Intent intent = new Intent ( QuizTestActivity.this, QuizResultsActivity.class);
                intent.putExtra("KEYCORRECT", correctCount);
                intent.putExtra("KEYTOTAL", questionCount);
                intent.putExtra("TOPIC_REVIEW", topic);
                intent.putExtra("KEYCORRECTARRAY", correctArray);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent ( QuizTestActivity.this, MainActivity.class));
        finish();
    }
}


