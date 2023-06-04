package pl.kalisz.akademia.pup.adrian.quiz.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import pl.kalisz.akademia.pup.adrian.quiz.entities.Question;
import pl.kalisz.akademia.pup.adrian.quiz.entities.relations.QuestionAndAnswer;

@Dao
public interface QuestionDao {
    @Insert
    void insert(Question... questions);

    @Insert
    void insert(List<Question> questions);

    @Update
    void update(Question question);

    @Delete
    void delete(Question... questions);

    @Query("delete from questions where id in (:ids)")
    void deleteById(int... ids);

    @Query("SELECT * FROM questions")
    List<Question> getAll();

    @Query("SELECT * FROM questions WHERE learned = 0")
    List<Question> getUnlearned();

    @Query("SELECT * FROM questions WHERE learned = 1")
    List<Question> getLearned();

    @Transaction
    @Query("SELECT * FROM questions")
    List<QuestionAndAnswer> getAllWithAnswers();

    @Query("UPDATE questions SET learned = 0, numberOfCorrectAnswersInARow = 0")
    void resetProgression();
}
