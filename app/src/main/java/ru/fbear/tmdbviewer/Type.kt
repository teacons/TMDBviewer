package ru.fbear.tmdbviewer

enum class Type(val string: String, val title: Int, val favoriteTitle: Int) {
    Movie("movie", R.string.movies, R.string.favorite_movies),
    TV("tv", R.string.tv, R.string.favorite_tv)
}