package org.sopt.domain;

public enum PostCategory {
    QUESTION("질문"),
    REVIEW("후기"),
    DIARY("일기"),
    ETC("기타");

    private final String displayName;

    PostCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}