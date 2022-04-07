package com.example.univera.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.univera.R;
import com.example.univera.adapter.CommentsAdapter;
import com.example.univera.api.ApiClient;
import com.example.univera.api.CommentsInterface;
import com.example.univera.api.Const;
import com.example.univera.helper.CheckConnection;
import com.example.univera.model.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsActivity extends AppCompatActivity {

    @BindView(R.id.comments)
    RecyclerView commentsList;
    @BindView(R.id.swipe_comments)
    SwipeRefreshLayout swipeComments;
    private CommentsAdapter commentsAdapter;
    private ArrayList<Comment> comment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_comments);
        ButterKnife.bind(this);
        initialization();
        getComments();

    }

    private void initialization() {
        commentsList.setHasFixedSize(true);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        swipeComments.setOnRefreshListener(this::getComments);
    }

    private void getComments() {
        swipeComments.setRefreshing(true);
        Call<List<Comment>> listCall = ApiClient.getClient().create(CommentsInterface.class).getComments(Const.BASE_URL + "comments");
        listCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NotNull Call<List<Comment>> call, @NotNull Response<List<Comment>> response) {
                swipeComments.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    comment = new ArrayList<>(response.body());
                    commentsAdapter = new CommentsAdapter(CommentsActivity.this, comment);
                    commentsList.setAdapter(commentsAdapter);
                }
            }
            @Override
            public void onFailure(@NotNull Call<List<Comment>> call, @NotNull Throwable t) {
                if (!CheckConnection.isConnection(CommentsActivity.this)) {
                    swipeComments.setRefreshing(false);
                    isConnectedCheck();
                } else {
                    swipeComments.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
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
                        getComments();
                    }
                }).create();
        dialog.show();
    }
}
