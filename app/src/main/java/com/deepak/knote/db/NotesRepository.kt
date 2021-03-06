package com.deepak.knote.db

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.doAsync

class NotesRepository(context: Context) {
    private var database : MyNoteDatabase? = MyNoteDatabase.getInstance(context)
    private lateinit var liveNoteList : LiveData<List<Note>>
    private lateinit var noteList : List<Note>

    fun getAllNotes(): LiveData<List<Note>> {
        runBlocking {
            async(CommonPool) {
                liveNoteList = database?.noteDao()?.getAllNotes()!!
                return@async liveNoteList
            }.await()
        }
        return liveNoteList
    }

    fun getAllNotesList(): List<Note> {
        runBlocking {
            async(CommonPool) {
                noteList = database?.noteDao()?.getAllNotesList()!!
                return@async noteList
            }.await()
        }
        return noteList
    }

    fun getNoteById(id: Int): LiveData<List<Note>> {
        runBlocking {
            async(CommonPool) {
                liveNoteList = database?.noteDao()?.getNoteById(id)!!
                return@async liveNoteList
            }.await()
        }
        return liveNoteList
    }

    fun insertNote(note: Note) = doAsync { database?.noteDao()?.insertNote(note) }

    fun updateNote(note: Note) = doAsync { database?.noteDao()?.updateNote(note) }

    fun deleteNote(note: Note) = doAsync { database?.noteDao()?.deleteNote(note) }
}