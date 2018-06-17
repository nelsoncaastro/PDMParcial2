package me.nelsoncastro.pdmparcial2.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.support.v4.widget.SwipeRefreshLayout
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

    fun putUp2date(auth: String, Refreshy: SwipeRefreshLayout){
        mRepository!!.uptodateNouvelles(auth, Refreshy)
    }

    fun getAllNouvellesFavoris(): LiveData<List<Nouvelle>> = mRepository!!.getAllFavoris()

    fun getAllNouvellesByJeux(jeux: String): LiveData<List<Nouvelle>> = mRepository!!.getAllByJeux(jeux)

    fun getAllNouvellesByTitre(titre: String): LiveData<List<Nouvelle>> = mRepository!!.getAllByTitre(titre)

    fun setFavoris(value: Int, id: String) = mRepository!!.setFavoris(value, id)

}