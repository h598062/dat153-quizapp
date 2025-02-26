package no.dat153.quizzler;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.view.QuizActivity;
import no.dat153.quizzler.viewmodel.QuizViewModel;

@RunWith(AndroidJUnit4.class)
public class QuizLiveDataTest {

    private static final String TAG = QuizLiveDataTest.class.getSimpleName();

    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule =
            new ActivityScenarioRule<>(QuizActivity.class);

    private QuizViewModel viewModel;
    private LiveDataIdlingResource<QuestionItem> correctAnswerLiveDataIdlingResource; // Or the type of your LiveData
    private QuestionItem expectedCorrectAnswer;

    @Before
    public void setup() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            viewModel = activity.getViewModel();
        });
    }

    @Test
    public void testButtonClickWaitsForCorrectAnswerLiveData() {
        // 1. Define the idle condition: Wait until correctAnswerLiveData has a non-null value
        correctAnswerLiveDataIdlingResource = new LiveDataIdlingResource<>(viewModel.getCorrectOption(),
                Objects::nonNull // Idle when correctAnswerLiveData is not null
        );

        // 2. Register the IdlingResource
        IdlingRegistry.getInstance().register(correctAnswerLiveDataIdlingResource);

        Log.d(TAG, "testButtonClickWaitsForCorrectAnswerLiveData: registered IdlingResource, should now wait");

        // 3. Get the expected correct answer from ViewModel (inside onActivity - OK)
        activityScenarioRule.getScenario().onActivity(activity -> {
            Log.d(TAG, "testButtonClickWaitsForCorrectAnswerLiveData: getting the livedata");
            viewModel = activity.getViewModel();
            expectedCorrectAnswer = viewModel.getCorrectOption().getValue(); // Get and store it
        });

        // **IMPORTANT: UI Interaction (Espresso.onView().perform()) is now OUTSIDE onActivity()**
        // **On the test thread, where Espresso UI actions should be.**
        // Act: Click the button with the expected correct answer text
        Espresso.onView(ViewMatchers.withText(expectedCorrectAnswer.getImageText()))
                .perform(ViewActions.click());

        // Assert: (Optional) Add assertions to verify UI changes after the click
        // You can add assertions here if needed, also outside onActivity()

    }

    @After
    public void teardown() {
        // 4. Unregister the IdlingResource
        IdlingRegistry.getInstance().unregister(correctAnswerLiveDataIdlingResource);

    }
}
