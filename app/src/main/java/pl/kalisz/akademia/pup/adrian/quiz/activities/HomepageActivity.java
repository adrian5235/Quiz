package pl.kalisz.akademia.pup.adrian.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import pl.kalisz.akademia.pup.adrian.quiz.R;
import pl.kalisz.akademia.pup.adrian.quiz.db.QuizDatabase;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Answer;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

public class HomepageActivity extends AppCompatActivity {
    QuizDatabase db;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        setTitle("Strona główna");

        db = QuizDatabase.getInstance(this);

        // if user is running app for the first time after installation, setup database
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("firstTime", true)) {
            databaseSetup();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
        }

//        databaseSetup();

        final Button buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonStartQuiz.setOnClickListener(view -> {
            Intent intent = new Intent(HomepageActivity.this, QuizActivity.class);
            HomepageActivity.this.startActivity(intent);
        });

        final Button buttonStatistics = findViewById(R.id.buttonStatistics);
        buttonStatistics.setOnClickListener(view -> {
            Intent intent = new Intent(HomepageActivity.this, StatisticsActivity.class);
            HomepageActivity.this.startActivity(intent);
        });

        final Button buttonRules = findViewById(R.id.buttonRules);
        buttonRules.setOnClickListener(view -> {
            Intent intent = new Intent(HomepageActivity.this, RulesActivity.class);
            HomepageActivity.this.startActivity(intent);
        });
    }

    private void databaseSetup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = Arrays.asList(
                        new Question("buckler"),
                        new Question("waist"),
                        new Question("avalanche"),
                        new Question("flinch"),
                        new Question("dogged"),
                        new Question("chores"),
                        new Question("veil"),
                        new Question("rebuke"),
                        new Question("vigilance"),
                        new Question("concealment"),
                        new Question("solemn"),
                        new Question("dour"),
                        new Question("gaunt"));
