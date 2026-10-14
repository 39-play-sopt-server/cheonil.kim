package org.sopt.domain;

import org.sopt.code.PostErrorCode;
import org.sopt.exception.InvalidPostException;

import java.util.Arrays;

public enum PostCategory {
    QUESTION("질문"),
    REVIEW("후기"),
    DIARY("일기"),
    ETC("기타");

    private final String displayName;

    PostCategory(String displayName) {
        this.displayName = displayName;
    }

    public static PostCategory from(String value){
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidPostException(PostErrorCode.INVALID_CATEGORY));
    }

    public String getDisplayName(){
        return displayName;
    }
}