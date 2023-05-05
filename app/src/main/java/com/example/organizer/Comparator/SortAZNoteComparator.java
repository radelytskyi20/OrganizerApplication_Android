package com.example.organizer.Comparator;

import com.example.organizer.Model.Note;

import java.util.Comparator;
import java.util.List;

public class SortAZNoteComparator implements Comparator<Note>
{
    @Override
    public int compare(Note note1, Note note2) {
        return note1.getTitle().compareTo(note2.getTitle());
    }
}
