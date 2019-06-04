package ru.madbrains.cats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.cat_item.*
import kotlinx.android.synthetic.main.second_activity.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity() {

    var FactId : String? = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        setupActionBar()

        progressBar.visibility = ProgressBar.VISIBLE

        getImageFromServer()

        setText()



        add_del_button.setOnClickListener{



        }


    }

    fun setupActionBar(){
        supportActionBar?.apply{
            // включаем отображение кнопки назад
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

            //устанавливаем заголовок
            title = "Detail"
        }
    }

    override fun onSupportNavigateUp():Boolean {
        // при нажати кнопки назад Activity будет закрываться
        finish()
        return true
    }



    // Получение экземпляра класса API
    private fun getApi() : CatImageApi{
        val retrofit: Retrofit = Retrofit.Builder()
            // Базовая часть адреса
            .baseUrl("https://api.thecatapi.com")
            .build()

        // Указываем какой интерфейс используется для построения API
        val api: CatImageApi = retrofit.create(CatImageApi::class.java)
        return api
    }

    // Функция-парсер
    private fun parseResponse(responseText: String?): String?{

        // Преобразуем текст ответа сервера в JSON массива
        val jsonArray = JSONArray(responseText)
        // Получаем каждый элемент в виде JSON объекта
        val jsonObject: JSONObject = jsonArray.getJSONObject(0)
        // Получаем значение параметра URL
        return jsonObject.getString("url")

    }

    // Обработчик результата выполнения запроса
    private val callback : Callback<ResponseBody> = object : Callback<ResponseBody> {

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText: String? = response.body()?.string()
            Log.d("responseTag", responseText)

            Glide.with(this@DetailActivity).load(parseResponse(responseText)).into(imageView)
            progressBar.visibility = ProgressBar.INVISIBLE
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            t.printStackTrace()
        }

    }

    private fun getImageFromServer(){
        getApi().getCats().enqueue(callback)
    }


    fun setText(){
        intent?.extras?.getString("FACT_TEXT_TAG").let{
            textViewId_second.text = it
        }

    }



}