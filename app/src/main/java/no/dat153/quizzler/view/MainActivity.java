package no.dat153.quizzler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import no.dat153.quizzler.R;
import no.dat153.quizzler.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static @ColorRes Integer bgColor;
    private final String TAG = this.getClass().getSimpleName();
    private ActivityMainBinding binding;

    public static @ColorRes Integer getBgColor() {
        return bgColor;
    }

    public static void setBgColor(@ColorRes Integer bgColor) {
        MainActivity.bgColor = bgColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));
        binding.btnGalleri.setBackgroundTintList(getColorStateList(GalleryActivity.getBgColor()));
        binding.btnStartQuiz.setBackgroundTintList(getColorStateList(QuizActivity.getBgColor()));

        binding.btnGalleri.setOnClickListener(v -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}