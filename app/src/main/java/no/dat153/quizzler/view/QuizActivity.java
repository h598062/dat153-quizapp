package no.dat153.quizzler.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import no.dat153.quizzler.R;
import no.dat153.quizzler.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static @ColorRes Integer bgColor;
    private ActivityQuizBinding binding;

    public static @ColorRes Integer getBgColor() {
        return bgColor;
    }

    public static void setBgColor(@ColorRes Integer bgColor) {
        QuizActivity.bgColor = bgColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


    }

}