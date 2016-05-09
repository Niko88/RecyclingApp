package esposito.nicholas.recyclingapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by nicholasesposito on 06/05/2016.
 */
public class RecyclingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "lBELqEoCZZfMca04d0Rv1CekRwQ6zWUmktgmBfzU", "EVKDzkFzhypVOD3MXKdCX6KqNeKg4QhJjzONGO26");
    }
}
