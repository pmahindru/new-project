package com.example.csci3130_project_group17;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class profileReviewAdapter extends RecyclerView.Adapter {

    private List<Review> reviewList;

    public profileReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_review_item,parent,false);
        profileReviewAdapter.ProfileViewHolder historyViewHolder = new profileReviewAdapter.ProfileViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        profileReviewAdapter.ProfileViewHolder profileViewHolder = (profileReviewAdapter.ProfileViewHolder) holder;
        Review review = reviewList.get(position);
        profileViewHolder.review.setText("Comment: " + review.getComment());
        profileViewHolder.rating.setRating(review.getRating());
        profileViewHolder.name.setText("From: " + review.getReviewerName());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView review, name;
        RatingBar rating;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.profileReviewText);
            name = itemView.findViewById(R.id.profileReviewName);
            rating = itemView.findViewById(R.id.ratingBar);
        }
    }
}
