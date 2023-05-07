package com.example.tempfile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    ArrayList<User> activityList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        activityList = generateUserList(20);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        BrandsAdapter mAdapter = new BrandsAdapter(activityList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    //Auto generate users
    private ArrayList<User> generateUserList(int count) {
        ArrayList<User> userList = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            boolean followed = random.nextBoolean();

            long min1 = 100000000L;  // Minimum 9-digit number
            long max1 = 9999999999L; // Maximum 10-digit number
            long randomNumber1 = min1 + ((long) (random.nextDouble() * (max1 - min1)));
            String name = "User " + randomNumber1;

            long min2 = 100000000L;  // Minimum 9-digit number
            long max2 = 9999999999L; // Maximum 10-digit number
            long randomNumber2 = min2 + ((long) (random.nextDouble() * (max2 - min2)));
            String description = "Description " + randomNumber2;

            User user = new User(name, description, followed);
            userList.add(user);
        }

        return userList;
    }

    //ViewHolder
    public class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView brandImageView;

        public BrandViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            brandImageView = itemView.findViewById(R.id.imageView2);
        }
    }

    public class BrandsAdapter extends RecyclerView.Adapter<BrandViewHolder> {
        ArrayList<User> data;

        public BrandsAdapter(ArrayList<User> input) {
            data = input;
        }

        @Override
        public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            return new BrandViewHolder(item);
        }

        @Override
        public void onBindViewHolder(BrandViewHolder holder, int position) {
            User user = data.get(position);
            holder.nameTextView.setText(user.getName());
            holder.descriptionTextView.setText(user.getDescription());
            holder.brandImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Profile");
                    builder.setMessage(user.getDescription());
                    builder.setCancelable(true);
                    builder.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(v.getContext(), MainActivity2.class);
                            intent.putExtra("New_Text", user.getName());
                            v.getContext().startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}