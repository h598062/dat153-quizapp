package no.dat153.quizzler.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executors;

import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.utils.Utils;

@Database(entities = {QuestionItem.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {


    private static volatile QuestionDatabase INSTANCE;

    public static QuestionDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    QuestionDatabase.class, "database.db").addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        getInstance(context).questionItemDAO().insertAll(Utils.getDefaultData(context));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract QuestionItemDAO questionItemDAO();
}
