package pl.kalisz.akademia.pup.adrian.quiz.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "questions")
public class Question implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String content;
    // defines how many correct answers were given in a row for this question; used for statistics
    private String answer;
    // defines if user learned answer of a question
    // (when user answered the question properly 3 times in a row)
    private boolean learned = false;
    private int numberOfCorrectAnswersInARow = 0;

    public Question(int id, String content) {
        this.id = id;
        this.content = content;
    }

    @Ignore
    public Question(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getNumberOfCorrectAnswersInARow() {
        return numberOfCorrectAnswersInARow;
    }

    public boolean isLearned() {
        return learned;
    }

    public String getAnswer() {
        return answer;
    }

    public void setNumberOfCorrectAnswersInARow(int numberOfCorrectAnswersInARow) {
        this.numberOfCorrectAnswersInARow = numberOfCorrectAnswersInARow;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void increaseNumberOfCorrectAnswersInARow() {
        numberOfCorrectAnswersInARow++;
    }
}
