package unsw.infs.jingdianli.recipes.quiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import unsw.infs.jingdianli.recipes.R;

public class QuizReviewAdapter extends RecyclerView.Adapter<QuizReviewAdapter.QuizViewHolder> {

    public boolean[] mQuizResults;

    class QuizViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_review_question;
        private final TextView tv_review_result;
        private final TextView tv_review_answer;
        private final TextView tv_review_explanation;


        private QuizViewHolder( View itemView) {
            super(itemView);
            tv_review_question = itemView.findViewById( R.id.review_question_display);
            tv_review_result = itemView.findViewById( R.id.single_review_result);
            tv_review_answer = itemView.findViewById( R.id.review_correct_answer);
            tv_review_explanation = itemView.findViewById( R.id.review_question_explanation);
        }
    }

    private final LayoutInflater mInflater;
    private List<Quiz> mQuiz = Collections.emptyList();

    QuizReviewAdapter( Context context, boolean[] mResults) {
        mInflater = LayoutInflater.from(context);
        mQuizResults = mResults;
    }

    @Override
    public QuizViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate( R.layout.activity_recyclerview_review_item, parent, false);
        return new QuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        Quiz current = mQuiz.get(position);
        String s;
        s = "Question " + (position + 1) + "\n\n" + current.toString();
        holder.tv_review_question.setText(s);
        holder.tv_review_result.setText(mQuizResults[position] ? "true" : "false");
        holder.tv_review_result.setTextColor( Color.parseColor(mQuizResults[position] ? "#FF589696" : "#FFAC5338"));

        String answer;
        switch (current.getAnswer()) {
            case 1:
                answer = "Answer: " + current.getOption1();
                break;
            case 2:
                answer = "Answer: " + current.getOption2();
                break;
            case 3:
                answer = "Answer: " + current.getOption3();
                break;
            case 4:
                answer = "Answer: " + current.getOption4();
                break;
            default:
                answer = "should not appear";
                break;
        }

        holder.tv_review_answer.setText(answer);
        if (!current.getExplanation().equals("")) {
            holder.tv_review_explanation.setVisibility( View.VISIBLE);
            String explanation = "Explanation: " + current.getExplanation();
            holder.tv_review_explanation.setText(explanation);
        } else {
            holder.tv_review_explanation.setVisibility( View.GONE);
        }


    }



    void setQuiz( List<Quiz> quizzes ) {
        mQuiz = quizzes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuiz.size();
    }
}

