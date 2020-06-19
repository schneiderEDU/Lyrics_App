/*
    App Name: Lyrics
    File Name: data/Lyric.kt
    Build Time: 19.06.2020 - 12:08
    Version: 0.1
    Author: Christian Schneider (https://github.com/schneiderEDU)
    Description: data class to create objects of the type Lyric, which contains the artist (artist), songname (song) and a part of the lyrics (lyricLine)
 */

package it.schneideredu.lyrics.data

data class Lyric(
    val lyricLine : String,
    val artist : String,
    val song : String
) {
}