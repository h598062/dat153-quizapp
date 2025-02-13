package no.dat153.quizzler;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

import no.dat153.quizzler.view.GalleryActivity;
import no.dat153.quizzler.view.MainActivity;
import no.dat153.quizzler.view.QuizActivity;

public class QuizzlerApp extends Application {
    private static final String TAG = "QuizzlerApp";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        List<Integer> list = new ArrayList<>();
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_teal);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_lavender);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_yellow);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_pink);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_green);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_flamingo);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_mauve);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_peach);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_rosewater);
        list.add(com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_sky);

        Integer color = getRandomColor(list);
        MainActivity.setBgColor(color);
        list.remove(color);
        color = getRandomColor(list);
        GalleryActivity.setBgColor(color);
        list.remove(color);
        color = getRandomColor(list);
        QuizActivity.setBgColor(color);

    }

    private Integer getRandomColor(List<Integer> list) {
        return list.get((int) (Math.random() * list.size()));
    }
}
