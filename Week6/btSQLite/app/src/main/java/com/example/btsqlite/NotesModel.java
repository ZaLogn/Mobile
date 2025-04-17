package com.example.btsqlite;

import java.io.Serializable;

public class NotesModel implements Serializable {
    private int IdNote;
    private String NameNote;

    public NotesModel(int idNote, String nameNote) {
        this.IdNote = idNote;
        this.NameNote = nameNote;
    }

    public int getIdNote() {
        return IdNote;
    }

    public void setIdNote(int idNote) {
        this.IdNote = idNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    public void setNameNote(String nameNote) {
        this.NameNote = nameNote;
    }
}
