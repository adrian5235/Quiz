package pl.kalisz.akademia.pup.adrian.quiz.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.kalisz.akademia.pup.adrian.quiz.db.daos.AnswerDao;
import pl.kalisz.akademia.pup.adrian.quiz.db.daos.QuestionDao;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Answer;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

@Database(entities = {Question.class, Answer.class}, exportSchema = false, version = 2)
public abstract class QuizDatabase extends RoomDatabase {
    private static final String DB_NAME = "quiz_db";
    private static QuizDatabase instance;

    public static synchronized QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), QuizDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();
}
