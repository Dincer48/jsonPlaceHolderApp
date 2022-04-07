package com.example.univera.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univera.CustomItemClickListener;
import com.example.univera.R;
import com.example.univera.model.user.User;

import java.util.ArrayList;
import java.util.Random;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> implements Filterable {
    private ArrayList<User> userList;
    private ArrayList<User> filteredUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public UserListAdapter(Context context, ArrayList<User> userArrayList, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getLayoutPosition()), myViewHolder.getLayoutPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder viewHolder, int position) {

        viewHolder.userName.setText(filteredUserList.get(position).getUsername());
        viewHolder.userCity.setText(filteredUserList.get(position).getAddress().getCity());
        viewHolder.userWeb.setText(filteredUserList.get(position).getWebsite());
        viewHolder.userEmail.setText(filteredUserList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<User> tempFilteredList = new ArrayList<>();

                    for (User user : userList) {

                        if (user.getUsername().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userEmail;
        private TextView userCity;
        private TextView userWeb;
        private CardView carView;
        public ViewHolder(View view) {
            super(view);
            userName = (TextView)view.findViewById(R.id.userName);
            userEmail = (TextView)view.findViewById(R.id.userEmail);
            userWeb = (TextView)view.findViewById(R.id.userWebPage);
            userCity = (TextView)view.findViewById(R.id.userCity);
            carView = (CardView)view.findViewById(R.id.card_view);
            carView.setCardBackgroundColor(getRandomColorCode());
        }
    }

    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }
}
