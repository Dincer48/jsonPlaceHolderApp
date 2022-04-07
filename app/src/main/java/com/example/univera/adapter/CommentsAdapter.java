package com.example.univera.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univera.R;
import com.example.univera.model.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<Comment> commentsModelList;
    private Context mContext;

    public CommentsAdapter(Context mContext, List<Comment> commentsModelList) {
        this.commentsModelList = commentsModelList;
        this.mContext = mContext;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_comments_list, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comment commentsModel = commentsModelList.get(position);
        holder.commentspostId.setText("posts ID: " + commentsModel.getmPostId());
        holder.commentspostId.setTextColor(Color.parseColor("#FFFFFF"));
        holder.commentsId.setText("ID: " + commentsModel.getmId());
        holder.commentsId.setTextColor(Color.parseColor("#FFFFFF"));
        holder.commentsName.setText("Name: " + commentsModel.getmName());
        holder.commentsName.setTextColor(Color.parseColor("#FFFFFF"));
        holder.commentsEmail.setText("Email: " + commentsModel.getmEmail());
        holder.commentsEmail.setTextColor(Color.parseColor("#FFFFFF"));
        holder.commentsBody.setText("Body: " + commentsModel.getmBody());
        holder.commentsBody.setTextColor(Color.parseColor("#FFFFFF"));


    }

    @Override
    public int getItemCount() {
        return commentsModelList.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comments_postId)
        TextView commentspostId;
        @BindView(R.id.comments_id)
        TextView commentsId;
        @BindView(R.id.comments_name)
        TextView commentsName;
        @BindView(R.id.comments_email)
        TextView commentsEmail;
        @BindView(R.id.comments_body)
        TextView commentsBody;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}