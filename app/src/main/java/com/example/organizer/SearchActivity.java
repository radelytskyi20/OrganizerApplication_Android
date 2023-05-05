package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    EditText searchByTitleInputEditText, searchByTimeInputEditText, searchByDateInputEditText;
    Button searchByTitleButton, searchByTimeButton, searchByDateButton, sortAlphabeticalAZButton,
            sortAlphabeticalZAButton, sortByDateAndTimeButton;

    public static final String BUTTON_ID = "id";
    public static final String SEARCH_TEXT_ID = "search_text_id";
    public static final String EMPTY_STRING = "";
    public static final String SEARCH_BY_TITLE_BUTTON = "search_by_title_button";
    public static final String SEARCH_BY_TIME_BUTTON = "search_by_time_button";
    public static final String SEARCH_BY_DATE_BUTTON = "search_by_date_button";
    public static final String SORT_ALPHABETICAL_AZ_BUTTON = "sort_alphabetical_az_button";
    public static final String SORT_ALPHABETICAL_ZA_BUTTON = "sort_alphabetical_za_button";
    public static final String SORT_DATE_AND_TIME_BUTTON = "sort_date_and_time_button";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Пошук");


        searchByTitleInputEditText = (EditText) findViewById(R.id.search_by_title_input_SEARCHACTIVITY);
        searchByTimeInputEditText = (EditText) findViewById(R.id.search_by_time_input_SEARCHACTIVITY);
        searchByDateInputEditText = (EditText) findViewById(R.id.search_by_data_input_SEARCHACTIVITY);
        searchByTitleButton = (Button) findViewById(R.id.search_by_title_button_SEARCHACTIVITY);
        searchByTimeButton = (Button) findViewById(R.id.search_by_time_button_SEARCHACTIVITY);
        searchByDateButton = (Button) findViewById(R.id.search_by_date_button_SEARCHACTIVITY);
        sortAlphabeticalAZButton = (Button) findViewById(R.id.sort_A_Z_button_SEARCHACTIVITY);
        sortAlphabeticalZAButton = (Button) findViewById(R.id.sort_Z_A_button_SEARCHACTIVITY);
        sortByDateAndTimeButton = (Button) findViewById(R.id.sort_by_time_and_date_creation_button_SEARCHACTIVITY);


        searchByTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SEARCH_BY_TITLE_BUTTON, searchByTitleInputEditText.getText().toString());
            }
        });

        searchByTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidTimeFormat(searchByTimeInputEditText.getText().toString()))
                {
                    StartSearchResultActivity(SEARCH_BY_TIME_BUTTON, searchByTimeInputEditText.getText().toString());
                }
                else
                {
                    Toast.makeText(SearchActivity.this, "Помилка! Введіть коректний формат часу: HH:mm:ss", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidDateFormat(searchByDateInputEditText.getText().toString()))
                {
                    StartSearchResultActivity(SEARCH_BY_DATE_BUTTON, searchByDateInputEditText.getText().toString());
                }
                else
                {
                    Toast.makeText(SearchActivity.this, "Помилка! Введіть коректний формат дати: dd.MM.yyyy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sortAlphabeticalAZButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SORT_ALPHABETICAL_AZ_BUTTON, EMPTY_STRING);
            }
        });

        sortAlphabeticalZAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SORT_ALPHABETICAL_ZA_BUTTON, EMPTY_STRING);
            }
        });

        sortByDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SORT_DATE_AND_TIME_BUTTON, EMPTY_STRING);
            }
        });
    }


    private void StartSearchResultActivity(String clickedButton, String searchText)
    {
        if (CheckSearchText(clickedButton, searchText))
        {
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra(BUTTON_ID, clickedButton);
            intent.putExtra(SEARCH_TEXT_ID, searchText);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Помилка! Введіть текст пошуку.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckSearchText(String clickedButton, String searchText)
    {
        return !TextUtils.isEmpty(searchText)  || clickedButton.equals(SORT_ALPHABETICAL_AZ_BUTTON) ||
                clickedButton.equals(SORT_ALPHABETICAL_ZA_BUTTON) || clickedButton.equals(SORT_DATE_AND_TIME_BUTTON);
    }

    public boolean isValidTimeFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public boolean isValidDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}