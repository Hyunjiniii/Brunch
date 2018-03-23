package com.example.anhyunjin.brunch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anhyunjin.brunch.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Item> items;
    public View view;
    private Context context;

    public RecyclerAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        final Item contentItem = items.get(position);

        String time = contentItem.getDate();
        int idx = time.indexOf(" ");
        final String time1 = time.substring(0, idx);

        holder.title.setText(contentItem.getTitle());
        holder.date.setText(time1);
        holder.content.setText(contentItem.getContent());

        if (contentItem.isImage()) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.VISIBLE);
            holder.content.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.VISIBLE);

            Glide.with(holder.imageView.getContext())
                    .load(contentItem.geturl())
                    .into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
            holder.title.setVisibility(View.VISIBLE);
            holder.content.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.VISIBLE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Onclick_ImageActivity.class);
                intent.putExtra("url", contentItem.geturl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView title;
        TextView content;
        CardView cardView;
        ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.list_date);
            title = (TextView) itemView.findViewById(R.id.list_title);
            content = (TextView) itemView.findViewById(R.id.list_content);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            imageView = (ImageView) itemView.findViewById(R.id.list_image);
        }
    }
}
