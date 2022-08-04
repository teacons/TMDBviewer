package ru.fbear.tmdbviewer.model

interface SearchListEntry {
    val id: Int
    val posterPath: String?
    val name: String
    val voteAverage: Float
}