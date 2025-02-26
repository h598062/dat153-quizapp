package no.dat153.quizzler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import no.dat153.quizzler.data.QuestionRepo;
import no.dat153.quizzler.data.QuestionRepoInterface;
import no.dat153.quizzler.entity.QuestionItem;

public class GalleryViewModel extends AndroidViewModel {
    public static final int NO_SORT = 0;
    public static final int SORT_AtoZ = 1;
    public static final int SORT_ZtoA = 2;
    private QuestionRepoInterface questionRepo;
    private LiveData<List<QuestionItem>> allQuestions;
    private LiveData<List<QuestionItem>> sortedQuestions;
    private MutableLiveData<Integer> sortDirection = new MutableLiveData<>(NO_SORT);

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        questionRepo = QuestionRepo.getInstance(application);
        allQuestions = questionRepo.getAllQuestionItems();
        sortedQuestions = Transformations.switchMap(sortDirection, sortOrder -> {
            return Transformations.map(allQuestions, questions -> {
                if (questions == null) {
                    return Collections.emptyList(); // Handle null case
                }
                List<QuestionItem> sortedList = new ArrayList<>(questions);
                switch (sortOrder) {
                    case SORT_AtoZ:
                        sortedList.sort(Comparator.comparing(QuestionItem::getImageText)); // A-Z sort
                        break;
                    case SORT_ZtoA:
                        sortedList.sort(Comparator.comparing(QuestionItem::getImageText).reversed()); // Z-A sort
                        break;
                    case NO_SORT:
                    default:
                        // No sorting, return as is
                        break;
                }
                return sortedList;
            });
        });
    }

    public LiveData<List<QuestionItem>> getQuestions() {
        return sortedQuestions;
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

    public MutableLiveData<Integer> getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Integer sortDirection) {
        this.sortDirection.setValue(sortDirection);
    }
}
