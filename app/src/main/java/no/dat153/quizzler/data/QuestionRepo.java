package no.dat153.quizzler.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import no.dat153.quizzler.entity.QuestionItem;

public class QuestionRepo {
    private QuestionItemDAO questionItemDao;
    private LiveData<List<QuestionItem>> allQuestionItems;
    private ExecutorService executorService;

    public QuestionRepo(Application application) {
        QuestionDatabase db = QuestionDatabase.getInstance(application);
        questionItemDao = db.questionItemDAO();
        allQuestionItems = questionItemDao.getAllQuestionItems();
        executorService = Executors.newSingleThreadExecutor(); // Runs async operations
    }

    public LiveData<List<QuestionItem>> getAllQuestionItems() {
        return allQuestionItems;
    }

    public void insert(QuestionItem item) {
        executorService.execute(() -> questionItemDao.insert(item));
    }

    public void update(QuestionItem item) {
        executorService.execute(() -> questionItemDao.update(item));
    }

    public void delete(QuestionItem item) {
        executorService.execute(() -> questionItemDao.delete(item));
    }

    public void deleteAll() {
        executorService.execute(() -> questionItemDao.deleteAll());
    }
}
