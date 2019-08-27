package com.example.nikolakis.pocketsoccer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.nikolakis.pocketsoccer.activities.GameOverActivity;
import com.example.nikolakis.pocketsoccer.database.Result;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter {

    class ResultHolder extends RecyclerView.ViewHolder {

        private TextView textViewResult1, textViewResult2, textViewDateTime;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);
            textViewResult1 = itemView.findViewById(R.id.textViewResult1);
            textViewResult2 = itemView.findViewById(R.id.textViewResult2);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
        }

        public void setText(Result result) {
            if (result.getName1().equals(name1)) {
                textViewResult1.setText(result.getGoals1() + "");
                textViewResult2.setText(result.getGoals2() + "");
            }
            else {
                textViewResult1.setText(result.getGoals2() + "");
                textViewResult2.setText(result.getGoals1() + "");
            }
            textViewDateTime.setText(result.getTime() + "");
        }
    }

    class PlayersHolder extends RecyclerView.ViewHolder {

        private TextView textViewPlayer1, textViewPlayer2, textViewHeadToHead;

        public PlayersHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("playerName1", textViewPlayer1.getText().toString());
                    intent.putExtra("playerName2", textViewPlayer2.getText().toString());
                    intent.putExtra("fromStatisticsActivity", true);
                    context.startActivity(intent);
                }
            });
            textViewPlayer1 = itemView.findViewById(R.id.textViewResult1);
            textViewPlayer2 = itemView.findViewById(R.id.textViewResult2);
            textViewHeadToHead = itemView.findViewById(R.id.textViewDateTime);
        }

        public void setText(Result result) {
            textViewPlayer1.setText(result.getName1());
            textViewPlayer2.setText(result.getName2());
            textViewHeadToHead.setText(result.getGoals1() + " : " + result.getGoals2());
        }
    }

    private List<Result> allResults;
    private LayoutInflater layoutInflater;
    private String name1, name2;
    private boolean statisticsActivity;
    private Context context;

    public ResultListAdapter(Context context, String name1, String name2, boolean statisticsActivity) {
        this.context = context;
        this.name1 = name1;
        this.name2 = name2;
        this.statisticsActivity = statisticsActivity;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycler_view_item, viewGroup,false);
        if (statisticsActivity) {
            return new PlayersHolder(view);
        }
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (allResults != null) {
            if (statisticsActivity) {
                ((PlayersHolder)viewHolder).setText(allResults.get(i));
            }
            else {
                ((ResultHolder)viewHolder).setText(allResults.get(i));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (allResults != null) {
            return allResults.size();
        }
        return 0;
    }

    public void setResults(List<Result> results) {
        this.allResults = results;
        notifyDataSetChanged();
    }
}
