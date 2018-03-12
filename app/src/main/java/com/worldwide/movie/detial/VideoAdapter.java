package com.worldwide.movie.detial;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Video;
import java.util.List;

import static com.worldwide.movie.util.Config.youtubeThumbnailUrl;
import static com.worldwide.movie.util.NullChecker.requireNonNull;

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

  private List<Video> items;
  private VideoClickListener clickListner;

  public VideoAdapter(List<Video> items, VideoClickListener listener) {
    this.items = items;
    this.clickListner = listener;
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);

    ViewHolder vh = new ViewHolder(v);

    return vh;
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Video video = items.get(position);
    holder.tvTitle.setText(video.getName());

    Picasso.get()
        .load(youtubeThumbnailUrl + video.getKey() + "/mqdefault.jpg")
        .placeholder(R.drawable.placeholder)
        .fit()
        .into(holder.ivPoster);
    holder.itemView.setOnClickListener(view -> clickListner.onVideoSelected(video));
  }

  public void replaceData(List<Video> tasks) {
    setList(tasks);
    notifyDataSetChanged();
  }

  private void setList(List<Video> videos) {
    items = requireNonNull(videos);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivPoster;
    public TextView tvTitle;

    public ViewHolder(View itemView) {
      super(itemView);
      ivPoster = itemView.findViewById(R.id.ivPoster_video);
      tvTitle = itemView.findViewById(R.id.tvTitle);
    }
  }
}