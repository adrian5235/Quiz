package pl.kalisz.akademia.pup.adrian.quiz.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;

    public Answer(int id, String content) {
        this.id = id;
        this.content = content;
    }

    @Ignore
    public Answer(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
