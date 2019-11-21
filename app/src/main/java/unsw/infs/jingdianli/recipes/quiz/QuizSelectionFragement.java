package unsw.infs.jingdianli.recipes.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import unsw.infs.jingdianli.recipes.R;

public class QuizSelectionFragement extends Fragment {

    ListView listView;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate( R.layout.activity_quizzes_list, container, false);




        final String[] topicHeadingLevelOneArray = {
                "Quiz 1: The Truth about Metabolism",
                "Quiz 2: Exercise, Heart and Blood",
                "Quiz 3: Know about Your Muscle",
                "Quiz 4: Fitness and Diet",
                "Quiz 5: Fitness Dos and Don'ts I",
                "Quiz 6: Fitness Dos and Don'ts II",
                "Quiz 7: Fitness Dos and Don'ts III",
                "Quiz 8: Do You Know The Benefits of Walking?",

        };

        final String[] topicHeadingLevelTwoArray = {
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",
                "5 Questions --> ",

        };

        QuizSelectionAdapter newQuizSelectionAdapter = new QuizSelectionAdapter(getActivity (), topicHeadingLevelOneArray, topicHeadingLevelTwoArray);
        listView = (ListView) view.findViewById( R.id.quizzes_list);
        listView.setAdapter(newQuizSelectionAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (getActivity (), QuizTestActivity.class);
                intent.putExtra("TOPIC_TEST", (position + 1));
                startActivity(intent);

            }
        });
     return view;
    }
}