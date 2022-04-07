package com.example.univera.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.univera.R;
import com.example.univera.adapter.AlbumListAdapter;
import com.example.univera.api.AlbumsInterface;
import com.example.univera.api.ApiClient;
import com.example.univera.helper.CheckConnection;
import com.example.univera.model.Album;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsListActivity extends AppCompatActivity {

    @BindView(R.id.albumsList)
    RecyclerView albumList;
    @BindView(R.id.swipe_albums)
    SwipeRefreshLayout swipeAlbums;

    private AlbumListAdapter albumsListAdapter;
    private List<Album> albumsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_albums_list);
        ButterKnife.bind(this);
        initialization();
        getAlbumsList();
    }

    private void initialization() {
        albumList.setHasFixedSize(true);
        albumList.setLayoutManager(new LinearLayoutManager(this));
        swipeAlbums.setOnRefreshListener(this::getAlbumsList);

    }

    private void getAlbumsList() {
        swipeAlbums.setRefreshing(true);

        Call<List<Album>> call = ApiClient.getClient().create(AlbumsInterface.class).getAlbumsList();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(@NotNull Call<List<Album>> call, @NotNull Response<List<Album>> response) {
                swipeAlbums.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    albumsList = new ArrayList<>(response.body());
                    albumsListAdapter = new AlbumListAdapter(AlbumsListActivity.this, albumsList);
                    albumList.setAdapter(albumsListAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Album>> call, @NotNull Throwable t) {
                if (!CheckConnection.isConnection(AlbumsListActivity.this)) {
                    swipeAlbums.setRefreshing(false);
                    isConnectedCheck();
                } else {
                    swipeAlbums.setRefreshing(false);
                    message("Error Message: "+t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void isConnectedCheck(){
        AlertDialog dialog = new AlertDialog.Builder(this   )
                .setTitle("Connection Failed")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       getAlbumsList();
                    }
                }).create();
        dialog.show();
    }
}