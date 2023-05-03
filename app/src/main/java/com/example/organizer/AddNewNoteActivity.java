package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizer.Model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewNoteActivity extends AppCompatActivity {

    EditText titleInputEditText, descriptionInputEditText;
    Button addNewNoteButton;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        getSupportActionBar().setTitle("Додати запис");

        titleInputEditText = (EditText) findViewById(R.id.title_input_ADDNEWNOTEACTIVITY);
        descriptionInputEditText = (EditText) findViewById(R.id.description_input_ADDNEWNOTEACTIVITY);
        addNewNoteButton = (Button) findViewById(R.id.add_new_note_button_ADDNEWNOTEACTIVITY);
        dbHelper = new DBHelper(this);


        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInputEditText.getText().toString();
                String description = descriptionInputEditText.getText().toString();

                if (!CheckDataIsEmptyOrNot(title, description))
                {
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String formattedTime = timeFormat.format(currentTime);
                    String formattedDate = dateFormat.format(currentTime);

                    Note newNote = new Note(
                            title,
                            description,
                            formattedTime,
                            formattedDate

                    );

                    boolean result = dbHelper.addNote(newNote);
                    
                    if (result)
                    {
                        Toast.makeText(AddNewNoteActivity.this, "Запис успішно додано у БД.", Toast.LENGTH_SHORT).show();
                        StartMainActivity();
                    }
                    else
                        Toast.makeText(AddNewNoteActivity.this, "Помилка під час запису! Спробуйте будь ласка пізніше.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AddNewNoteActivity.this, "Помилка! Заповніть усі поля.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean CheckDataIsEmptyOrNot(String title, String description)
    {
        return TextUtils.isEmpty(title) || TextUtils.isEmpty(description);
    }

    private void StartMainActivity()
    {
        Intent intent = new Intent(AddNewNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}