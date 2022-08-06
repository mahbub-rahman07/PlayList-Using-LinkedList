package com.tenminuteschool.listtolinkedlist

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tenminuteschool.listtolinkedlist.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var currentPlayItem: PlayItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPlayItem = getAPlayList()
        setSongInfo()



        binding.nextBtn.setOnClickListener {

            if(currentPlayItem.next != null){
                currentPlayItem = currentPlayItem.next!!

                setSongInfo()
            }else {
                binding.songTitle.text = "No more song available, try previous"
                binding.artist.text = ""
            }

        }

        binding.prevBtn.setOnClickListener {

            if(currentPlayItem.prev != null){
                currentPlayItem = currentPlayItem.prev!!
                setSongInfo()
            }else {
                binding.songTitle.text = "No more song available, try next"
                binding.artist.text = ""
            }

        }


    }

    private fun setSongInfo() {
        binding.songTitle.text = "Title: ${currentPlayItem.song.name}"
        binding.artist.text = "Artist: ${currentPlayItem.song.artist}"

    }


    private fun getAPlayList(): PlayItem {

        val itemList = mutableListOf<PlayItem>()
        val songList = getSongList()

        for (i in songList.indices){
            var item:PlayItem
            if(i == 0){
                item = PlayItem(
                    songList[i]
                )
            }else {
                item = PlayItem(
                    songList[i],
                    prev = itemList[i-1]
                )
                itemList[i-1].next = item
            }
            itemList.add(item)
        }

        return itemList[0]
    }
    private fun getSongList(): List<Song> {
        val songList = mutableListOf<Song>()
        songList.add(Song("Beautiful", "Christina Aguilera"))
        songList.add(Song("Love Can Build a Bridge", "The Judds"))
        songList.add(Song("Man in the Mirror", "Michael Jackson"))
        songList.add(Song("I Will Survive", "Gloria Gaynor"))
        songList.add(Song("The Gambler", "Kenny Rogers"))
        songList.add(Song("Daughters", "John Mayer"))
        songList.add(Song("I Believe I Can Fly", "Yolanda Adams"))
        return songList
    }

    data class PlayItem(
        val song: Song,
        var next: PlayItem? = null,
        var prev: PlayItem? = null,
    )

    data class Song(
        val name:String,
        val artist:String
    )
}