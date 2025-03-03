package no.dat153.quizzler;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.shifthackz.catppuccin.palette.legacy.R.color;

import java.util.ArrayList;
import java.util.List;

import no.dat153.quizzler.data.QuestionDatabase;
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
        list.add(color.catppuccin_mocha_sapphire);
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

        QuestionDatabase instance = QuestionDatabase.getInstance(this);
        instance.questionItemDAO().getAllQuestionItems();

    }

    private Integer getRandomColor(List<Integer> list) {
        return list.get((int) (Math.random() * list.size()));
    }


}
