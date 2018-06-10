package me.nelsoncastro.pdmparcial2

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

class ViewModel(application: Application): AndroidViewModel(application) {
    internal var mRepository: Repository? = null
    internal var allNouvelles: LiveData<List<Nouvelle>>? = null

    init {
        mRepository = Repository(application)
        allNouvelles = mRepository?.getAll()
    }

    fun getAllNouvelles(): LiveData<List<Nouvelle>> {return  allNouvelles!!}

    fun putUp2date(auth: String){
        mRepository!!.uptodateNouvelles(auth)
    }

}