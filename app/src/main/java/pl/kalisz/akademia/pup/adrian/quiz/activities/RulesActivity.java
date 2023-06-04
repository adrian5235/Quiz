package pl.kalisz.akademia.pup.adrian.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import pl.kalisz.akademia.pup.adrian.quiz.R;

public class RulesActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView textViewRules = findViewById(R.id.textViewRules);
        textViewRules.setText("Gdy dane słówko zostanie poprawnie przetłumaczone 3 razy z rzędu," +
                " wówczas zostaje ono uznane za nauczone.");
    }
}