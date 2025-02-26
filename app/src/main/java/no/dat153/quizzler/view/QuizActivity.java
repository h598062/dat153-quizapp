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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import no.dat153.quizzler.QuizFragment;
import no.dat153.quizzler.R;
import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.databinding.ActivityQuizBinding;
import no.dat153.quizzler.viewmodel.QuizViewModel;
import no.dat153.quizzler.viewmodel.QuizViewModelFactory;

public class QuizActivity extends AppCompatActivity {

    private static @ColorRes Integer bgColor;
    private final String TAG = this.getClass().getSimpleName();
    private ActivityQuizBinding binding;

    private QuizViewModel viewModel;

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

        QuestionRepo repo = QuestionRepo.getInstance(getApplication());
        ViewModelProvider.Factory factory = new QuizViewModelFactory(getApplication(), repo);
        viewModel = new ViewModelProvider(this, factory).get(QuizViewModel.class);

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));
        binding.fragmentQuiz.setBackgroundTintList(getColorStateList(bgColor));


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        loadFragment();


        getSupportFragmentManager().setFragmentResultListener(QuizFragment.RESULT, this, (requestKey, bundle) -> {
            loadFragment();
        });

    }

    private void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        QuizFragment fragment = new QuizFragment();
        t.replace(binding.fragmentQuiz.getId(), fragment);
        t.commit();
    }

    public QuizViewModel getViewModel() {
        return viewModel;
    }
}
