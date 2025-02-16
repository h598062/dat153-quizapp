package no.dat153.quizzler.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GalleryRepo {
    private GalleryItemDAO galleryItemDao;
    private LiveData<List<GalleryItem>> allGalleryItems;
    private ExecutorService executorService;

    public GalleryRepo(Application application) {
        GalleryDatabase db = GalleryDatabase.getInstance(application);
        galleryItemDao = db.galleryItemDAO();
        allGalleryItems = galleryItemDao.getAllGalleryItems();
        executorService = Executors.newSingleThreadExecutor(); // Runs async operations
    }

    public LiveData<List<GalleryItem>> getAllGalleryItems() {
        return allGalleryItems;
    }

    public void insert(GalleryItem item) {
        executorService.execute(() -> galleryItemDao.insert(item));
    }

    public void update(GalleryItem item) {
        executorService.execute(() -> galleryItemDao.update(item));
    }

    public void delete(GalleryItem item) {
        executorService.execute(() -> galleryItemDao.delete(item));
    }

    public void deleteAll() {
        executorService.execute(() -> galleryItemDao.deleteAll());
    }
}
