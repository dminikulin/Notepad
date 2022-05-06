package com.example.notepad.tools;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import androidx.annotation.ColorInt;

public class TextStyle {
    public static void style(EditText editText, int typeFace){
        SpannableString sp = new SpannableString(editText.getText());
        StyleSpan styleSpan = new StyleSpan(typeFace);
        sp.setSpan(
                styleSpan,
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }
    public static void clearAll(EditText editText){
        SpannableString sp = new SpannableString(editText.getText());
        Object[] spans = sp.getSpans(0, sp.length(), Object.class);
        for(Object s: spans) sp.removeSpan(s);
        editText.setText(sp);
    }

    public static void styleClear(EditText editText){
        SpannableString sp = new SpannableString(editText.getText());
        StyleSpan[] spans = sp.getSpans(
                editText.getSelectionStart(),
                editText.getSelectionEnd(),
                StyleSpan.class
        );
        for(Object s: spans) sp.removeSpan(s);
        editText.setText(sp);
    }

    public static void setColor(EditText editText, @ColorInt int color){
        SpannableString sp = new SpannableString(editText.getText());
        ForegroundColorSpan styleSpan = new ForegroundColorSpan(color);
        sp.setSpan(
                styleSpan,
                editText.getSelectionStart(),
                editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }
}
