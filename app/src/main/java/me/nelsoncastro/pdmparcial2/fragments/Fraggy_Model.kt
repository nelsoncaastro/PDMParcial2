package me.nelsoncastro.pdmparcial2.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.ViewPagerAdapter

class Fraggy_Model: Fragment() {

    private val CLE = "LLAVE"
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = it.getString(CLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_modelo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabbything = view.findViewById<TabLayout>(R.id.tabby)
        val viewything = view.findViewById<ViewPager>(R.id.viewy)
        val adapter = ViewPagerAdapter(view.context,childFragmentManager, type!!)
        viewything.adapter = adapter
        tabbything.setupWithViewPager(viewything)
    }

    companion object {
        fun newInstance(type: String) =
                Fraggy_Model().apply {
                    arguments = Bundle().apply {
                        putString(CLE, type)
                    }
                }
    }
}