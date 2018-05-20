package com.saharw.mymusicplayer.presentation.fragments.base.dagger

import android.support.v7.app.AppCompatActivity
import com.saharw.mymusicplayer.entities.data.base.MediaItem
import com.saharw.mymusicplayer.presentation.activities.files.mvp.FilesModel
import com.saharw.mymusicplayer.presentation.activities.files.mvp.FilesPresenter
import com.saharw.mymusicplayer.presentation.activities.files.mvp.FilesView
import com.saharw.mymusicplayer.presentation.base.IModel
import com.saharw.mymusicplayer.presentation.base.IPresenter
import com.saharw.mymusicplayer.presentation.base.IView
import com.saharw.mymusicplayer.service.MusicService
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference
import javax.inject.Singleton

/**
 * Created by saharw on 10/05/2018.
 */
@Module
class FilesActivityModule(private val activity: AppCompatActivity,
                          private val mainLayoutId: Int,
                          private val itemLayoutId: Int,
                          private val mediaItems : Collection<MediaItem>?,
                          private val musicServWeakRef : WeakReference<MusicService>){

    @Provides
    @Singleton
    fun provideView(): IView{
        return FilesView(activity, mainLayoutId, itemLayoutId, mediaItems)
    }

    @Provides
    @Singleton
    fun provideModel(): IModel {
        return FilesModel()
    }

    @Provides
    @Singleton
    fun providePresenter(view: IView, model : IModel): IPresenter {
        return FilesPresenter(view, model, mediaItems, musicServWeakRef)
    }
}