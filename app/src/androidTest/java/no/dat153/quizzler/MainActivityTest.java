package no.dat153.quizzler;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import no.dat153.quizzler.view.GalleryActivity;
import no.dat153.quizzler.view.MainActivity;
import no.dat153.quizzler.view.QuizActivity;

public class MainActivityTest {
    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(MainActivity.class);
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
        scenario.close();
    }

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