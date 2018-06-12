package me.nelsoncastro.pdmparcial2.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.nelsoncastro.pdmparcial2.entities.Joueur
import me.nelsoncastro.pdmparcial2.repositories.JoueurRepository

class JoueurViewModel(application: Application): AndroidViewModel(application) {
    internal var mRepository: JoueurRepository? = null
    internal var allJoueurs: LiveData<List<Joueur>>? = null

    init {
        mRepository = JoueurRepository(application)
        allJoueurs = mRepository?.getAll()
    }

    fun getAllJoueurs(): LiveData<List<Joueur>> = allJoueurs!!

    fun putUp2date(auth: String){
        mRepository!!.uptodateJoueurs(auth)
    }
}