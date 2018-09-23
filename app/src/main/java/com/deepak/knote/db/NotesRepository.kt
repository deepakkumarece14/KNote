package com.deepak.knote.db

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.doAsync

class NotesRepository(context: Context) {
    private var database : MyNoteDatabase? = MyNoteDatabase.getInstance(context)
    private lateinit var noteList : LiveData<List<Note>>

    fun getAllNotes(): LiveData<List<Note>> {
        runBlocking {
//            database = MyNoteDatabase.getInstance(context)
            noteList = database?.noteDao()?.getAllNotes()!!
        }
        return noteList
    }

    fun getNoteById(id: Int): LiveData<List<Note>> {
        doAsync {
//            database = MyNoteDatabase.getInstance(context)
            noteList = database?.noteDao()?.getNoteById(id)!!
        }
        return noteList
    }

    fun insertNote(note: Note) {
        doAsync {
//            val note = Note(noteTitle = note.noteTitle,noteContent = note.noteContent)
//            database = MyNoteDatabase.getInstance(context)
            database?.noteDao()?.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        doAsync {
//            database = MyNoteDatabase.getInstance(context)
            database?.noteDao()?.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        doAsync {
//            database = MyNoteDatabase.getInstance(context)
            database?.noteDao()?.deleteNote(note)
        }
    }
}