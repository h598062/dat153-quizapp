package no.dat153.quizzler;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

public class LiveDataIdlingResource<T> implements IdlingResource {

    private static final String TAG = "LiveDataIdlingResource"; // Tag for logs

    private final LiveData<T> liveData;
    private final Predicate<T> idleCondition;
    private ResourceCallback resourceCallback;
    private boolean isIdle = false;
    private final CountDownLatch latch = new CountDownLatch(1);
    private Observer<T> observer;


    public LiveDataIdlingResource(LiveData<T> liveData, Predicate<T> idleCondition) {
        this.liveData = liveData;
        this.idleCondition = idleCondition;
    }

    @Override
    public String getName() {
        return "LiveDataIdlingResource: " + liveData.toString();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) {
            Log.d(TAG, getName() + " - Already Idle: true"); // Log: Already idle
            return true;
        }

        T value = liveData.getValue();
        boolean conditionMet = idleCondition.test(value);
        Log.d(TAG, getName() + " - isIdleNow called, value: " + value + ", conditionMet: " + conditionMet); // Log: Value and condition

        if (conditionMet) {
            isIdle = true;
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
                Log.d(TAG, getName() + " - Transitioned to Idle, notified callback"); // Log: Transition to idle
            }
            latch.countDown();
            if (observer != null) {
                liveData.removeObserver(observer);
                observer = null;
                Log.d(TAG, getName() + " - Observer removed"); // Log: Observer removed
            }
            return true;
        }
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        Log.d(TAG, getName() + " - registerIdleTransitionCallback called"); // Log: Register callback
        this.resourceCallback = callback;

        if (observer == null) {
            observer = new Observer<T>() {
                @Override
                public void onChanged(T t) {
                    Log.d(TAG, getName() + " - LiveData onChanged, value: " + t); // Log: LiveData update
                    if (!isIdle && isIdleNow()) {
                        if (resourceCallback != null) {
                            resourceCallback.onTransitionToIdle();
                            Log.d(TAG, getName() + " - Transitioned to Idle (from onChanged), notified callback"); // Log: Transition to idle from onChanged
                        }
                        latch.countDown();
                        if (observer != null) {
                            liveData.removeObserver(observer);
                            observer = null;
                            Log.d(TAG, getName() + " - Observer removed (from onChanged)"); // Log: Observer removed from onChanged
                        }
                    }
                }
            };
            liveData.observeForever(observer);
            Log.d(TAG, getName() + " - Observer added"); // Log: Observer added
        }
    }

    public void waitForIdle(long timeoutSeconds) throws InterruptedException, TimeoutException {
        if (!isIdleNow()) {
            if (!latch.await(timeoutSeconds, TimeUnit.SECONDS)) {
                throw new TimeoutException("LiveDataIdlingResource timed out after " + timeoutSeconds + " seconds waiting for idle condition.");
            }
        }
    }
}
