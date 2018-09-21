@file:Suppress("unused")

package com.deepak.knote.db

import android.content.Context
import org.jetbrains.anko.doAsync

class NotesRepository(context: Context) {
    private var database : MyNoteDatabase? = null
    private lateinit var noteList : List<Note>

    init {
        database = MyNoteDatabase.getInstance(context)
    }

    fun getAllNotes(): List<Note> {
        doAsync {
//            database = MyNoteDatabase.getInstance(context)
            noteList = database?.noteDao()?.getAllNotes()!!
        }
        return noteList
    }

    fun getNoteById(id: Int): List<Note> {
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