package me.nelsoncastro.pdmparcial2.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.nelsoncastro.pdmparcial2.entities.Categorie
import me.nelsoncastro.pdmparcial2.repositories.CategorieRepository

class CategorieViewModel(application: Application): AndroidViewModel(application) {
    internal var mRepository: CategorieRepository? = null
    internal var allCategories: LiveData<List<Categorie>>? = null

    init {
        mRepository = CategorieRepository(application)
        allCategories = mRepository?.getAll()
    }

    fun getAllCategories(): LiveData<List<Categorie>> = allCategories!!

    fun putUp2date(auth: String) {
        mRepository!!.uptodateCategories(auth)
    }
}