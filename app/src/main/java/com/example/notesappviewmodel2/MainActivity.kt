package com.example.notesappviewmodel2

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesappviewmodel2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var contect: Context
    val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    var rvAdapter= RVAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contect = applicationContext
        mainViewModel.getNotes().observe(this,{
           books-> rvAdapter.update(books)
        })
        binding.btnAddNote.setOnClickListener {
            val note = binding.edNote.text.toString()
            mainViewModel.addNote(note)
            binding.edNote.text.clear()
        }

    }
    fun alertDialog(id:String,note:String){

        val dialogBuild= AlertDialog.Builder(this)
        val lLayout= LinearLayout(this)
        val tvAlert= TextView(this)
        val edAlert= EditText(this)
        tvAlert.text=" Update Note  "
        edAlert.setSingleLine()
        edAlert.setText(note)
        lLayout.addView(tvAlert)
        lLayout.addView(edAlert)
        lLayout.setPadding(50,40,50,10)

        dialogBuild.setNegativeButton("cancel", DialogInterface.OnClickListener { _, _ ->

        })
        dialogBuild.setPositiveButton("save", DialogInterface.OnClickListener { _, _ ->
            val noteUpdete=edAlert.text.toString()
            if (noteUpdete.isNotEmpty()){
                mainViewModel.updateNote(id, noteUpdete)
            }

        })
        val aler=dialogBuild.create()
        aler.setView(lLayout)
        aler.show()
    }
}