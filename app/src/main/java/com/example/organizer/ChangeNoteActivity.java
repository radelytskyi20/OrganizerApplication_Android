package com.example.organizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class ChangeNoteActivity extends AppCompatActivity {

    EditText titleOutputEditText, descriptionOutputEditText;
    Button updateNoteInfoButton, deleteNoteButton;
    DBHelper dbHelper;
    Note currentNote;
    int currentNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_change);
        getSupportActionBar().setTitle("Редагування");

         currentNoteId = Integer.parseInt(getIntent().getStringExtra("id"));

        titleOutputEditText = (EditText) findViewById(R.id.title_output_NOTEDETAILSACTIVITY);
        descriptionOutputEditText = (EditText) findViewById(R.id.description_output_NOTEDETAILSACTIVITY);
        updateNoteInfoButton = (Button) findViewById(R.id.update_note_button_NOTEDETAILSACTIVITY);
        deleteNoteButton = (Button) findViewById(R.id.delete_note_button_NOTEDETAILSACTIVITY);
        dbHelper = new DBHelper(this);

        currentNote = dbHelper.findNoteById(currentNoteId);

        DisplayInfo();


        updateNoteInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInfo();
            }
        });

        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteNote();
            }
        });

    }
    private void DisplayInfo()
    {
        if (currentNote != null)
        {
            titleOutputEditText.setText(currentNote.getTitle());
            descriptionOutputEditText.setText(currentNote.getDescription());
        }
    }
    private boolean CheckDataIsEmptyOrNot(String title, String description)
    {
        return TextUtils.isEmpty(title) || TextUtils.isEmpty(description);
    }

    private void StartMainActivity()
    {
        Intent intent = new Intent(ChangeNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void DeleteNote()
    {
        if (currentNote != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Видалення");
            builder.setMessage("Ви точно бажаєте видалити цей запис?");
            builder.setPositiveButton("Так", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean result = dbHelper.deleteNote(currentNote);

                    if (result)
                    {
                        Toast.makeText(ChangeNoteActivity.this, "Запис було успішно видалено.", Toast.LENGTH_SHORT).show();
                        StartMainActivity();
                    }
                    else
                        Toast.makeText(ChangeNoteActivity.this, "Помилка під час видалення запису! Спробуйте будь ласка пізніше.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ChangeNoteActivity.this, "Відміна дії.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        }
    }
    private void UpdateInfo()
    {
        if (currentNote != null)
        {
            String title = titleOutputEditText.getText().toString();
            String description = descriptionOutputEditText.getText().toString();

            if (!CheckDataIsEmptyOrNot(title, description))
            {

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String formattedTime = timeFormat.format(currentTime);
                String formattedDate = dateFormat.format(currentTime);

                Note noteToUpdate = new Note(
                        currentNote.getId(),
                        title,
                        description,
                        formattedTime,
                        formattedDate
                );

                boolean result = dbHelper.updateNote(noteToUpdate);

                if (result)
                {
                    Toast.makeText(ChangeNoteActivity.this, "Інформація успішно оновлена.", Toast.LENGTH_SHORT).show();
                    StartMainActivity();
                }
                else
                    Toast.makeText(ChangeNoteActivity.this, "Помилка під час оновлення! Спробуйте будь ласка пізніше.", Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(ChangeNoteActivity.this, "Помилка! Заповніть усі поля.", Toast.LENGTH_SHORT).show();
        }
    }
}