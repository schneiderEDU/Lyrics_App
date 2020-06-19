/*
    App Name: Lyrics
    File Name: LyricsActivity.kt
    Build Time: 19.06.2020 - 12:08
    Version: 0.1
    Author: Christian Schneider (https://github.com/schneiderEDU)
    Description: Stage 1 - Basic App (without any design patterns)
    Perquisites: basics in procedural programming, basic understanding of oop
    Learning:
        - data classes: creating data classes with attributes
        - lists: creating lists from objects
        - text views: setting text programmatically
        - buttons: setting onClick listeners
     Additional Information:
        Please have a look at both build.gradle files and compare them to your generated build.gradle files from Android Studio.
        Also have a look at the comments in every ktx file (there only 2) in the project.
 */
package it.schneideredu.lyrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import it.schneideredu.lyrics.data.Lyric

class LyricsActivity : AppCompatActivity() {
    /*
        Private attributes for the LyricsActivity class. These are only accessible within this class (due to the keyword private)
            index - Variable of type Int; used to get a specific item from lyricsList
            lyricsList - (immutable) List of Lyric objects; used at the moment to store our data; will be obsolete in future versions of the app, but for now it is required
            txt* - TextView objects; store the references to the TextViews in the layout file
            btn* - Button objects; store the references to the Buttons in the layout file

        All references to the layout file cannot be instantiated at declaration due to the circumstance that the layout will be set in the onCreate() method.
        Therefore we used the keyword lateinit to assign values later in the onCreate() method, after the layout is set.
     */
    private var index : Int = 0
    private var lyricsList : List<Lyric> = listOf<Lyric>(
        Lyric(
            "I turn the night to day\n" +
                    "I'm feeling so alive\n" +
                    "Cause I'm in a harder state of mind",
            "D-Block & S-Te-Fan and DJ Isaac",
            "Harder State Of Mind"
        ),
        Lyric(
            "You're taking me high\n" +
                    "You lift me up until I'm touching heaven\n" +
                    "Together we fly\n" +
                    "You give me love, reach for the sky",
            "Adaro & Digital Punk",
            "Reach For The Sky"
        ),
        Lyric(
            "We stay standin' even in tough times\n" +
                    "A big step up, fight in the front line\n" +
                    "I am a rebel and if you feel the same\n" +
                    "Come battle with us, step into the game",
            "KELTEK & B-Front",
            "Step Into The Game"
        )
    )

    private lateinit var txtLyric : TextView
    private lateinit var txtArtist : TextView
    private lateinit var txtSong : TextView
    private lateinit var btnNext : Button
    private lateinit var btnPrev : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)
        /* Calls the hideSystemUi() method defined at the end of this file */
        hideSystemUi()
        /* Assigns ui elements by their id to the respective (lateinit) variables*/
        txtLyric = findViewById(R.id.txtLyricsLine)
        txtArtist = findViewById(R.id.txtArtist)
        txtSong = findViewById(R.id.txtSong)
        btnPrev = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        /* Calls showLyric() method to display the first list entry from the lyricsList */
        showLyric()

        /* Setting anonymous functions as onClickListeners
                btnPrev decreases the index by 1 on button click, it the index is greater than 0
                btnNext increases the index by 1 on button click, if the index is lesser than the size of lyricList-1 (index starts at 0!)
           If-Statements prevent to get exceptions by accessing list elements that doesn't exist.
           Both onClickListeners call the showLyric() method after manipulating the index value
        */
        btnPrev.setOnClickListener {
            if(index > 0) {
                index--
                showLyric()
            }
        }

        btnNext.setOnClickListener{
            if(index < lyricsList.size-1) {
                index++
                showLyric()
            }
        }
    }
    /*
        Sets the TextView texts to the respective values from the current object from the lyricsList;
        calls the showHideButton() method
     */
    private fun showLyric() {
        txtLyric.text = lyricsList[index].lyricLine
        txtArtist.text = lyricsList[index].artist
        txtSong.text = lyricsList[index].song
        showHideButton()
    }

    /*
        Determines which Buttons must be show to navigate through the lyricsList objects.
        The when() function is the ktx replacement for the switch operator in C-like languages.
        Hiding buttons prevents the user from trying to navigate out of the list bounds via the ui
     */
    private fun showHideButton() {
        when(index) {
            0 -> {
                btnPrev.visibility = INVISIBLE
                btnNext.visibility = if(lyricsList.size > 1) VISIBLE else INVISIBLE
            }
            lyricsList.size - 1 -> {
                btnPrev.visibility = if(lyricsList.size > 1) VISIBLE else INVISIBLE
                btnNext.visibility = INVISIBLE
            }
            else -> {
                btnPrev.visibility = VISIBLE
                btnNext.visibility = VISIBLE
            }
        }
    }

    /*
        Is called when app comes from background to the foreground; used to re-enable fullscreen mode
     */
    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    /*
        Is called when the focus changes; used to keep fullscreen mode enabled
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUi()
    }

    /*
        Hide status and navigation bars, app will be in fullscreen mode
     */
    private fun hideSystemUi() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

}