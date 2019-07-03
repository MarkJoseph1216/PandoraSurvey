package com.example.privateex.pandorasurvey;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextWatcherExtended implements TextWatcher {

    private int lastLength;

    public abstract void afterTextChanged(Editable s, boolean backSpace);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        lastLength = s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextChanged(s, lastLength > s.length());
    }
}