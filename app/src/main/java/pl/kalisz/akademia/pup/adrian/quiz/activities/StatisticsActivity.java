package pl.kalisz.akademia.pup.adrian.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pl.kalisz.akademia.pup.adrian.quiz.R;
import pl.kalisz.akademia.pup.adrian.quiz.adapters.QuestionAdapter;
import pl.kalisz.akademia.pup.adrian.quiz.db.QuizDatabase;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

public class StatisticsActivity extends AppCompatActivity {
    QuizDatabase db;
    List<Question> questionsLearned;
    List<Question> questionsUnlearned;
    QuestionAdapter questionAdapter;
    RecyclerView recyclerView;
    Thread thread;
    TextView textViewTitle;
    int learned;
    int unlearned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        textViewTitle = findViewById(R.id.textViewTitle);
        db = QuizDatabase.getInstance(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(questionAdapter);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                questionsLearned = db.questionDao().getLearned();
                questionsUnlearned = db.questionDao().getUnlearned();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final TextView textViewLearned = findViewById(R.id.textViewLearned);
        learned = questionsLearned.size();
        unlearned = questionsUnlearned.size();
        int allQuestions = learned + unlearned;
        textViewLearned.setText("Nauczone słówka " + learned + "/" + allQuestions);
    }

    @SuppressLint("SetTextI18n")
    public void displayLearned(View view) {
        textViewTitle.setText("Nauczone słówka");
        questionAdapter = new QuestionAdapter(questionsLearned);
        recyclerView.setAdapter(questionAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void displayUnlearned(View view) {
        textViewTitle.setText("Słówka do nauki");
        questionAdapter = new QuestionAdapter(questionsUnlearned);
        recyclerView.setAdapter(questionAdapter);
    }

    public void resetProgression(View view) throws InterruptedException {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.questionDao().resetProgression();
            }
        });
        thread.start();
        thread.join();
        finish();
        startActivity(getIntent());
    }
}