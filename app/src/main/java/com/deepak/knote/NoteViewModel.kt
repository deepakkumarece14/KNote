package com.deepak.knote

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.deepak.knote.db.Note
import com.deepak.knote.db.NotesRepository

public class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: NotesRepository = NotesRepository(application)
    private var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun getAllNotes() : LiveData<List<Note>> = allNotes

    fun getNoteById(id: Int) : LiveData<List<Note>> = repository.getNoteById(id)

    fun insert(note: Note) = repository.insertNote(note)

    fun update(note: Note) = repository.updateNote(note)

    fun delete(note: Note) = repository.deleteNote(note)

}