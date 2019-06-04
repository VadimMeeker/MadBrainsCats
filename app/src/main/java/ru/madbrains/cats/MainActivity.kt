package ru.madbrains.cats

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCatsFromServer()

        main_button.text = getString(R.string.favourites_button)

        main_button.setOnClickListener {

            val intent = Intent(".FavouritesActivity")
            startActivity(intent)
        }
    }


    // Получение экземпляра класса API
    private fun getApi(): CatTextApi {
        val retrofit: Retrofit = Retrofit.Builder()
            // Базовая часть адреса
            .baseUrl("https://api.myjson.com")
            .build()

        // Указываем какой интерфейс используется для построения API
        val api: CatTextApi = retrofit.create(CatTextApi::class.java)
        return api
    }


    private fun setList(cats: List<Cat>) {

        val adapter = CatAdapter(cats)
        recyclerViewId.adapter = adapter

        // Определяет дополнительные параметры
        // LayoutManager отвечает за позиционирование view-компонентов в RecyclerView
        // LinearLayoutManager, который показывает данные в простом списке – вертикальном или горизонтальном
        val layoutManager = LinearLayoutManager(this)
        recyclerViewId.layoutManager = layoutManager
    }


    // Функция-парсер
    private fun parseResponse(responseText: String?): List<Cat> {
        // Создаем пустой список объектов класса Cat
        val catList: MutableList<Cat> = mutableListOf()
        // Преобразуем текст ответа сервера в JSON массива
        val jsonArray = JSONArray(responseText)
        // В цикле по кол-ву элементаов массива JSON объектов
        for (index: Int in 0 until jsonArray.length()) {
            // Получаем каждый элемент в виде JSON объекта
            val jsonObject: JSONObject = jsonArray.getJSONObject(index)
            // Получаем значение параметра text
            val catText: String = jsonObject.getString("text")
            // Создаем объект класса Cat с вышеполученными параметрами
            val catId: String = jsonObject.getString("id")
            // Создаем объект класса Cat с вышеполученными параметрами
            val cat = Cat(catText, catId)
            catList.add(cat)
        }
        return catList
    }

    // Обработчик результата выполнения запроса
    private val callback: Callback<ResponseBody> = object : Callback<ResponseBody> {

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText: String? = response.body()?.string()
            Log.d("responseTag", responseText)
            setList(parseResponse(responseText))

        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            t.printStackTrace()
        }

    }

    private fun getCatsFromServer() {
        getApi().getCats().enqueue(callback)
    }
}
/*
    // функция инициализации Realm
    private fun initRealm(){
        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            // при изменении конфигурации, БД будет пересоздаваться
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }

    // сохранение загруэенных фактов о котах в БД
    fun saveIntoDB(cats: List<Cat>) {
        // получаем ссылку на БД
        val realm:Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        // сохраняем список котов в БД в транзакции
        realm.copyToRealm(cats)
        realm.commitTransaction()
    }






    // чтение из БД
    fun loadFromDB():List<Cat>{
        // получаем ссылку на БД
        val realm: Realm = Realm.getDefaultInstance()
        return realm.where(Cat::class.java).findAll()
    }

    // отображение списка из БД
    fun showListFromDB(){
        val cats: List<Cat> = loadFromDB()
        //setList(cats)
    }
*/

