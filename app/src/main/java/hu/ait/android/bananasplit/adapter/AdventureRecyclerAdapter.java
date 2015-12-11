package hu.ait.android.bananasplit.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.ait.android.bananasplit.ExpenseSheetActivity;
import hu.ait.android.bananasplit.R;
import hu.ait.android.bananasplit.data.Adventure;
import hu.ait.android.bananasplit.data.Expense;

/**
 * Created by joshuapitkofsky on 12/11/15.
 */
public class AdventureRecyclerAdapter extends RecyclerView.Adapter<AdventureRecyclerAdapter.AdventureViewHolder>{

    private Context context;
    private List<Adventure> adventureList;


    public AdventureRecyclerAdapter(Context context) {
        this.context = context;

        adventureList = new ArrayList<Adventure>();
        Adventure.getQuery();
        /*/Exp.listAll(Todo.class);
*/
    }


    @Override
    public void onBindViewHolder(AdventureRecyclerAdapter.AdventureViewHolder holder, final int position) {
        final Adventure adventure = adventureList.get(position);
        holder.adventureName.setText(adventure.getAdventureName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExpenseSheetActivity.class);
                Adventure adventure = adventureList.get(position);
                intent.putExtra("ADVENTURE",adventure);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return adventureList.size();
    }




    @Override
    public AdventureRecyclerAdapter.AdventureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adventureRow = LayoutInflater.from(
                context).inflate(R.layout.adventure, parent, false);
        return new AdventureRecyclerAdapter.AdventureViewHolder(adventureRow);
    }

    public void addAdventure(Adventure adventure) {
        Toast.makeText(context, "" + adventure.getAdventureName(), Toast.LENGTH_SHORT).show();
        adventureList.add(adventure);
        notifyDataSetChanged();

    }

    public Adventure getitem(int position) {
        return adventureList.get(position);
    }


    //setting the fields of the Adventure row layout
    public class AdventureViewHolder extends RecyclerView.ViewHolder {

        private final TextView adventureName;

        public AdventureViewHolder(View itemView) {
            super(itemView);
            adventureName = (TextView) itemView.findViewById(R.id.adventureName);

        }
    }
}
