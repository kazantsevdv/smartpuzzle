package com.kazantsev.home_screen

sealed class BlankEvent {
//    data class Order(val noteOrder: NoteOrder): BlankEvent()
//    data class DeleteNote(val note: Note): BlankEvent()
    object RestoreNote: BlankEvent()
    object ToggleOrderSection: BlankEvent()
}
