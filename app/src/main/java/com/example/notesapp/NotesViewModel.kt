package com.example.notesapp
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).noteDao()
    private val repo = NotesRepository(dao)

    val notes: StateFlow<List<Note>> = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addNote(title: String) {
        viewModelScope.launch { repo.insert(title) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { repo.delete(note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { repo.update(note) }
    }
}