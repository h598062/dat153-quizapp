package no.dat153.quizzler.utils;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import no.dat153.quizzler.entity.QuestionItem;

/**
 * Singleton class to store questions
 */
public class QuestionHelper {
    private static QuestionHelper instance;
    private final List<QuestionItem> questions;

    private QuestionHelper() {
        this.questions = new ArrayList<>();
    }

    /**
     * Get the instance of the QuestionHelper
     *
     * @return the instance of the QuestionHelper
     */
    public static QuestionHelper getInstance() {
        if (instance == null) {
            instance = new QuestionHelper();
        }
        return instance;
    }

    /**
     * Get the internal list of questions
     * <p>
     * You should likely just use the instance methods, and not get the internal list
     *
     * @return the list of questions
     */
    public List<QuestionItem> getQuestions() {
        return questions;
    }

    /**
     * Add a question to the list of questions
     *
     * @param question the question to add
     */
    public void addQuestion(QuestionItem question) {
        questions.add(question);
    }

    /**
     * Creates and adds a new question to the list of questions
     *
     * @param text     the text of the question
     * @param imageUri the uri of the image of the question
     * @return the question that was added
     */
    public QuestionItem addNewQuestion(String text, Uri imageUri) {
        QuestionItem question = new QuestionItem(text, imageUri);
        questions.add(question);
        return question;
    }

    /**
     * Remove a question from the list of questions
     *
     * @param question the question to remove
     */
    public void removeQuestion(QuestionItem question) {
        questions.remove(question);
    }

    /**
     * Remove a question from the list of questions
     *
     * @param index the index of the question to remove
     */
    public void removeQuestionAtIndex(int index) {
        questions.remove(index);
    }

    /**
     * Sort the questions in ascending order
     */
    public void sortQuestionsDescending() {
        questions.sort(Comparator.comparing(QuestionItem::getText));
    }

    /**
     * Sort the questions in descending order
     */
    public void sortQuestionsAscending() {
        questions.sort(Comparator.comparing(QuestionItem::getText).reversed());
    }


}
