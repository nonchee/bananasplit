package hu.ait.android.bananasplit.data;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nancy on 12/8/15.
 */

@ParseClassName("Adventure")
public class Adventure {

    //names of all the users
    ArrayList<ParseUser> coAdventurers;
    ArrayList<String> adventurerNames;

    //required blank constructor
    public Adventure() {
        coAdventurers = new ArrayList<ParseUser>();
        adventurerNames = new ArrayList<String>();
    }

    public void addUser(ParseUser adventurer) {
        coAdventurers.add(adventurer);
    }

    public ArrayList<ParseUser> getUsers() {
        return coAdventurers;
    }

    public int getNumAdventurers() {
        return coAdventurers.size();
    }

    public void addAnyNewUsersFromParseUserList(List<ParseUser> parseUsers) {

        for (ParseUser user : parseUsers) {
            if (!coAdventurers.contains(user)) {
                coAdventurers.add(user);
            }
        }
    }


    public void addAnyNewUsersFromStringUsernameList(List<String> usernames) {
        Log.d("add new users!", "adding " + usernames.size() + "new users");
        for (String username : usernames) {
            Log.d("add new users!", username);
            if (!adventurerNames.contains(username)) {
                Log.d("add new users!", "affirmative! added " + username);
                adventurerNames.add(username);
            }
        }
    }


    public ArrayList<String> getUserStrings() {

        return adventurerNames;
    }
}
