package me.kolotilov.data.local.fileSystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File

interface ImageInteractor {

    fun getFilePath(name: String): File

    fun saveImage(bitmap: Bitmap, name: String): Completable

    fun loadImage(name: String): Single<Bitmap>

    fun deleteImage(name: String): Completable
}

internal class ImageInteractorImpl(
    private val context: Context
) : ImageInteractor {

    override fun getFilePath(name: String): File {
        return File(context.filesDir, "$name.png")
    }

    override fun loadImage(name: String): Single<Bitmap> {
        return Single.fromCallable {
            val file = getFilePath(name)
            BitmapFactory.decodeFile(file.toString())
        }.observeOn(Schedulers.io())
    }

    override fun saveImage(bitmap: Bitmap, name: String): Completable {
        return Completable.fromRunnable {
            val file = getFilePath(name)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())
        }.observeOn(Schedulers.io())
    }

    override fun deleteImage(name: String): Completable {
        return Completable.fromRunnable {
            val file = getFilePath(name)
            file.delete()
        }.observeOn(Schedulers.io())
    }
}