package no.dat153.quizzler.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import no.dat153.quizzler.entity.QuestionItem;

@Database(entities = {QuestionItem.class}, version = 1)
public abstract class GalleryDatabase extends RoomDatabase {


    private static volatile GalleryDatabase INSTANCE;

    public static GalleryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GalleryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GalleryDatabase.class, "gallery_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract GalleryItemDAO galleryItemDAO();
}
