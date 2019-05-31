package com.example.acer.capstonestagetwo;

import android.text.InputFilter;
import android.text.Spanned;

public class InputRange implements InputFilter {
    private int min, max;

    public InputRange(int min, int max) {
        this.min = min;
        this.max = max;

    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        try {
            int input = Integer.parseInt(spanned.toString() + charSequence.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}