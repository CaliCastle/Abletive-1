package data;

import android.app.Application;
import android.content.Context;

/**
 * Created by Alan on 2016/3/7.
 */
public class MApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

}
