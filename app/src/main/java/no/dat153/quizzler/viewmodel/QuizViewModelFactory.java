package no.dat153.quizzler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import no.dat153.quizzler.data.QuestionRepo;

public class QuizViewModelFactory implements ViewModelProvider.Factory {
    private final Application app;
    private final QuestionRepo repo;

    public QuizViewModelFactory(@NonNull Application app, @NonNull QuestionRepo repo) {
        this.app = app;
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuizViewModel(app, repo);
    }
}
