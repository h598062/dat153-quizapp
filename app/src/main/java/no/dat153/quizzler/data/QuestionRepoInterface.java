package no.dat153.quizzler.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import no.dat153.quizzler.entity.QuestionItem;

public interface QuestionRepoInterface {
    LiveData<List<QuestionItem>> getAllQuestionItems();

    void insert(QuestionItem item);

    void update(QuestionItem item);

    void delete(QuestionItem item);

    void deleteAll();
}
