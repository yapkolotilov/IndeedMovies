package me.kolotilov.indeedmovies.di

import dagger.Module
import dagger.Provides
import me.kolotilov.logic.QrDecoder
import me.kolotilov.logic.QrDecoderImpl

@Module
class LogicModule {

    @Provides
    fun provideQrDecoder(): QrDecoder {
        return QrDecoderImpl()
    }
}