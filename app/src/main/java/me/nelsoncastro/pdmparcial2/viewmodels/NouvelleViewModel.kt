package me.nelsoncastro.pdmparcial2.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import me.nelsoncastro.pdmparcial2.repositories.NouvelleRepository

class NouvelleViewModel(application: Application): AndroidViewModel(application) {
    internal var mRepository: NouvelleRepository? = null
    internal var allNouvelles: LiveData<List<Nouvelle>>? = null

    init {
        mRepository = NouvelleRepository(application)
        allNouvelles = mRepository?.getAll()
    }

    fun getAllNouvelles(): LiveData<List<Nouvelle>> = allNouvelles!!

    fun putUp2date(auth: String){
        mRepository!!.uptodateNouvelles(auth)
    }

}