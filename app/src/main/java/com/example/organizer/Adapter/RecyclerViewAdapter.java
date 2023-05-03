package com.example.organizer.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizer.Model.Note;
import com.example.organizer.ChangeNoteActivity;
import com.example.organizer.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    List<Note> noteList;
    Context context;

    public RecyclerViewAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_note, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note currentNote = noteList.get(position);

        holder.title.setText(currentNote.getTitle());
        holder.description.setText(
                currentNote.getDescription().length() > 45 ?
                currentNote.getDescription().substring(0, 45) + "..." : currentNote.getDescription());
        holder.date.setText(currentNote.getDate());
        holder.time.setText(currentNote.getTime());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteDetailsActivity = new Intent(context, ChangeNoteActivity.class);
                noteDetailsActivity.putExtra("id",  String.valueOf(currentNote.getId()));
                context.startActivity(noteDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        TextView date;
        TextView time;
        RelativeLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_output_ONELINENOTEFILE);
            description = itemView.findViewById(R.id.description_output_ONELINENOTEFILE);
            date = itemView.findViewById(R.id.date_output_ONELINENOTEFILE);
            time = itemView.findViewById(R.id.time_output_ONELINENOTEFILE);

            parentLayout = itemView.findViewById(R.id.one_line_note_relative_layout_ONELINENOTEFILE);
        }
    }
}