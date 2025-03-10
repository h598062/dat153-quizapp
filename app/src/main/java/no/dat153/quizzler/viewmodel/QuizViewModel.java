package no.dat153.quizzler.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.entity.QuestionItem;

public class QuizViewModel extends AndroidViewModel {

    private static final String TAG = QuizViewModel.class.getSimpleName();
    private QuestionRepo questionRepo;
    private LiveData<List<QuestionItem>> allQuestions;
    private MutableLiveData<Integer> correctGuesses = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalGuesses = new MutableLiveData<>(0);
    private MutableLiveData<Map<String, QuestionItem>> currentQuestionOptions = new MutableLiveData<>();

    private MutableLiveData<QuestionItem> correctOption = new MutableLiveData<>();

    private String correctButton = "";

    public QuizViewModel(@NonNull Application application) {
        super(application);
        questionRepo = new QuestionRepo(application);
        allQuestions = questionRepo.getAllQuestionItems();
        var observer = new androidx.lifecycle.Observer<List<QuestionItem>>() {
            @Override
            public void onChanged(List<QuestionItem> questionItems) {
                Log.d(TAG, "QuizViewModel: Observer called");
                if(settOppSvarAlternativer()) {
                    allQuestions.removeObserver(this);
                }
            }
        };
        allQuestions.observeForever(observer);
    }

    public String sjekkSvar(String buttonID) {

        QuestionItem selectedAnswer = currentQuestionOptions.getValue().get(buttonID);
        if (selectedAnswer.equals(correctOption.getValue())) {
            correctGuesses.setValue(correctGuesses.getValue() + 1);
            Log.d(TAG, "buttonOnClick: Correct Guesses: " + correctGuesses.getValue());
        }
        totalGuesses.setValue(totalGuesses.getValue() + 1);
        Log.d(TAG, "knappeTrykk: Total Guesses: " + totalGuesses.getValue());
        return correctButton;
    }

    public LiveData<List<QuestionItem>> getAllQuestions() {
        return allQuestions;
    }

    public MutableLiveData<Integer> getCorrectGuesses() {
        return correctGuesses;
    }

    public MutableLiveData<Integer> getTotalGuesses() {
        return totalGuesses;
    }

    public MutableLiveData<Map<String, QuestionItem>> getCurrentQuestionOptions() {
        return currentQuestionOptions;
    }

    public MutableLiveData<QuestionItem> getCorrectOption() {
        return correctOption;
    }

    public boolean settOppSvarAlternativer() {
        Log.d(TAG, "settOppSvarAlternativer: antall spørsmål: " + allQuestions.getValue().size());
        if (allQuestions.getValue().size() < 3) {
            Log.d(TAG, "settOppSvarAlternativer: Ikke nok spørsmål");
            return false;
        }
        Random random = new Random();
        QuestionItem correct = allQuestions.getValue().get(random.nextInt(allQuestions.getValue().size()));
        correctOption.setValue(correct);
        Set<QuestionItem> svarAlternativer = new HashSet<>();
        svarAlternativer.add(correct);

        while (svarAlternativer.size() < 3) {
            int tilfeldig = random.nextInt(allQuestions.getValue().size());
            svarAlternativer.add(allQuestions.getValue().get(tilfeldig));
        }

        ArrayList<QuestionItem> svarListe = new ArrayList<>(svarAlternativer);
        Collections.shuffle(svarListe);

        Map<String, QuestionItem> svar = Map.of(
                "A", svarListe.get(0),
                "B", svarListe.get(1),
                "C", svarListe.get(2)
        );

        svar.forEach((k, v) -> {
            if (v.equals(correct)) {
                correctButton = k;
            }
        });

        currentQuestionOptions.setValue(svar);
        return true;
    }
}
