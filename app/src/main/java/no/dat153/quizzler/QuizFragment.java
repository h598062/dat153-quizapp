package no.dat153.quizzler;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.shifthackz.catppuccin.palette.legacy.R.color;

import java.util.HashSet;
import java.util.Set;

import no.dat153.quizzler.databinding.FragmentQuizBinding;
import no.dat153.quizzler.entity.QuestionItem;
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
    public static QuizFragment newInstance(String param1, String param2) {
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
        quizViewModel = new QuizViewModel(requireActivity().getApplication());
    }

    private void settOppObservers() {
        quizViewModel.getQuestions().observe(getViewLifecycleOwner(), questionItems -> {
            if (questionItems != null) {
                quizViewModel.settOppSvarAlternativer();
            }
        });
        quizViewModel.getCorrectItem().observe(getViewLifecycleOwner(), questionItem -> {
            if (questionItem != null) {
                binding.imageView.setImageURI(questionItem.getImageUri());
            }
        });
        quizViewModel.getQuestionItems().observe(getViewLifecycleOwner(), questionItems -> {
            if (questionItems != null) {
                binding.btnAnswerA.setText(questionItems.get(0).getImageText());
                binding.btnAnswerB.setText(questionItems.get(1).getImageText());
                binding.btnAnswerC.setText(questionItems.get(2).getImageText());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAnswerA.setOnClickListener(v -> buttonListener(quizViewModel.getQuestionItems().getValue().get(0)));
        binding.btnAnswerB.setOnClickListener(v -> buttonListener(quizViewModel.getQuestionItems().getValue().get(1)));
        binding.btnAnswerC.setOnClickListener(v -> buttonListener(quizViewModel.getQuestionItems().getValue().get(2)));

        settOppObservers();

        return view;
    }

    private void buttonListener(QuestionItem selectedAnswer) {
        QuestionItem q1 = quizViewModel.getQuestionItems().getValue().get(0);
        QuestionItem q2 = quizViewModel.getQuestionItems().getValue().get(1);
        QuestionItem q3 = quizViewModel.getQuestionItems().getValue().get(2);
        QuestionItem correctItem = quizViewModel.getCorrectItem().getValue();
        Button correctButton;
        Button incorrectButton1;
        Button incorrectButton2;
        if (q1.equals(correctItem)) {
            correctButton = binding.btnAnswerA;
            incorrectButton1 = binding.btnAnswerB;
            incorrectButton2 = binding.btnAnswerC;
        } else if (q2.equals(correctItem)) {
            correctButton = binding.btnAnswerB;
            incorrectButton1 = binding.btnAnswerA;
            incorrectButton2 = binding.btnAnswerC;
        } else if (q3.equals(correctItem)) {
            correctButton = binding.btnAnswerC;
            incorrectButton1 = binding.btnAnswerA;
            incorrectButton2 = binding.btnAnswerB;
        } else {
            Log.d(TAG, "onCreateView: No correct item found");
            return;
        }
        correctButton.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_green));
        incorrectButton1.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
        incorrectButton2.setBackgroundColor(ContextCompat.getColor(requireContext(), color.catppuccin_mocha_red));
        quizViewModel.knappeTrykk(selectedAnswer);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(this::finish, 1000);
    }

    private void finish() {
        Log.d(TAG, "finish: finished");
        getParentFragmentManager().setFragmentResult(RESULT, new Bundle());
    }
}