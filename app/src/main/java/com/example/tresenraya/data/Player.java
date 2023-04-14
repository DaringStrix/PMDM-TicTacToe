package com.example.tresenraya.data;

import androidx.annotation.NonNull;

public enum Player {
    X("X") ,
    O("O") ,
    DRAW("no one"),
    NONE("");

    private String displayName;
    Player(String displayName) {
        this.displayName = displayName;
    }

    @NonNull
    @Override
    public String toString() {
        return  displayName;
    }
}
