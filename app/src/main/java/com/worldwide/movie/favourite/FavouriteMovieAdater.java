package com.worldwide.movie.favourite;

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
import com.worldwide.movie.movie.MovieClickListener;
import java.util.List;

import static com.worldwide.movie.util.Config.BaseImageUrl;
import static com.worldwide.movie.util.NullChecker.requireNonNull;

/**
 * Created by Anand on 12-03-2018.
 */

public class FavouriteMovieAdater extends RecyclerView.Adapter<FavouriteMovieAdater.ViewHolder> {

  private List<Movie> items;
  private MovieClickListener clickListner;

  public FavouriteMovieAdater(List<Movie> items, MovieClickListener listener) {
    this.items = items;
    this.clickListner = listener;
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_brief, parent, false);

    ViewHolder vh = new ViewHolder(v);

    return vh;
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Movie movie = items.get(position);
    holder.tvTitle.setText(movie.getOriginalTitle());

    Picasso.get()
        .load(BaseImageUrl + movie.getImage())
        .placeholder(R.drawable.placeholder)
        .fit()
        .into(holder.ivPoster);
    holder.tvRatingCount.setText(String.valueOf(movie.getVoteAverage()));

    holder.tvReleaseDate.setText(String.valueOf(movie.getReleaseDate()));

    holder.itemView.setOnClickListener(view -> clickListner.onMovieSelected(movie));
  }

  public void replaceData(List<Movie> movies) {
    setList(movies);
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
    public TextView tvTitle, tvRatingCount, tvReleaseDate;

    public ViewHolder(View itemView) {
      super(itemView);
      ivPoster = itemView.findViewById(R.id.ivPoster_brief);
      tvTitle = itemView.findViewById(R.id.tvTitle);
      tvRatingCount = itemView.findViewById(R.id.tvRatingCount);
      tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
    }
  }
}