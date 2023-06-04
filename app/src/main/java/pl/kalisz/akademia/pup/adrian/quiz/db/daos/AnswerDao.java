package pl.kalisz.akademia.pup.adrian.quiz.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.kalisz.akademia.pup.adrian.quiz.entities.Answer;

@Dao
public interface AnswerDao {
    @Insert
    void insert(Answer... answers);

    @Insert
    void insert(List<Answer> answers);

    @Delete
    void delete(Answer answer);

    @Query("delete from answers where id in (:ids)")
    void deleteById(int... ids);

    @Query("SELECT * FROM answers")
    List<Answer> getAll();
}
