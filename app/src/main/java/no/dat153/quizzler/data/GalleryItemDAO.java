package no.dat153.quizzler.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object for GalleryItem.
 * Defines the methods that can be used to interact with the database.
 */
@Dao
public interface GalleryItemDAO {
    /**
     * Inserts a new GalleryItem into the database.
     * If the item already exists, it will be replaced.
     *
     * @param item The Gallery item to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GalleryItem item);

    /**
     * Inserts multiple GalleryItems into the database.
     * If any of the items already exist, they will be replaced.
     *
     * @param items The Gallery items to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GalleryItem... items);

    /**
     * Retrieves a GalleryItem from the database.
     *
     * @param id The id of the Gallery item to retrieve.
     * @return The Gallery item with the given id.
     */
    LiveData<GalleryItem> getGalleryItem(int id);

    /**
     * Retrieves all GalleryItems from the database.
     *
     * @return A list of all Gallery items in the database.
     */
    LiveData<List<GalleryItem>> getAllGalleryItems();

    /**
     * Updates an existing GalleryItem in the database.
     *
     * @param item The Gallery item to update.
     */
    @Update
    void update(GalleryItem item);

    /**
     * Deletes a GalleryItem from the database.
     *
     * @param item The Gallery item to delete.
     */
    @Delete
    void delete(GalleryItem item);



}
