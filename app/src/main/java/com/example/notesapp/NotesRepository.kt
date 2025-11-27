package com.example.notesapp
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val dao: NoteDao) {
    fun getAll(): Flow<List<Note>> = dao.getAll()

    suspend fun insert(title: String) {
        val t = title.trim()
        if (t.isNotEmpty()) dao.insert(Note(title = t))
    }

    suspend fun update(note: Note) = dao.update(note)
    suspend fun delete(note: Note) = dao.delete(note)
}