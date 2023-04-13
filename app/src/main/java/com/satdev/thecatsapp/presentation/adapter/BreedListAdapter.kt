package com.satdev.thecatsapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.databinding.BreedListItemBinding
import com.squareup.picasso.Picasso

class BreedListAdapter (private val breedFilterListener: BreedFilterListener) : RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>(), Filterable {
    interface BreedFilterListener{
        fun onFilterResult(count:Int)
    }
    private var breedList = arrayListOf<Breed>()
    private var breedListFull = arrayListOf<Breed>()

    fun setBreedList(list:List<Breed>){
        breedList.clear()
        breedList.addAll(list)
        breedListFull = ArrayList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = BreedListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breedList.get(position))
    }

    override fun getItemCount(): Int = breedList.size

    override fun getFilter(): Filter = itemFilter

    private val itemFilter:Filter = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList: ArrayList<Breed> = ArrayList()
            if (p0 == null || p0.length == 0) {

                filteredList.addAll(breedListFull)
            } else {
                val pattern = p0.toString().lowercase().trim { it <= ' ' }
                for (item in breedListFull) {
                    try {
                        if (item.name != null && item.name!!.lowercase().trim().contains(pattern)) {
                            filteredList.add(item)

                        }
                    } catch (e: Exception) {
                        Log.d("error", "performFiltering: "+e.message)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            breedList.clear()
            breedList.addAll(p1?.values as Collection<Breed>)
            breedFilterListener.onFilterResult(p1.count)
            notifyDataSetChanged()
        }
    }


    inner class BreedViewHolder(private val binding: BreedListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(item: Breed){

            binding.catName.setText(item.name)
            Picasso.get().load("https://cdn2.thecatapi.com/images/${item.referenceImageId}.jpg").into(binding.catImage)
            binding.catCountry.setText(item.origin)
            binding.catIq.setText(item.intelligence.toString())
        }

        override fun onClick(p0: View?) {
        }
    }
}