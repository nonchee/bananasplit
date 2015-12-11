package hu.ait.android.bananasplit.parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;

import hu.ait.android.bananasplit.data.Adventure;
import hu.ait.android.bananasplit.data.Expense;


/**
 * Created by Nancy on 11/9/15.
 */
public class BananaSplitApplication extends Application {

    public static final String APPLICATION_ID = "UyD2cuHqNCQGWQ0G3eYYtldIwYrCUYv2b5pLhAGL";
    public static final String CLIENT_KEY = "ZbQ76RH5WQYwAf6Qvxavf4YaaJllACP7kQHAOhDv";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Expense.class);
        ParseObject.registerSubclass(Adventure.class);

        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        //saves in the backend whether someone has started the application
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("bananasplit");
    }
}
