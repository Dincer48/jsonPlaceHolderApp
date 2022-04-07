package com.example.univera.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univera.R;
import com.example.univera.model.Album;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private Context mCon;
    private List<Album> mAlbumsList;

    public AlbumListAdapter(Context mCon, List<Album> mAlbumsList) {
        this.mCon = mCon;
        this.mAlbumsList = mAlbumsList;
    }

    @Override
    public AlbumListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCon).inflate(R.layout.card_album_list, parent, false);
        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListViewHolder holder, int position) {
        Album albumsModel = mAlbumsList.get(position);
        holder.tvAlbumUserId.setText("User Id:" + albumsModel.getmUserId());
        holder.tvAlbumId.setText("Id:" + albumsModel.getmId());
        holder.tvAlbumTitle.setText("Title:" + albumsModel.getmTitle());
        holder.albumCard.setCardBackgroundColor(getRandomColorCode());
    }

    @Override
    public int getItemCount() {
        return mAlbumsList.size();
    }

    public static class AlbumListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_album_userId)
        TextView tvAlbumUserId;
        @BindView(R.id.tv_album_id)
        TextView tvAlbumId;
        @BindView(R.id.tv_album_title)
        TextView tvAlbumTitle;
        @BindView(R.id.albums_card)
        CardView albumCard;

        public AlbumListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }
}