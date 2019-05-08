package com.example.admin.homework_w5

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import java.io.Serializable

class AdapterMovie(val context : Context, val item : ArrayList<MovieModel>) : RecyclerView.Adapter<ViewHolderMovie>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderMovie {
        return ViewHolderMovie(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size;
    }

    override fun onBindViewHolder(holder: ViewHolderMovie, i: Int) {
        Glide.with(context)
                .load(Constant.getImgUrl() + item[i].poster_path)
                .into(holder.imgAvatar)
        holder.txtName.text = item[i].title
        holder.txtDescription.text = item[i].overview
        if(item[i].video!!) {
            holder.imgPlay.setVisibility(View.VISIBLE)
        }
        holder.setListenerEventClick(listenerEventClick)
    }
    private val listenerEventClick = object : IClickOnFragment {
        override fun itemClick(position: Int) {
            val movieModel : MovieModel  = item[position]
            val intent = Intent(context, DetailScreen::class.java)
            intent.putExtra("data", movieModel)
            context.startActivity(intent)
        }
    }

}

class ViewHolderMovie(itemView: View) : RecyclerView.ViewHolder(itemView){
    var iClickOnFragment : IClickOnFragment? = null
    var imgAvatar = itemView.imgAvatar
    var imgPlay = itemView.imgPlay
    var txtName = itemView.txtName
    var txtDescription = itemView.txtDesctiption
    fun setListenerEventClick(IClickOnFragment : IClickOnFragment) {
        this.iClickOnFragment = IClickOnFragment
    }
    init {
        itemView.setOnClickListener {
            iClickOnFragment?.itemClick(adapterPosition)
        }
    }
}