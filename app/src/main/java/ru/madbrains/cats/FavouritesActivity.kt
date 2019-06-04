package ru.madbrains.cats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class FavouritesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_button.text = getString(R.string.facts_button)

        main_button.setOnClickListener {

            finish()

        }

    }


    private fun setList(cats: List<Cat>){

        val adapter = CatAdapter(cats)
        recyclerViewId.adapter = adapter

        // Определяет дополнительные параметры
        // LayoutManager отвечает за позиционирование view-компонентов в RecyclerView
        // LinearLayoutManager, который показывает данные в простом списке – вертикальном или горизонтальном
        val layoutManager = LinearLayoutManager(this)
        recyclerViewId.layoutManager = layoutManager
    }

}