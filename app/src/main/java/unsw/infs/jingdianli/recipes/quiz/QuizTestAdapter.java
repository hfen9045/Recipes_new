package unsw.infs.jingdianli.recipes.quiz;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import unsw.infs.jingdianli.recipes.R;

public class QuizTestAdapter extends RecyclerView.Adapter<QuizTestAdapter.QuizViewHolder> {

    private Context mContext;
    static public int[][] checkArray;


    class QuizViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_test_question;
        private final RadioGroup rg_test_answers;
        private final RadioButton rb_test_a;
        private final RadioButton rb_test_b;
        private final RadioButton rb_test_c;
        private final RadioButton rb_test_d;


        private QuizViewHolder( View itemView) {
            super(itemView);
            tv_test_question = itemView.findViewById( R.id.quiz_question);
            rg_test_answers = itemView.findViewById( R.id.radiogroup_choices);
            rb_test_a = itemView.findViewById( R.id.radio_btn_a);
            rb_test_b = itemView.findViewById( R.id.radio_btn_b);
            rb_test_c = itemView.findViewById( R.id.radio_btn_c);
            rb_test_d = itemView.findViewById( R.id.radio_btn_d);
        }
    }


    private final LayoutInflater mInflater;
    private List<Quiz> mQuiz = Collections.emptyList();

    QuizTestAdapter ( Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public QuizViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate( R.layout.activity_recyclerview_quizquestions, parent, false);

        return new QuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, final int position) {
        Quiz current = mQuiz.get(position);
        boolean isMcq = current.getIsMcq();
        String s;
        s = "Question " + (position + 1) + "\n\n" + current.toString();
        holder.tv_test_question.setText(s);
        holder.rb_test_a.setText(current.getOption1());
        holder.rb_test_b.setText(current.getOption2());

        if (isMcq) {
            holder.rb_test_c.setVisibility( View.VISIBLE);
            holder.rb_test_d.setVisibility( View.VISIBLE);
            holder.rb_test_c.setText(current.getOption3());
            holder.rb_test_d.setText(current.getOption4());
        } else {
            holder.rb_test_c.setVisibility( View.GONE);
            holder.rb_test_d.setVisibility( View.GONE);
        }

        if (current.getAnswer() == 1) {
            checkArray[position][1] = R.id.radio_btn_a;
        } else if (current.getAnswer() == 2) {
            checkArray[position][1] = R.id.radio_btn_b;
        } else if (current.getAnswer() == 3) {
            checkArray[position][1] = R.id.radio_btn_c;
        } else if (current.getAnswer() == 4){
            checkArray[position][1] = R.id.radio_btn_d;
        }

        holder.rg_test_answers.setOnCheckedChangeListener(null);
        if (holder.rg_test_answers.getCheckedRadioButtonId() != -1) {
            holder.rg_test_answers.clearCheck();
        }
        if (checkArray[position][0] != 0) {
            holder.rg_test_answers.check(checkArray[position][0]);
        }

        holder.rg_test_answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged( RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    checkArray[position][0] = checkedId;
                }
            }
        });
    }

    void setQuiz( List<Quiz> quizzes ) {
        mQuiz = quizzes;
        checkArray = new int[quizzes.size()][2];
        for(int i = 0; i < checkArray.length; i++) {
            for (int j = 0; j < checkArray[i].length; j++) {
                if (j == 0) {
                    checkArray[i][j] = 0;
                } else {
                    checkArray[i][j] = -1;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuiz.size();
    }
}


