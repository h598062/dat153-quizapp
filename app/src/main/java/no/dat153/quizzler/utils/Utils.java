package no.dat153.quizzler.utils;

import android.content.Context;
import android.net.Uri;

import no.dat153.quizzler.R;
import no.dat153.quizzler.entity.QuestionItem;

public class Utils {

    public static QuestionItem[] getDefaultData(Context context) {
        return new QuestionItem[]{
                new QuestionItem("Cok", getResourceUri(R.drawable.cola, context)),
                new QuestionItem("Gud", getResourceUri(R.drawable.gud, context)),
                new QuestionItem("Giiisle", getResourceUri(R.drawable.gisle, context)),
                new QuestionItem("Chad", getResourceUri(R.drawable.guy, context)),
                new QuestionItem("Cry", getResourceUri(R.drawable.wah, context))
        };
    }

    public static Uri getResourceUri(int resId, Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
    }
}
