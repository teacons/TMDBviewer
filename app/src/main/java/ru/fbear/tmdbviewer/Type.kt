package ru.fbear.tmdbviewer

enum class Type(val string: String, val title: Int) {
    Movie("movie", R.string.movies),
    TV("tv", R.string.tv)
}