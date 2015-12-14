package hu.ait.android.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.bananasplit.adapter.AdventureRecyclerAdapter;
import hu.ait.android.bananasplit.data.Adventure;


/**
 * Created by Josh on 12/10/15.
 */
public class MainActivity extends AppCompatActivity implements AddAdventureFragment.AddAdventureInterface {

    private AdventureRecyclerAdapter adapter;
    @Bind(R.id.addAdventure)
    FloatingActionButton addAdventure;

    //add recycler adapter and dialogue fragment to add adventure

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey("USER_NAME")) {
            Intent intent = getIntent();
            String username;
            username = intent.getStringExtra("USER_NAME");
            tvTitle.setText(username+"'s Adventures");

        }
        adapter = new AdventureRecyclerAdapter(this);
        final RecyclerView rva = (RecyclerView) findViewById(R.id.rva);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rva.setLayoutManager(linearLayoutManager);


        ParseQuery<Adventure> parseQuery = Adventure.getQuery();
        parseQuery.findInBackground(new FindCallback<Adventure>() {
            @Override
            public void done(List<Adventure> objects, ParseException e) {
                if (e == null) {

                    for (Adventure adv : objects) {
                        adapter.addAdventure(adv);
                    }
                    rva.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("MainActivity", "woops: " + e.getMessage());
                }
            }
        });

    }


    @OnClick(R.id.addAdventure)
    public void startAddAdventureFragment() {
        new AddAdventureFragment().show(getSupportFragmentManager(), AddAdventureFragment.TAG);
    }

    @Override
    public void onAddAdventureFragmentResult(final Adventure adventure) {

        adventure.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //add the adventure to adapter to be viewed
                    adapter.addAdventure(adventure);

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}