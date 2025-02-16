package no.dat153.quizzler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.entity.QuestionItem;

public class GalleryViewModel extends AndroidViewModel {
    private QuestionRepo questionRepo;
    private LiveData<List<QuestionItem>> questions;

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        questionRepo = new QuestionRepo(application);
        questions = questionRepo.getAllQuestionItems();
    }

    public LiveData<List<QuestionItem>> getQuestions() {
        return questions;
    }

    public void add(QuestionItem item) {
        questionRepo.insert(item);
    }

    public void update(QuestionItem item) {
        questionRepo.update(item);
    }

    public void delete(QuestionItem item) {
        questionRepo.delete(item);
    }

    public void deleteAll() {
        questionRepo.deleteAll();
    }
}
