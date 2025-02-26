package no.dat153.quizzler;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import no.dat153.quizzler.view.GalleryActivity;
import no.dat153.quizzler.view.MainActivity;
import no.dat153.quizzler.view.QuizActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public IntentsRule intentsTestRule = new IntentsRule();

    @Test
    public void TestStartQuizButton() {
        onView(withId(R.id.btnStartQuiz)).perform(click());
        intended(hasComponent(QuizActivity.class.getName()));
    }

    @Test
    public void TestOpenGalleryButton() {
        onView(withId(R.id.btnGalleri)).perform(click());
        intended(hasComponent(GalleryActivity.class.getName()));
    }
}