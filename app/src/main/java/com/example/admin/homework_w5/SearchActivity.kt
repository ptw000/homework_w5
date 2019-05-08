package com.example.admin.homework_w5

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.*
import java.io.IOException

class SearchActivity : AppCompatActivity() {
    private lateinit var keyword : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        keyword  = intent.getStringExtra("keyword")
        RequestData().execute()
    }
    inner class RequestData : AsyncTask<Void, String, Void>() {
        private val gson = GsonBuilder().create()
        private lateinit var adapterMovie: AdapterMovie
        override fun doInBackground(vararg params: Void?): Void? {

            val client = OkHttpClient()
//            val json = Gson().toJson(SearchModel(Constant.getApiKey(), keyword))
//            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            val request = Request.Builder()
                    .url(Constant.getUrlSearch().plus(keyword))
//                    .post(body)
                    .build()
            client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // TODO xử lý khi lấy data fail
                            publishProgress("ERROR")
                            Log.i("onFailure", "onFailure")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if (response.isSuccessful){
                                // TODO xử lý kết quả trả về
                                publishProgress(response.body()!!.string())
                                Log.i("onResponse", "onResponse")
                            }
                        }
                    })
            return null
        }

        override fun onProgressUpdate(vararg values: String) {
            Log.i("onProgressUpdate", "onProgressUpdate")
            super.onProgressUpdate(*values)
            val result : TopRateModel = gson.fromJson(values[0], TopRateModel::class.java)
            val arrMovieModel : ArrayList<MovieModel> = result.results!!
            if(arrMovieModel.size == 0) {
                txtResult.visibility = View.VISIBLE
                txtResult.text = "There is no result"
            }
            else {
                txtResult.visibility = View.GONE
                adapterMovie = AdapterMovie(this@SearchActivity, arrMovieModel)
                rvMovie.layoutManager = LinearLayoutManager(this@SearchActivity)
                rvMovie.adapter = adapterMovie
                Log.i("onProgressUpdate", values[0])
            }
        }
    }
}
