package pl.kalisz.akademia.pup.adrian.quiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.kalisz.akademia.pup.adrian.quiz.R;
import pl.kalisz.akademia.pup.adrian.quiz.db.QuizDatabase;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Answer;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

public class QuizActivity extends AppCompatActivity {
    QuizDatabase db;
    private Question question = null;
    private Answer answer;
    // correct answer will be gotten from question, which stores it's content, not object - that's
    // why it's stored as String
    private String correctAnswer;
    private List<Question> questions;
    private List<Answer> answers;
    private List<Answer> answersInitialCopy;
    private TextView textViewQuestion;
    private Button correctAnswerButton;
    private int points;
    // define how many questions will the quiz consist of
    int n;
    // define how much delay will appear after answering question
    private long delay;
    // define when last answer button has been clicked
    private long lastClickTime;
    private List<Button> answerButtons;
    // every thread assigned to this variable must execute before starting new one
    private Thread thread;
    // stores questions learned on quiz run
    private ArrayList<Question> questionsLearned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db = QuizDatabase.getInstance(this);
        fetchDataFromDb(false);

        // store initial answers list, so it can be used past first question
        answersInitialCopy = new ArrayList<>(answers);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        questionsLearned = new ArrayList<>();
        points = 0;
        delay = 3000L;
        n = 5;
        if (questions.size() < n) {
            n = questions.size();
        }
        runQuestion(n);
    }

    // fetch all answers and unlearned questions from the database and store them in lists
    private void fetchDataFromDb(boolean onlyQuestions) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!onlyQuestions) {
                    answers = db.answerDao().getAll();
                }
                questions = db.questionDao().getUnlearned();
//                for (Question question : questions) {
//                    Log.d("Unlearned question = ", "" + question.getContent());
//                }
            }
        });
        thread.start();

        // wait for thread to finish, so questions and answers can be used
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void runQuestion(int n) {
        // if it's past first question, retrieve initial answers lists as 3 of them
        // (number of incorrect answers per question) are being removed from the list every question
        if (n < 5) {
            answers = new ArrayList<>(answersInitialCopy);
        }

        // if any of the questions were learned during this session, fetch them from db again
        // so learned ones will be skipped
        for (Question question : questions) {
            if (question.isLearned()) {
                fetchDataFromDb(true);
            }
        }

        answerButtons = new ArrayList<>();
        answerButtons.add(findViewById(R.id.buttonAnswer1));
        answerButtons.add(findViewById(R.id.buttonAnswer2));
        answerButtons.add(findViewById(R.id.buttonAnswer3));
        answerButtons.add(findViewById(R.id.buttonAnswer4));
        restoreDefaultButtonColors();
        setButtonsOnClickListeners();
        setRandomQuestion();
        setAnswers(answerButtons);
        this.n--;
    }

    private void setRandomQuestion() {
        Random r = new Random();
        int index;
        index = r.nextInt(questions.size());
        question = questions.remove(index);
        correctAnswer = question.getAnswer();
//        Log.d("CORRECT ANSWER", " " + correctAnswer);
        for (Answer answer1 : answers) {
            if (answer1.getContent().equals(correctAnswer)) {
                answers.remove(answer1);
//                Log.d("REMOVING ANSWER", " " + answer1.getContent());
//                for (Answer answer2 : answers) {
//                    Log.d("DISPLAYING ANSWERS AFTER REMOVING", " " + answer2.getContent());
//                }
                break;
            }
        }

        textViewQuestion.setText(question.getContent());
    }

    // set all remaining answers; getting all answers after setting a random question first, ensures
    // that there is no correct answer as the question containing it has been removed from the list,
    // so there is no need to filter out the correct answer
    private void setAnswers(List<Button> buttons) {
        buttons = setCorrectAnswer(buttons);
        for (Button button : buttons) {
            setRandomAnswer(button);
        }
    }

    // set correct answer in random button and return list of buttons remaining without answer set
    private List<Button> setCorrectAnswer(List<Button> buttons) {
        Random r = new Random();
        correctAnswerButton = buttons.remove(r.nextInt(buttons.size()));
        correctAnswerButton.setText(correctAnswer);
        return buttons;
    }

    private void setRandomAnswer(Button button) {
        Random r = new Random();
        int index = r.nextInt(answers.size());
        answer = answers.remove(index);
        button.setText(answer.getContent());
    }

    // prevent user from clicking answer buttons more than once per question
    private void setButtonsOnClickListeners() {
        for(Button button : answerButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - lastClickTime < delay){
                        return;
                    }
                    lastClickTime = SystemClock.elapsedRealtime();

                    try {
                        checkAnswer(view);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    // check if the answer is correct or not and show it by changing background colors
    // (if the answer is incorrect, then show the correct one);
    // if the answer is correct, then add points
    private void checkAnswer(View view) throws InterruptedException {
        Button button = (Button) view;
        answer.setContent(button.getText().toString());

        if (answer.getContent().equals(correctAnswer)) {
            button.setBackgroundColor(getResources().getColor(R.color.success));
            points++;
            question.increaseNumberOfCorrectAnswersInARow();
            if (question.getNumberOfCorrectAnswersInARow() == 3) {
                question.setLearned(true);
                questionsLearned.add(question);
            }
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    db.questionDao().update(question);
                }
            });
            thread.start();
            thread.join();
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.failure));
            correctAnswerButton.setBackgroundColor(getResources().getColor(R.color.success));
            if (question.getNumberOfCorrectAnswersInARow() > 0) {
                question.setNumberOfCorrectAnswersInARow(0);
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.questionDao().update(question);
                    }
                });
                thread.start();
                thread.join();
            }
        }
        
//        Log.d("", "correct answers = " + question.getNumberOfCorrectAnswersInARow());

        // run quiz n times, then run summary
        new Handler().postDelayed(new Runnable() {
            public void run () {
                if (n > 0) {
                    runQuestion(n);
                } else {
                    Intent intent = new Intent(QuizActivity.this,
                            SummaryActivity.class);
                    intent.putExtra("points", points);
                    intent.putExtra("questionsLearned", questionsLearned);
                    startActivity(intent);
                }
            }
        }, delay);
    }

    private void restoreDefaultButtonColors() {
        for (Button button: answerButtons) {
            button.setBackgroundColor(getResources().getColor(R.color.orange));
        }
    }
}