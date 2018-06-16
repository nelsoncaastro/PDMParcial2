package me.nelsoncastro.pdmparcial2

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import me.nelsoncastro.pdmparcial2.fragments.Home_Fraggy
import me.nelsoncastro.pdmparcial2.fragments.Joueur_Fraggy

class ViewPagerAdapter(private val contexte: Context,fm: FragmentManager, type: String): FragmentPagerAdapter(fm) {

    private val nouvelles = Home_Fraggy.newInstance(type)
    private val joueurs = Joueur_Fraggy.newInstance(type)

    override fun getItem(position: Int): Fragment = when (position){
        0 -> nouvelles
        1 -> joueurs
        else -> nouvelles
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> contexte.getString(R.string.tab1)
            1 -> contexte.getString(R.string.tab2)
            else -> null
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}