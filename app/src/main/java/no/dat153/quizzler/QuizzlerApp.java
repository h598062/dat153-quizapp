package no.dat153.quizzler;

import android.app.Application;
import android.net.Uri;

import androidx.appcompat.app.AppCompatDelegate;

import com.shifthackz.catppuccin.palette.legacy.R.color;

import java.util.ArrayList;
import java.util.List;

import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.utils.QuestionHelper;
import no.dat153.quizzler.view.GalleryActivity;
import no.dat153.quizzler.view.MainActivity;
import no.dat153.quizzler.view.QuizActivity;

public class QuizzlerApp extends Application {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        List<Integer> list = new ArrayList<>();
        list.add(color.catppuccin_mocha_teal);
        list.add(color.catppuccin_mocha_lavender);
        list.add(color.catppuccin_mocha_yellow);
        list.add(color.catppuccin_mocha_pink);
        list.add(color.catppuccin_mocha_green);
        list.add(color.catppuccin_mocha_flamingo);
        list.add(color.catppuccin_mocha_mauve);
        list.add(color.catppuccin_mocha_peach);
        list.add(color.catppuccin_mocha_rosewater);
        list.add(color.catppuccin_mocha_sky);

        Integer color = getRandomColor(list);
        MainActivity.setBgColor(color);
        list.remove(color);
        color = getRandomColor(list);
        GalleryActivity.setBgColor(color);
        list.remove(color);
        color = getRandomColor(list);
        QuizActivity.setBgColor(color);

        addDefaultQuestions();
    }

    private Integer getRandomColor(List<Integer> list) {
        return list.get((int) (Math.random() * list.size()));
    }

    private void addDefaultQuestions() {
        QuestionHelper qh = QuestionHelper.getInstance();
        qh.addQuestion(new QuestionItem("Cok", getResourceUri(R.drawable.cola)));
        qh.addQuestion(new QuestionItem("Gud", getResourceUri(R.drawable.gud)));
        qh.addQuestion(new QuestionItem("Giiisle", getResourceUri(R.drawable.gisle)));
        qh.addQuestion(new QuestionItem("Chad", getResourceUri(R.drawable.guy)));
        qh.addQuestion(new QuestionItem("Cry", getResourceUri(R.drawable.wah)));
    }

    private Uri getResourceUri(int resId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + resId);
    }
}
