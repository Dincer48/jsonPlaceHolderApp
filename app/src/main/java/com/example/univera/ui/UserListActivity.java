package com.example.univera.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.univera.CustomItemClickListener;
import com.example.univera.R;
import com.example.univera.adapter.UserListAdapter;
import com.example.univera.api.ApiClient;
import com.example.univera.api.ApiInterface;
import com.example.univera.helper.CheckConnection;
import com.example.univera.model.user.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    private UserListAdapter userListAdapter;
    private ArrayList<User> userlist = new ArrayList<>();

    Dialog dialog;

    @BindView(R.id.usersList)
    RecyclerView userLists;
    @BindView(R.id.swipe_users)
    SwipeRefreshLayout swipeUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_layout);
        dialog = builder.create();
        dialog.show();

        setContentView(R.layout.activity_user_list);


        ButterKnife.bind(this);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        userLists.setLayoutManager(layoutManager);
        swipeUsers.setOnRefreshListener(this::getUSerLists);
        userLists.setItemAnimator(new DefaultItemAnimator());
        getUSerLists();


    }
    private void getUSerLists() {
        swipeUsers.setRefreshing(true);

        Call<List<User>> call = ApiClient.getClient().create(ApiInterface.class).getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                swipeUsers.setRefreshing(false);
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    userlist = new ArrayList<>(response.body());
                    userListAdapter = new UserListAdapter(getApplicationContext(), userlist, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(User user, int position) {
                            Toast.makeText(getApplicationContext(), "" + user.getUsername(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    userLists.setAdapter(userListAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                if (!CheckConnection.isConnection(UserListActivity.this)) {
                    swipeUsers.setRefreshing(false);
                    isConnectedCheck();
                } else {
                    swipeUsers.setRefreshing(false);
                }
            }
        });
    }

    public void isConnectedCheck(){
        AlertDialog dialog = new AlertDialog.Builder(this   )
                .setTitle("Connection Failed")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getUSerLists();
                    }
                }).create();
        dialog.show();
    }



}