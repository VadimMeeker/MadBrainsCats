package ru.madbrains.cats

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


// элемент списка
class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.textViewId)

    fun bind(cat: Cat){
        textView.text = cat.text; // загружаем текст в TextView

        itemView.setOnClickListener{
            openDetailActivity(itemView.context, cat.text)
        }
    }

    fun openDetailActivity(context: Context, catFactText: String){
        // создаем объект Intent для запуска текущей Activity
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("FACT_TEXT_TAG", catFactText)
        // запускаем Activity
        context.startActivity(intent)
    }

}

// адаптер - управляет элементами
class CatAdapter(private val cats: List<Cat>) : RecyclerView.Adapter<CatsViewHolder>(){

    // Создает новый объект ViewHolder всякий раз, когда RecyclerView нуждается в этом.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val rootView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cat_item, parent, false)

        return CatsViewHolder(rootView)
    }

    // Возвращает общее количество элементов списка
    override fun getItemCount(): Int{
        return cats.size
    }

    // Принимает объект ViewHolder и устанавливает необходимые данные для соответствующей строки во view-компоненте
    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bind(cats.get(position))
    }
}