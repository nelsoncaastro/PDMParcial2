package me.nelsoncastro.pdmparcial2.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.nelsoncastro.pdmparcial2.entities.Etudiant
import me.nelsoncastro.pdmparcial2.repositories.EtudiantRepository

class EtudiantViewModel(application: Application): AndroidViewModel(application) {
    internal var mRepository: EtudiantRepository? = null
    internal var allEtudiants: LiveData<List<Etudiant>>? = null

    init {
        mRepository = EtudiantRepository(application)
        allEtudiants = mRepository?.getAll()
    }

    fun getAllEtudiants(): LiveData<List<Etudiant>> = allEtudiants!!

    fun putUp2date(auth: String){
        mRepository!!.uptodateEtudiants(auth)
    }
}