package com.astro.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Context context;
    ArrayList<Chat> chatArrayList;

    public ChatAdapter(Context context, ArrayList<Chat> chatArrayList) {
        this.context = context;
        this.chatArrayList = chatArrayList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_layout,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.textViewusername.setText(chatArrayList.get(position).getUsername());
        holder.textViewmessage.setText(chatArrayList.get(position).getMessage());
        holder.textViewdatatime.setText(chatArrayList.get(position).getDatatime());
        Picasso.get().load(chatArrayList.get(position).getImageurl()).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textViewusername,textViewmessage,textViewdatatime;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=itemView.findViewById(R.id.imageviewchatprofil);
            textViewusername=itemView.findViewById(R.id.textviewchatusername);
            textViewmessage=itemView.findViewById(R.id.textviewchatmessage);
            textViewdatatime=itemView.findViewById(R.id.textviewchatdatatime);
        }
    }
}
