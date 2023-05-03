package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    EditText searchByTitleInputEditText, searchByTimeInputEditText, searchByDataInputEditText;
    Button searchByTitleButton, searchByTimeButton, searchByDataButton, sortAlphabeticalAZButton,
            sortAlphabeticalZAButton, sortByDateAndTimeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Пошук");


        searchByTitleInputEditText = (EditText) findViewById(R.id.search_by_title_input_SEARCHACTIVITY);
        searchByTimeInputEditText = (EditText) findViewById(R.id.search_by_time_input_SEARCHACTIVITY);
        searchByDataInputEditText = (EditText) findViewById(R.id.search_by_data_input_SEARCHACTIVITY);
        searchByTitleButton = (Button) findViewById(R.id.search_by_title_button_SEARCHACTIVITY);
        searchByTimeButton = (Button) findViewById(R.id.search_by_time_button_SEARCHACTIVITY);
        searchByDataButton = (Button) findViewById(R.id.search_by_date_button_SEARCHACTIVITY);
        sortAlphabeticalAZButton = (Button) findViewById(R.id.sort_A_Z_button_SEARCHACTIVITY);
        sortAlphabeticalZAButton = (Button) findViewById(R.id.sort_Z_A_button_SEARCHACTIVITY);
        sortByDateAndTimeButton = (Button) findViewById(R.id.sort_by_time_and_date_creation_button_SEARCHACTIVITY);

    }
}