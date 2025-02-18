package no.dat153.quizzler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.entity.QuestionItem;

public class QuizViewModel extends ViewModel {
    private QuestionRepo questionRepo;
    private LiveData<List<QuestionItem>> questions;
    private MutableLiveData<Integer> correctGuesses = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalGuesses = new MutableLiveData<>(0);
    private MutableLiveData<List<QuestionItem>> questionItems = new MutableLiveData<>();

    private MutableLiveData<QuestionItem> correctItem = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
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

    public void setCorrectGuesses(MutableLiveData<Integer> correctGuesses) {
        this.correctGuesses = correctGuesses;
    }

    public void setTotalGuesses(MutableLiveData<Integer> totalGuesses) {
        this.totalGuesses = totalGuesses;
    }

    public MutableLiveData<List<QuestionItem>> getQuestionItems() {
        return questionItems;
    }

    public MutableLiveData<QuestionItem> getCorrectItem() {
        return correctItem;
    }

    public boolean knappeTrykk(QuestionItem item) {
        totalGuesses = getTotalGuesses();
        totalGuesses.setValue(totalGuesses.getValue() + 1);

        if (item.equals(correctItem)) {
            correctGuesses = getCorrectGuesses();
            correctGuesses.setValue(correctGuesses.getValue() + 1);
            return true;
        } else {
            return false;
        }
    }

    public void settOppSvarAlternativer() {
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
