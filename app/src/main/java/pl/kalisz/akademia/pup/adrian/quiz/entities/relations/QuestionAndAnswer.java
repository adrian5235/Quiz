package pl.kalisz.akademia.pup.adrian.quiz.entities.relations;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import pl.kalisz.akademia.pup.adrian.quiz.entities.Answer;
import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;

@Entity(tableName = "question_answer")
public class QuestionAndAnswer {
    @Embedded
    private Answer answer;
    @Relation(
            parentColumn = "id",
            entityColumn = "answer"
    )
    private Question question;

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
