package com.worldwide.movie.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Movie;
import java.util.ArrayList;
import java.util.List;

import static com.worldwide.movie.util.Config.BaseImageUrl;
import static com.worldwide.movie.util.NullChecker.requireNonNull;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

  private List<Movie> items;
  private MovieClickListener clickListner;

  public MovieAdapter(ArrayList<Movie> items, MovieClickListener listener) {
    this.items = items;
    this.clickListner = listener;
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);

    ViewHolder vh = new ViewHolder(v);

    return vh;
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Movie movie = items.get(position);

    Picasso.get()
        .load(BaseImageUrl + movie.getImage())
        .placeholder(R.drawable.placeholder)
        .fit()
        .into(holder.ivPoster);
    holder.itemView.setOnClickListener(view -> clickListner.onMovieSelected(movie));
  }

  public void replaceData(List<Movie> tasks) {
    setList(tasks);
    notifyDataSetChanged();
  }

  private void setList(List<Movie> movies) {
    items = requireNonNull(movies);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivPoster;
    public TextView tvTitle;

    public ViewHolder(View itemView) {
      super(itemView);
      ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
    }
  }
}