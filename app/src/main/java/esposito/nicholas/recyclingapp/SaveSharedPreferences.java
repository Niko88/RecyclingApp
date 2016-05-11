package esposito.nicholas.recyclingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nicholasesposito on 10/05/2016.
 */
public class SaveSharedPreferences {
    static final String PREF_FIRST_ACCESS = "firstAccess";
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String firstAccess)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FIRST_ACCESS, firstAccess);
        editor.commit();
    }

    public static String getAccess(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_FIRST_ACCESS, "");
    }
}
