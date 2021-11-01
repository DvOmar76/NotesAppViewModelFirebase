package com.example.notesappviewmodel2

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application):AndroidViewModel(application) {
    val app=application
     val db= Firebase.firestore
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun getNotes():LiveData<List<Note>>{
        db.collection("Notes")
            .get()
            .addOnSuccessListener { outPut->
                val TempNote=ArrayList<Note>()
                for (note in outPut){
                    note.data.map {
                            (key,value)->
                        TempNote.add(Note(note.id,value.toString()))
                    }
                }
                notes.postValue(TempNote)
            }
        return notes
    }
    fun addNote(notet:String){
        if (notet.isNotEmpty())
        {
            val note = hashMapOf(
            "note" to "$notet",
        )
            db.collection("Notes")
                .add(note)
                .addOnSuccessListener {
                    Toast.makeText(app, "note is added", Toast.LENGTH_SHORT).show()
                    getNotes()
                }
                .addOnFailureListener{
                    Toast.makeText(app, "error", Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            Toast.makeText(app, "please enter text", Toast.LENGTH_SHORT).show()
        }

    }
    fun updateNote(id:String,noteNew:String){
        db.collection("Notes")
            .get()
            .addOnSuccessListener {
                outPut->
                for (note in outPut){
                    if (id==note.id){
                        db.collection("Notes").document(id).update("note",noteNew)
                        getNotes()

                    }
                }
            }

    }
    fun deleteNote(id:String){
        db.collection("Notes")
            .get()
            .addOnSuccessListener {
                output->
                for (note in output){
                    if (note.id==id){
                        db.collection("Notes").document(id).delete()
                        getNotes()

                    }
                }
            }
    }



}