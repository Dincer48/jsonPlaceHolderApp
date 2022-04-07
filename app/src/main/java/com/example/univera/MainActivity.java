package com.example.univera;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.univera.helper.CheckConnection;
import com.example.univera.ui.AlbumsListActivity;
import com.example.univera.ui.CommentsActivity;
import com.example.univera.ui.LoginActivity;
import com.example.univera.ui.UserListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.userList)
    TextView userList;
    @BindView(R.id.albumList)
    TextView albumList;
    @BindView(R.id.commentsList)
    TextView commentsList;

    static Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        onClickGetList();
    }

    private void onClickGetList() {
        onClickUserList();
        onClickAlbumsList();
        onClickCommentsList();
    }

    private void onClickCommentsList() {
        commentsList.setOnClickListener(view -> {
            if(CheckConnection.isConnection(this)){
                Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                startActivity(intent);
            }else{
               isConnectedCheck();
            }
        });
    }

    private void onClickAlbumsList() {
        albumList.setOnClickListener(view -> {
            if(CheckConnection.isConnection(this)){
                Intent intent = new Intent(MainActivity.this, AlbumsListActivity.class);
                startActivity(intent);
            }else{
               isConnectedCheck();
            }
        });
    }

    private void onClickUserList() {
        userList.setOnClickListener(view -> {
            if(CheckConnection.isConnection(this)){
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }else{
                isConnectedCheck();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  public void isConnectedCheck(){
      AlertDialog dialog = new AlertDialog.Builder(this   )
              .setTitle("Connection Failed")
              .setMessage("Please Check Your Internet Connection")
              .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {

                  }
              }).create();
      dialog.show();
  }
}