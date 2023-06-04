package pl.kalisz.akademia.pup.adrian.quiz.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kalisz.akademia.pup.adrian.quiz.R;
import pl.kalisz.akademia.pup.adrian.quiz.adapters.QuestionAdapter;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

public class SummaryActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<Question> questionsLearned;
    QuestionAdapter questionAdapter;
    RecyclerView recyclerView;
    TextView textViewTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);

        Bundle extras = getIntent().getExtras();
        int points = extras.getInt("points");
        questionsLearned = (ArrayList<Question>) getIntent().
                getSerializableExtra("questionsLearned");

        TextView textViewPoints = findViewById(R.id.textViewPoints);
        textViewPoints.setText("Uzyskany wynik " + points + "/5");
        textViewTitle = findViewById(R.id.textViewTitle);
        int numberOfLearnedQuestions = questionsLearned.size();
        if (numberOfLearnedQuestions > 0) {
            textViewTitle.setText("Gratulacje! Nauczyłeś/aś się nowych słówek");
            questionAdapter = new QuestionAdapter(questionsLearned);
        }
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(questionAdapter);
    }

    public void tryAgain(View view) {
        intent = new Intent(SummaryActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    public void backToHomepage(View view) {
        intent = new Intent(SummaryActivity.this, HomepageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}