package no.dat153.quizzler.data;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.utils.Utils;

@Database(entities = {QuestionItem.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static final String TAG = "QuestionDatabase";
    private static volatile QuestionDatabase INSTANCE;

    public static QuestionDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    QuestionDatabase.class, "database.db").addCallback(new Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        Log.d(TAG, "Legger til data i databasen...");
                                        getInstance(context).questionItemDAO().insertAll(Utils.getDefaultData(context));
                                        Log.d(TAG, "Lagt til data i databasen!");
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
