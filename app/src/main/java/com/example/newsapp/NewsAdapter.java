package com.example.newsapp;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private Context context;
    private List<Data> newsList;

    public NewsAdapter(Context context, List<Data> list) {
        this.context = context;
        this.newsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Data data = newsList.get(i);

        viewHolder.tvTitle.setText(data.getTitle());
        viewHolder.tvDate.setText(data.getDate());
        viewHolder.tvAuthor.setText(data.getAuthor());
        viewHolder.tvDescription.setText(data.getDescription());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateList(List<Data> newList){
        newsList = new ArrayList<>();
        newsList.addAll(newList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDate, tvAuthor, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // set custom font to recylerview
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font/quicksand.ttf");
            Typeface custom_font2 = Typeface.createFromAsset(context.getAssets(), "font/quicksand_medium.ttf");

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitle.setTypeface(custom_font2);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDate.setTypeface(custom_font);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvAuthor.setTypeface(custom_font2);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDescription.setTypeface(custom_font);
        }
    }
}
