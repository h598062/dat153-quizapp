package no.dat153.quizzler;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shifthackz.catppuccin.palette.legacy.R.color;

import java.util.HashSet;
import java.util.Set;

import no.dat153.quizzler.databinding.FragmentQuizBinding;
import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.view.MainActivity;
import no.dat153.quizzler.viewmodel.QuizViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    private static final String TAG = QuizFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String RESULT = "quizResult";

    public static final String BUTTON_A = "A";
    public static final String BUTTON_B = "B";
    public static final String BUTTON_C = "C";

    private FragmentQuizBinding binding;
    private QuizViewModel quizViewModel;
    private Set<QuestionItem> usedQuestionItems = new HashSet<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(QuizViewModel quizViewModel, String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void settOppObservers() {
        quizViewModel.getCorrectOption().observe(getViewLifecycleOwner(), questionItem -> {
            if (questionItem != null) {
                binding.imageView.setImageURI(questionItem.getImageUri());
            }
        });
        quizViewModel.getCurrentQuestionOptions().observe(getViewLifecycleOwner(), questionItems -> {
            if (questionItems != null) {
                binding.btnAnswerA.setText(questionItems.get(BUTTON_A).getImageText());
                binding.btnAnswerB.setText(questionItems.get(BUTTON_B).getImageText());
                binding.btnAnswerC.setText(questionItems.get(BUTTON_C).getImageText());
            }
        });
        quizViewModel.getTotalGuesses().observe(getViewLifecycleOwner(), totalGuesses -> {
            if (totalGuesses != null) {
                binding.textView.setText(String.format("%d / %d", quizViewModel.getCorrectGuesses().getValue(), totalGuesses));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.btnAnswerA.setBackgroundColor(ContextCompat.getColor(requireContext(), MainActivity.getBgColor()));
        binding.btnAnswerB.setBackgroundColor(ContextCompat.getColor(requireContext(), MainActivity.getBgColor()));
        binding.btnAnswerC.setBackgroundColor(ContextCompat.getColor(requireContext(), MainActivity.getBgColor()));

        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        binding.btnAnswerA.setOnClickListener(v -> buttonListener(BUTTON_A));
        binding.btnAnswerB.setOnClickListener(v -> buttonListener(BUTTON_B));
        binding.btnAnswerC.setOnClickListener(v -> buttonListener(BUTTON_C));

        settOppObservers();

        binding.textView.setText(String.format("%d / %d", quizViewModel.getCorrectGuesses().getValue(), quizViewModel.getTotalGuesses().getValue()));

        return view;
    }

    private void buttonListener(String selectedAnswer) {
        String correctButton = quizViewModel.sjekkSvar(selectedAnswer);

        switch (correctButton) {
            case BUTTON_A:
                binding.btnAnswerA.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_green));
                binding.btnAnswerB.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                binding.btnAnswerC.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                break;
            case BUTTON_B:
                binding.btnAnswerA.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                binding.btnAnswerB.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_green));
                binding.btnAnswerC.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                break;
            case BUTTON_C:
                binding.btnAnswerA.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                binding.btnAnswerB.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
                binding.btnAnswerC.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_green));
                break;
        }
        new android.os.Handler(Looper.getMainLooper()).postDelayed(this::finish, 1000);
    }

    private void finish() {
        Log.d(TAG, "finish: finished");
        quizViewModel.settOppSvarAlternativer();
        getParentFragmentManager().setFragmentResult(RESULT, new Bundle());
    }
}