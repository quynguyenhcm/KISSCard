package net.qrolling.kisscard.utils;

import android.content.Context;
import android.widget.EditText;

import net.qrolling.kisscard.R;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 23/05/18.
 */
public class Validator {
    Context ctx;

    public Validator() {

    }

    public Validator(Context ctx) {
        this.ctx = ctx;
    }

    public boolean isNotNull(EditText editText, String fieldName) {
        if (isEmpty(editText)) {
            setError(editText, String.format(ctx.getString(R.string.is_required), fieldName));
            return false;
        }

        clearError(editText);
        return true;
    }

    public boolean isEmpty(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }

    public void setError(EditText editText, String errorString) {
        editText.setError(errorString);
    }

    public void clearError(EditText editText) {
        editText.setError(null);
    }
}
