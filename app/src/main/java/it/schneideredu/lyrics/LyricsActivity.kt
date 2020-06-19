/*
    App Name: Lyrics
    File Name: LyricsActivity.kt
    Build Time: 19.06.2020 - 12:08
    Version: 0.2
    Author: Christian Schneider (https://github.com/schneiderEDU)
    Description: Stage 2 - Basic App with data binding
    Prerequisites: basics in procedural programming, basic understanding of oop
    Learning:
        - data binding : implement data binding in layout xml and LyricsActivity; get rid of findViewById
     Additional Information:
        Please have also a look at the layout file xml. There are some significant changes in order to use data binding
 */
package it.schneideredu.lyrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import it.schneideredu.lyrics.data.Lyric
import it.schneideredu.lyrics.databinding.ActivityLyricsBinding
import kotlinx.android.synthetic.main.activity_lyrics.*

class LyricsActivity : AppCompatActivity() {
    /*
        Private attributes for the LyricsActivity class. These are only accessible within this class (due to the keyword private)
            index - Variable of type Int; used to get a specific item from lyricsList
            lyricsList - (immutable) List of Lyric objects; used at the moment to store our data; will be obsolete in future versions of the app, but for now it is required
            binding - Object for data binding; initialized in the onCreate() method
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

    private lateinit var binding : ActivityLyricsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initializes the binding object and replaces the stock setContentView() method */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lyrics)
        /* Calls the hideSystemUi() method defined at the end of this file */
        hideSystemUi()
        /* Calls showLyric() method to display the first list entry from the lyricsList */
        showLyric()

        /* Setting anonymous functions as onClickListeners
                btnPrev decreases the index by 1 on button click, it the index is greater than 0
                btnNext increases the index by 1 on button click, if the index is lesser than the size of lyricList-1 (index starts at 0!)
           If-Statements prevent to get exceptions by accessing list elements that doesn't exist.
           Both onClickListeners call the showLyric() method after manipulating the index value
        */
        btnPrevious.setOnClickListener {
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
        Sets the TextView texts to the respective values from the current object from the lyricsList using data binding;
        calls the showHideButton() method
     */
    private fun showLyric() {
        binding.lyric = lyricsList[index]
        showHideButton()
    }

    /*
        Determines which Buttons must be show to navigate through the lyricsList objects.
        The when() function is the ktx replacement for the switch operator in C-like languages.
        Hiding buttons prevents the user from trying to navigate out of the list bounds via the ui
     */
    private fun showHideButton() {
        when(index) {
            0 -> binding.apply{
                btnPrevious.visibility = INVISIBLE
                btnNext.visibility = if(lyricsList.size > 1) VISIBLE else INVISIBLE
            }
            lyricsList.size - 1 -> binding.apply{
                btnPrevious.visibility = if(lyricsList.size > 1) VISIBLE else INVISIBLE
                btnNext.visibility = INVISIBLE
            }
            else -> binding.apply{
                btnPrevious.visibility = VISIBLE
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