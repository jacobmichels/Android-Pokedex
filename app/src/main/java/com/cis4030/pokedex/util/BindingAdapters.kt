package com.cis4030.pokedex.util

import android.os.Build
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabaseCustomMove
import com.cis4030.pokedex.database.DatabaseCustomPokemon
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.ui.pokedex_create.CreatePokemonMovelistAdapter
import com.cis4030.pokedex.ui.pokedex_create.CustomPokemonGridAdapter
import com.cis4030.pokedex.ui.pokedex_list.PokemonGridAdapter
import com.cis4030.pokedex.ui.team.TeamsListAdapater
import com.google.android.material.chip.Chip
import java.io.File

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DatabasePokemon>?) {
    val adapter = recyclerView.adapter as PokemonGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindCustomRecyclerView(recyclerView: RecyclerView, data: List<DatabaseCustomPokemon>?) {
    val adapter = recyclerView.adapter as CustomPokemonGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindTeamRecyclerView(recyclerView: RecyclerView, data: List<DatabaseTeam>?){
    val adapter = recyclerView.adapter as TeamsListAdapater
    adapter.submitList(data)
}

@BindingAdapter("imageName")
fun bindImagename(imageView: ImageView, name: String){
    val file = File(imageView.context.filesDir,name)
    val bytes = file.readBytes()
    imageView.setImageBitmap(byteArrayToBitmap(bytes))
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("app:chipChecked")
fun bindChipChecked(chip:Chip, checkedList:List<String>){
    Log.d("POKEDEX","size:${checkedList.size}")
    chip.isChecked = checkedList.contains(chip.text as String)
}

@BindingAdapter("app:checkboxChecked")
fun bindCheckboxChecked(checkBox: CheckBox,checkedList: List<Int>){
    checkBox.isChecked=checkedList.contains((checkBox.text as String).toInt())
}