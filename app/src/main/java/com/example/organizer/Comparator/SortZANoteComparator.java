package com.example.organizer.Comparator;

import com.example.organizer.Model.Note;

import java.util.Comparator;

public class SortZANoteComparator implements Comparator<Note>
{
    public int compare(Note note1, Note note2) {
        return note2.getTitle().compareTo(note1.getTitle());
    }
}
