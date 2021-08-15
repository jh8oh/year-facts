package dev.ohjiho.yearfacts.data.model

data class YearFact(
    val text: String,
    val found: Boolean,
    val number: Float,
    val type: String,
    val date: String?,
    val year: Int?
)