package com.deepak.knote.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes")
    fun getAllNotesList(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): LiveData<List<Note>>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}