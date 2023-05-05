package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizer.Adapter.RecyclerViewAdapter;
import com.example.organizer.Comparator.SortAZNoteComparator;
import com.example.organizer.Comparator.SortZANoteComparator;
import com.example.organizer.Model.Note;

import java.util.Collections;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private String clickedButton;
    private String searchText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DBHelper dbHelper;
    private ImageView noSearchResultImageView;
    private TextView noSearchResultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setTitle("Деталі пошуку");

        clickedButton = getIntent().getStringExtra(SearchActivity.BUTTON_ID);
        searchText = getIntent().getStringExtra(SearchActivity.SEARCH_TEXT_ID);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_SEARCHRESULTACTIVITY);
        noSearchResultImageView = (ImageView) findViewById(R.id.no_result_search_imageview_SEARCHRESULTACTIVITY);
        noSearchResultText = (TextView) findViewById(R.id.no_result_search_SEARCHRESULTACTIVITY);
        dbHelper = new DBHelper(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        switch (clickedButton)
        {
            case SearchActivity.SEARCH_BY_TITLE_BUTTON:
                List<Note> searchByTitleList = dbHelper.searchNotesByTitle(searchText);
                ShowSearchResult(searchByTitleList);
                break;

            case SearchActivity.SEARCH_BY_TIME_BUTTON:
                List<Note> searchByTimeList = dbHelper.searchNotesByTime(searchText);
                ShowSearchResult(searchByTimeList);
                break;

            case SearchActivity.SEARCH_BY_DATE_BUTTON:
                List<Note> searchByDateList = dbHelper.searchNotesByDate(searchText);
                ShowSearchResult(searchByDateList);
                break;

            case SearchActivity.SORT_ALPHABETICAL_AZ_BUTTON:
                List<Note> sortedAZList = dbHelper.getAllNotes();
                Collections.sort(sortedAZList, new SortAZNoteComparator());
                ShowSearchResult(sortedAZList);
                break;

            case SearchActivity.SORT_ALPHABETICAL_ZA_BUTTON:
                List<Note> sortedZAList = dbHelper.getAllNotes();
                Collections.sort(sortedZAList, new SortZANoteComparator());
                ShowSearchResult(sortedZAList);
                break;

            case SearchActivity.SORT_DATE_AND_TIME_BUTTON:
                List<Note> sortedByDateAndTimeList = dbHelper.getSortedByTimeAndDateCreation();
                ShowSearchResult(sortedByDateAndTimeList);
                break;
        }
    }

    private void ShowSearchResult(List<Note> noteList)
    {
        if (noteList.size() == 0)
        {
            noSearchResultImageView.setVisibility(View.VISIBLE);
            noSearchResultText.setVisibility(View.VISIBLE);
        }
        else
        {
            mAdapter = new RecyclerViewAdapter(noteList, this);
            recyclerView.setAdapter(mAdapter);
            noSearchResultImageView.setVisibility(View.INVISIBLE);
            noSearchResultText.setVisibility(View.INVISIBLE);
        }
        Toast.makeText(this, "Результат пошуку: " + noteList.size(), Toast.LENGTH_SHORT).show();
    }
}