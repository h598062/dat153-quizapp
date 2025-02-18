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
import java.util.Random;
import java.util.Set;

import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.entity.QuestionItem;

public class QuizViewModel extends AndroidViewModel {

    private static final String TAG = QuizViewModel.class.getSimpleName();
    private QuestionRepo questionRepo;
    private LiveData<List<QuestionItem>> questions;
    private MutableLiveData<Integer> correctGuesses = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalGuesses = new MutableLiveData<>(0);
    private MutableLiveData<List<QuestionItem>> questionItems = new MutableLiveData<>();

    private MutableLiveData<QuestionItem> correctItem = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
        super(application);
        questionRepo = new QuestionRepo(application);
        questions = questionRepo.getAllQuestionItems();
    }

    public LiveData<List<QuestionItem>> getQuestions() {
        return questions;
    }

    public MutableLiveData<Integer> getCorrectGuesses() {
        return correctGuesses;
    }

    public MutableLiveData<Integer> getTotalGuesses() {
        return totalGuesses;
    }

    public MutableLiveData<List<QuestionItem>> getQuestionItems() {
        return questionItems;
    }

    public MutableLiveData<QuestionItem> getCorrectItem() {
        return correctItem;
    }

    public void knappeTrykk(QuestionItem item) {

        Log.d(TAG, "knappeTrykk:  " + item.getImageText() + " Correct Item: " + correctItem.getValue().getImageText());
        if (item.equals(correctItem.getValue())) {
            correctGuesses.setValue(correctGuesses.getValue() + 1);
            Log.d(TAG, "knappeTrykk: Correct Guesses: " + correctGuesses.getValue());
        }
        totalGuesses.setValue(totalGuesses.getValue() + 1);
        Log.d(TAG, "knappeTrykk: Total Guesses: " + totalGuesses.getValue());
    }

    public void settOppSvarAlternativer() {
        Log.d(TAG, "settOppSvarAlternativer: antall spørsmål: " + questions.getValue().size());
        if (questions.getValue().size() < 3) {
            Log.d(TAG, "settOppSvarAlternativer: Ikke nok spørsmål");
            return;
        }
        Random random = new Random();
        correctItem.setValue(questions.getValue().get(random.nextInt(questions.getValue().size())));
        Set<QuestionItem> svarAlternativer = new HashSet<>();
        svarAlternativer.add(correctItem.getValue());

        while (svarAlternativer.size() < 3) {
            int tilfeldig = random.nextInt(questions.getValue().size());
            svarAlternativer.add(questions.getValue().get(tilfeldig));
        }

        ArrayList<QuestionItem> svarListe = new ArrayList<>(svarAlternativer);
        Collections.shuffle(svarListe);

        questionItems.setValue(svarListe);
    }
}
