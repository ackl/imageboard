package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Posts {
    private String content;
    private String time;
    private long id;

    public Posts() {}

    public Posts(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime() {
        this.time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public void setId(long id) {
        this.id = id;
    }
}

