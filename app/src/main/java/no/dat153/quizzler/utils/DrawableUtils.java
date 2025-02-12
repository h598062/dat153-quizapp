package no.dat153.quizzler.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

public class DrawableUtils {

    /**
     * Set background color of a view with a drawable and a color
     *
     * @param context       The context of the view, usually the activity (this)
     * @param view          The view to set the background color of, usually a layout
     * @param drawableResId The resource id of the drawable to use
     * @param colorResId    The resource id of the color to use
     */
    public static void setBackgroundColor(Context context, View view, @DrawableRes int drawableResId, @ColorRes int colorResId) {
        GradientDrawable bg = (GradientDrawable) ResourcesCompat.getDrawable(context.getResources(), drawableResId, context.getTheme());
        if (bg != null) {
            bg.setColor(context.getResources().getColor(colorResId, context.getTheme()));
            view.setBackground(bg);
        }
    }
}
