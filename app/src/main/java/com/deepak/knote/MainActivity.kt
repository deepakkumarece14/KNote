package com.deepak.knote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.deepak.knote.db.MyNoteDatabase
import com.deepak.knote.db.Note
import com.deepak.knote.db.NotesRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

const val NOTE_ID = "NOTE_ID"
const val NOTE_TITLE = "NOTE_TITLE"
const val NOTE_CONTENT = "NOTE_CONTENT"

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: KNoteAdapter
    private lateinit var noteList : List<Note>
    private lateinit var liveNoteList : LiveData<List<Note>>
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        noteViewModel.getAllNotes().observe(this, Observer {
            toast("noteList changed")
        })

        liveNoteList = NotesRepository(applicationContext).getAllNotes()
        noteList = NotesRepository(applicationContext).getAllNotesList()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.hasFixedSize()
        adapter = KNoteAdapter(noteList) { note -> onItemClick(note) }
        recyclerView.adapter = adapter

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                if (direction == ItemTouchHelper.RIGHT) {
                    val swipedNote = viewHolder.itemView.tag as Note
                    val id = swipedNote.id
                    swipedNote.id = id
                    adapter.removeNote(position)
                    NotesRepository(applicationContext).deleteNote(swipedNote)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        fab.onClick { startActivity<NoteActivity>() }

    }

    private fun onItemClick(note: Note?) {
        val id = note?.id
        val title = note?.noteTitle.toString()
        val content = note?.noteContent.toString()

        startActivity<UpdateNoteActivity>(NOTE_ID to id,NOTE_TITLE to title,NOTE_CONTENT to content)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyNoteDatabase.destroyInstance()
    }

}