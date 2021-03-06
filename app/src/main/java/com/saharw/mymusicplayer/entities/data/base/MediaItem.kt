package com.saharw.mymusicplayer.entities.data.base

import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import java.io.Serializable

/**
 * Created by saharw on 09/05/2018.
 */

// content provider columns
const val COLUMN_ID = MediaStore.MediaColumns._ID // int
const val COLUMN_DATA = MediaStore.Audio.AudioColumns.DATA // text (string)
const val COLUMN_TITLE = MediaStore.Audio.AudioColumns.TITLE // text (string)
const val COLUMN_DISPLAY_NAME = MediaStore.Audio.AudioColumns.DISPLAY_NAME // text (string)
const val COLUMN_DURATION = MediaStore.Audio.AudioColumns.DURATION // Int(long), ms
const val COLUMN_ARTIST_ID = MediaStore.Audio.AudioColumns.ARTIST_ID // Int (long)
const val COLUMN_ARTIST = MediaStore.Audio.AudioColumns.ARTIST// text (string)
const val COLUMN_IS_MUSIC = MediaStore.Audio.AudioColumns.IS_MUSIC // Int (boolean)
const val COLUMN_SIZE = MediaStore.Audio.AudioColumns.SIZE // int(long), bytes
const val COLUMN_ALBUM_ID = MediaStore.Audio.AudioColumns.ALBUM_ID // int(long)
const val COLUMN_ALBUM = MediaStore.Audio.AudioColumns.ALBUM // text(string)

// values
const val VALUE_IS_MUSIC = 1

open class MediaItem(
        val _id : Long,
        val artistId : Long,
        val artist : String,
        val albumId: Long,
        val album : String,
        val dataPath: String,
        val name: String,
        val type: ItemType,
        val duration: Long,
        val sizeBytes : Long) : Serializable {

    companion object {
        val TAG = "MediaItem"
        fun createFromCursor(cursor: Cursor): MediaItem? {
            var item :MediaItem? = null
            try {
                var id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                var artistId = cursor.getLong(cursor.getColumnIndex(COLUMN_ARTIST_ID))
                var artist = cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST))
                var albumId = cursor.getLong(cursor.getColumnIndex(COLUMN_ALBUM_ID))
                var album = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM))
                var data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                var type = if(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_MUSIC)) == VALUE_IS_MUSIC)  ItemType.FileMusic else ItemType.Unknown
                var duration = cursor.getLong(cursor.getColumnIndex(COLUMN_DURATION))
                var sizeBytes = cursor.getLong(cursor.getColumnIndex(COLUMN_SIZE))
                item = MediaItem(id, artistId, artist, albumId, album, data, name, type, duration, sizeBytes)
            }catch (t: Throwable){
                Log.e(TAG, "createFromCursor: error creating item from cursor!", t)
            }
            return item
        }
    }

    override fun toString(): String {
        var sb = StringBuilder()
        sb.append("mediaItem{")
        sb.append(" id: $_id, ")
        sb.append("artist: $artist, ")
        sb.append("data: $dataPath, ")
        sb.append("name: $name, ")
        sb.append("type: $type, ")
        sb.append("duration: $duration")
        sb.append("sizeBytes: $sizeBytes")
        sb.append("}")
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if(other is MediaItem && other != null){
            return other._id == this._id
        }else {
            return false
        }
    }
}