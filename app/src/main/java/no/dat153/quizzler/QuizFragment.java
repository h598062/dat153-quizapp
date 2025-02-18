package no.dat153.quizzler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Random;

import no.dat153.quizzler.databinding.FragmentQuizBinding;
import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.viewmodel.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentQuizBinding binding;
    private QuizViewModel quizViewModel;

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
    }

    private void knappeTrykk() {
        Toast.makeText(getContext(), "Klikket på knapp", Toast.LENGTH_SHORT).show();
    }

    private void oppdaterQuiz() {
        // Oppdaterer quiz
        quizViewModel = new QuizViewModel(requireActivity().getApplication());
        quizViewModel.getQuestions().observe(getViewLifecycleOwner(), questions -> {
            if (questions != null && !questions.isEmpty()) {
                // Select a random image from the questions
                Random random = new Random();
                int randomIndex = random.nextInt(questions.size());
                QuestionItem randomItem = questions.get(randomIndex);
                binding.imageView.setImageURI(randomItem.getImageUri());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAnswerA.setOnClickListener(v -> knappeTrykk());
        binding.btnAnswerB.setOnClickListener(v -> knappeTrykk());
        binding.btnAnswerC.setOnClickListener(v -> knappeTrykk());

        oppdaterQuiz();

        return view;
    }
}