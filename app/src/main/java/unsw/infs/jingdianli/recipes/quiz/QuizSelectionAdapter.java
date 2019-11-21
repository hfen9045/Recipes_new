package unsw.infs.jingdianli.recipes.quiz;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import unsw.infs.jingdianli.recipes.R;

public class QuizSelectionAdapter extends ArrayAdapter {


    private final Activity context;
    private final String[] topicHeadingLevelOneArray;
    private final String[] topicHeadingLevelTwoArray;



    public QuizSelectionAdapter ( Activity context , String[] topicHeadingLevelOneArray, String[] topicHeadingLevelTwoArray) {

        super(context, R.layout.activity_quizlistview_selection_item, topicHeadingLevelOneArray);

        this.context = context;
        this.topicHeadingLevelOneArray = topicHeadingLevelOneArray;
        this.topicHeadingLevelTwoArray = topicHeadingLevelTwoArray;

    }

    public View getView( int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate( R.layout.activity_quizlistview_selection_item, null,true);
        TextView topicTV = (TextView) rowView.findViewById( R.id.single_quiz_title);
        TextView line2TV = (TextView) rowView.findViewById( R.id.single_quiz_subtitle);
        topicTV.setText(topicHeadingLevelOneArray[position]);
        line2TV.setText(topicHeadingLevelTwoArray[position]);

        return rowView;
    };
}
