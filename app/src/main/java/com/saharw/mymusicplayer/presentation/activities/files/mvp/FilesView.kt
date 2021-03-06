package com.saharw.mymusicplayer.presentation.activities.files.mvp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.MediaController
import com.saharw.mymusicplayer.R
import com.saharw.mymusicplayer.entities.adapters.MediaItemsAdapter
import com.saharw.mymusicplayer.entities.data.base.MediaItem
import com.saharw.mymusicplayer.entities.media.MyMediaController
import com.saharw.mymusicplayer.presentation.base.IView
import io.reactivex.subjects.PublishSubject
import java.lang.ref.WeakReference

/**
 * Created by saharw on 10/05/2018.
 */
class FilesView(private val activity: WeakReference<AppCompatActivity>,
                private val mainLayoutId: Int,
                private val itemLayoutId: Int,
                private val mediaItems: Collection<MediaItem>?,
                private val mediaController: WeakReference<MediaController>) : IView {

    private val TAG = "FilesView"

    private lateinit var mItemsList : ListView
    private lateinit var mItemsAdapter : MediaItemsAdapter

    val mOnItemClickSubject = PublishSubject.create<MediaItem>()!!

    override fun onViewCreate() {
        Log.d(TAG, "onViewCreate")
        var view = activity.get()?.layoutInflater?.inflate(mainLayoutId, null,false)
        if(view != null){
            initUIComponents(view)
        }
        activity.get()?.setContentView(view)
    }

    override fun onViewResume() {
        Log.d(TAG, "onViewResume")
    }

    override fun onViewPause() {
        Log.d(TAG, "onViewPause")
    }

    override fun onViewDestroy() {
        Log.d(TAG, "onViewDestroy")
    }

    override fun getContext(): Context {
        return activity.get()!!
    }

    private fun initUIComponents(view: View) {
        Log.d(TAG, "initUIComponents")
        mItemsList = view.findViewById(R.id.listV_files)
        mItemsAdapter = MediaItemsAdapter(activity.get()!!, mediaItems as List<MediaItem>, itemLayoutId)
        mItemsList.adapter = mItemsAdapter

        mItemsList.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
            var viewHolder =  view?.tag as MediaItemsAdapter.ViewHolder
            Log.d(TAG, "onItemClicked: position: $position, name: ${viewHolder.mDisplayName.text}, path: ${viewHolder.mMediaItem.dataPath}")
            mOnItemClickSubject.onNext(viewHolder.mMediaItem)
        }
    }

    fun showMediaController() {
        Log.d(TAG, "showMediaController")
        activity.get()?.runOnUiThread { mediaController.get()?.show() }
    }

    fun hideMediaController() {
        Log.d(TAG, "hideMediaController")
        activity.get()?.runOnUiThread { mediaController.get()?.hide() }
    }

    fun setSongTitle(songTitle: String) {
        Log.d(TAG, "setSongTitle")
        activity.get()?.runOnUiThread {(mediaController.get() as MyMediaController).setSongTitle(songTitle)}
    }
}