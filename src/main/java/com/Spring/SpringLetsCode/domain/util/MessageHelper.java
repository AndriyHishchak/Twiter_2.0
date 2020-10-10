package com.Spring.SpringLetsCode.domain.util;

import com.Spring.SpringLetsCode.Model.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
       return author !=null ? author.getUsername() : "<none>";
    }
}
