package com.Spring.SpringLetsCode.domain.DTO;

import com.Spring.SpringLetsCode.Model.Message;
import com.Spring.SpringLetsCode.Model.User;
import com.Spring.SpringLetsCode.domain.util.MessageHelper;

public class MessageDTO {
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private boolean meLike;

    public MessageDTO(Message message, Long likes, boolean meLike) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLike = meLike;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLike=" + meLike +
                '}';
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public boolean isMeLike() {
        return meLike;
    }
}
