package no.dat153.quizzler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.dat153.quizzler.QuizFragment;
import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.entity.QuestionItem;

public class QuizViewModel extends ViewModel {
    private QuestionRepo questionRepo;
    private LiveData<List<QuestionItem>> questions;

    public QuizViewModel(@NonNull Application application) {
        questionRepo = new QuestionRepo(application);
        questions = questionRepo.getAllQuestionItems();
    }

    public LiveData<List<QuestionItem>> getQuestions() {
        return questions;
    }
}
