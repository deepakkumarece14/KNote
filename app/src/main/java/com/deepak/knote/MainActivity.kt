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
//        database = MyNoteDatabase.getInstance(this@MainActivity)
        liveNoteList = NotesRepository(applicationContext).getAllNotes()
//        noteList = NotesRepository(applicationContext).getAllNotes()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(itemDecoration)
        adapter = KNoteAdapter(noteList)            //TODO(" noteList is not initialized")
        recyclerView.adapter = KNoteAdapter(noteList)
        recyclerView.hasFixedSize()

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                if (direction == ItemTouchHelper.LEFT) {
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

    private fun refreshRecyclerView() {
        liveNoteList = NotesRepository(applicationContext).getAllNotes()
        adapter = KNoteAdapter(noteList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyNoteDatabase.destroyInstance()
    }

}