package com.example.listacomprapersistente

import android.app.Application
import com.example.listacomprapersistente.data.AppContainer

class ListaCompraApp : Application() {
    lateinit var appContainer : AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}