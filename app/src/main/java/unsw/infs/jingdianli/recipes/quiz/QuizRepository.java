package unsw.infs.jingdianli.recipes.quiz;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


class QuizRepository {

    private QuizDao mQuizDao;


    private LiveData<List<Quiz>> mQuizTopic1;
    private LiveData<List<Quiz>> mQuizTopic2;
    private LiveData<List<Quiz>> mQuizTopic3;
    private LiveData<List<Quiz>> mQuizTopic4;
    private LiveData<List<Quiz>> mQuizTopic5;
    private LiveData<List<Quiz>> mQuizTopic6;
    private LiveData<List<Quiz>> mQuizTopic7;
    private LiveData<List<Quiz>> mQuizTopic8;



    QuizRepository ( Application application) {
        QuizRoomDatabase db = QuizRoomDatabase.getDatabase(application);
        mQuizDao = db.quizDao();
        mQuizTopic1 = mQuizDao.getTopic1();
        mQuizTopic2 = mQuizDao.getTopic2();
        mQuizTopic3 = mQuizDao.getTopic3();
        mQuizTopic4 = mQuizDao.getTopic4();
        mQuizTopic5 = mQuizDao.getTopic5();
        mQuizTopic6 = mQuizDao.getTopic6();
        mQuizTopic7 = mQuizDao.getTopic7();
        mQuizTopic8 = mQuizDao.getTopic8();
    }






    LiveData<List<Quiz>> getTopic1Quiz() {
        return mQuizTopic1;
    }
    LiveData<List<Quiz>> getTopic2Quiz() {
        return mQuizTopic2;
    }
    LiveData<List<Quiz>> getTopic3Quiz() {
        return mQuizTopic3;
    }
    LiveData<List<Quiz>> getTopic4Quiz() {
        return mQuizTopic4;
    }
    LiveData<List<Quiz>> getTopic5Quiz() {
        return mQuizTopic5;
    }
    LiveData<List<Quiz>> getTopic6Quiz() {
        return mQuizTopic6;
    }
    LiveData<List<Quiz>> getTopic7Quiz() {
        return mQuizTopic7;
    }
    LiveData<List<Quiz>> getTopic8Quiz() {
        return mQuizTopic8;
    }



    void insert( Quiz quiz ) {
        new insertAsyncTask(mQuizDao).execute( quiz );
    }

    private static class insertAsyncTask extends AsyncTask<Quiz, Void, Void> {

        private QuizDao mAsyncTaskDao;

        insertAsyncTask( QuizDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Quiz... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
