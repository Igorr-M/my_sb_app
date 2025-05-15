package com.example.my_sb_app.entity.util;

import com.example.my_sb_app.entity.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return (author != null) ? author.getUsername() : "<none>";
    }
}
