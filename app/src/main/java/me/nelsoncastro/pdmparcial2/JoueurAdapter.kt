package me.nelsoncastro.pdmparcial2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.nelsoncastro.pdmparcial2.entities.Joueur

class JoueurAdapter(private val contexte: Context): RecyclerView.Adapter<JoueurAdapter.JoueurViewHolder>() {

    private var mJoueur: List<Joueur>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoueurViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_small, parent, false)
        return  JoueurViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (mJoueur != null) mJoueur!!.size else 0
    }

    override fun onBindViewHolder(holder: JoueurViewHolder, position: Int) {
        val curry = mJoueur!![position]
        holder.titlesmall.text = curry.name
        holder.descsmall.text = curry.biografia
        Picasso.with(holder.itemView.context)
                .load(curry.avatar)
                .error(R.drawable.himym)
                .into(holder.imgsmall)
    }

    internal fun setJoueur(jou: List<Joueur>){
        mJoueur = jou
        notifyDataSetChanged()
    }

    class JoueurViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal val imgsmall: ImageView = itemView.findViewById(R.id.image_nouvelle_small)
        internal val titlesmall: TextView = itemView.findViewById(R.id.title_nouvelle_small)
        internal val descsmall: TextView = itemView.findViewById(R.id.desc_nouvelle_small)
    }
}