//                        new Question("absolved"),
//                        new Question("ravaged"),
//                        new Question("snort"),
//                        new Question("modest"),
//                        new Question("hand out"),
//                        new Question("stool"),
//                        new Question("frown"),
//                        new Question("diffident"),
//                        new Question("irk"),
//                        new Question("grovel"),
//                        new Question("braid"),
//                        new Question("deposed"),
//                        new Question("smoldering"),
//                        new Question("reluctant"),
//                        new Question("cradle"),
//                        new Question("stout"),
//                        new Question("soothingly"),
//                        new Question("tangle"),
//                        new Question("to yank"),
//                        new Question("feeble"),
//                        new Question("grumpy"),
//                        new Question("washstand"),
//                        new Question("soap"),
//                        new Question("compassion"),
//                        new Question("laundry"),
//                        new Question("wrinkled"),
//                        new Question("temples"),
//                        new Question("seclusion"),
//                        new Question("apprehended"),
//                        new Question("ox"),
//                        new Question("flock"),
//                        new Question("to herde"),
//                        new Question("vase"),
//                        new Question("tile"),
//                        new Question("brisk"),
//                        new Question("grin"),
//                        new Question("apron"),
//                        new Question("wail"),
//                        new Question("utter"),
//                        new Question("trove"),
//                        new Question("curtsy"),
//                        new Question("razor"),
//                        new Question("to rattle"),
//                        new Question("stagger"),
//                        new Question("biddable"),
//                        new Question("sheet"),
//                        new Question("ceiling"),
//                        new Question("awe"),
//                        new Question("mangling"),
//                        new Question("ivory"),
//                        new Question("anchor"),
//                        new Question("solitude"),
//                        new Question("stocky"),
//                        new Question("lever"),
//                        new Question("grove"),
//                        new Question("sheer"),
//                        new Question("porridge"),
//                        new Question("angular"),
//                        new Question("whip"),
//                        new Question("reins"),
//                        new Question("scepter"),
//                        new Question("prod at someone"),
//                        new Question("gilded"),
//                        new Question("vile"),
//                        new Question("ferries"),
//                        new Question("hide"),
//                        new Question("boasting"),
//                        new Question("lace"),
//                        new Question("coach"),
//                        new Question("eyelid"),
//                        new Question("grunt"),
//                        new Question("dove"),
//                        new Question("cudgel"),
//                        new Question("stirrup"),
//                        new Question("dome"),
//                        new Question("exalt"));

                List<Answer> answers = Arrays.asList(
                        new Answer("puklerz"),
                        new Answer("talia"),
                        new Answer("lawina"),
                        new Answer("wzdrygnąć się"),
                        new Answer("zawzięty"),
                        new Answer("obowiązki"),
                        new Answer("zasłona"),
                        new Answer("nagana"),
                        new Answer("czujność"),
                        new Answer("ukrywanie"),
                        new Answer("uroczysty"),
                        new Answer("srogi"),
                        new Answer("wychudzony"),
                        new Answer("rozgrzeszony"),
                        new Answer("spustoszony"),
                        new Answer("parsknięcie"),
                        new Answer("skromny"),
                        new Answer("rozdawać"),
                        new Answer("stołek"),
                        new Answer("zmarszczyć brwi"),
                        new Answer("niepewny"),
                        new Answer("drażnić"),
                        new Answer("czołgać się"),
                        new Answer("warkocz"),
                        new Answer("zdetronizowany"),
                        new Answer("tlący"),
                        new Answer("niechętny"),
                        new Answer("kolebka"),
                        new Answer("tęgi"),
                        new Answer("kojąco"),
                        new Answer("splot"),
                        new Answer("szarpać"),
                        new Answer("słaby"),
                        new Answer("zrzędliwy"),
                        new Answer("umywalka"),
                        new Answer("mydło"),
                        new Answer("współczucie"),
                        new Answer("pranie"),
                        new Answer("pomarszczony"),
                        new Answer("skronie"),
                        new Answer("odosobnienie"),
                        new Answer("zatrzymany"),
                        new Answer("wół"),
                        new Answer("gromada"),
                        new Answer("zaganiać"),
                        new Answer("wazon"),
                        new Answer("płytka"),
                        new Answer("rześki"),
                        new Answer("szeroki uśmiech"),
                        new Answer("fartuch"),
                        new Answer("zawodzenie"),
                        new Answer("kompletny"),
                        new Answer("skarb"),
                        new Answer("ukłon"),
                        new Answer("brzytwa"),
                        new Answer("grzechotać"),
                        new Answer("chwiać się na nogach"),
                        new Answer("posłuszny"),
                        new Answer("prześcieradło"),
                        new Answer("sufit"),
                        new Answer("groza"),
                        new Answer("zniekształcenie"),
                        new Answer("kość słoniowa"),
                        new Answer("kotwica"),
                        new Answer("samotność"),
                        new Answer("krępy"),
                        new Answer("dźwignia"),
                        new Answer("gaj"),
                        new Answer("zwykły"),
                        new Answer("owsianka"),
                        new Answer("kanciasty"),
                        new Answer("bicz"),
                        new Answer("lejce"),
                        new Answer("berło"),
                        new Answer("prowokować kogoś"),
                        new Answer("pozłacany"),
                        new Answer("podły"),
                        new Answer("promy"),
                        new Answer("skóra"),
                        new Answer("przechwalający się"),
                        new Answer("koronka"),
                        new Answer("powóz"),
                        new Answer("powieka"),
                        new Answer("chrząknięcie"),
                        new Answer("gołąb"),
                        new Answer("pałka"),
                        new Answer("strzemię"),
                        new Answer("kopuła"),
                        new Answer("wywyższać"));
                db.answerDao().insert(answers);

                for (int i = 0; i < questions.size(); i++) {
                    questions.get(i).setAnswer(answers.get(i).getContent());
                }
                db.questionDao().insert(questions);
            }
        }).start();
    }
}