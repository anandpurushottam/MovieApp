package com.worldwide.movie.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Anand on 09-03-2018.
 */
@Entity
public class Movie implements Serializable {
  @PrimaryKey
  @SerializedName("id") @Expose private Integer id;
  @SerializedName("vote_count") @Expose private Integer voteCount;

  @SerializedName("vote_average") @Expose private Double voteAverage;
  @SerializedName("title") @Expose private String title;
  @SerializedName("poster_path") @Expose private String image;
  @SerializedName("original_title") @Expose private String originalTitle;
  @SerializedName("backdrop_path") @Expose private String backdropPath;
  @SerializedName("overview") @Expose private String overview;
  @SerializedName("release_date") @Expose private String releaseDate;

  public Movie(int id, String title, String image) {
    this.id = id;
    this.title = title;
    this.image = image;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }
}
