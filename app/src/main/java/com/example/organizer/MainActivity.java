package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizer.Adapter.RecyclerViewAdapter;
import com.example.organizer.Model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView notesIsEmptyTextView;
    private ImageView emptyDataImageView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;
    DBHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        notesIsEmptyTextView = (TextView) findViewById(R.id.notes_is_empty_text_MAINACTIVTIY);
        emptyDataImageView = (ImageView) findViewById(R.id.empty_imageview_MAINACTIVITY);

        List<Note> noteList = dbHelper.getAllNotes();

        if (!noteList.isEmpty())
        {
            notesIsEmptyTextView.setVisibility(View.INVISIBLE);
            emptyDataImageView.setVisibility(View.INVISIBLE);


            recyclerView =(RecyclerView) findViewById(R.id.recycler_view_MAINACTIVITY);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new RecyclerViewAdapter(noteList, this);
            recyclerView.setAdapter(mAdapter);

        }
        else
        {
            notesIsEmptyTextView.setVisibility(View.VISIBLE);
            emptyDataImageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_search_MYMENUFILE:
                StartSearchActivity();
                return true;

            case R.id.nav_add_new_note_MYMENUFILE:
                StartAddNewNoteActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void StartSearchActivity()
    {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void StartAddNewNoteActivity()
    {
        Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
        startActivity(intent);
    }
}