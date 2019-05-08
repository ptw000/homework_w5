package com.example.admin.homework_w5

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_top_rate.*
import okhttp3.*
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TopRateFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TopRateFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TopRateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rate, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RequestData().execute()
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            RequestData().execute()
        })
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume")
    }

    inner class RequestData : AsyncTask<Void, String, Void>() {
        private val gson = GsonBuilder().create()
        private lateinit var adapterMovie: AdapterMovie
        override fun doInBackground(vararg params: Void?): Void? {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(Constant.getUrlTopRate())
                    .build()
            client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // TODO xử lý khi lấy data fail
                            publishProgress("ERROR")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if (response.isSuccessful){
                                // TODO xử lý kết quả trả về
                                publishProgress(response.body()!!.string())
                            }
                        }
                    })
            return null
        }

        override fun onProgressUpdate(vararg values: String) {
            super.onProgressUpdate(*values)
            if(values[0].equals("ERROR")) {
                txtError.visibility = View.VISIBLE
                txtError.text = "SOMETHING WENT WRONG"
            }
            else {
                txtError.visibility = View.GONE
                val result : TopRateModel = gson.fromJson(values[0], TopRateModel::class.java)
                val arrMovieModel : ArrayList<MovieModel> = result.results!!
                adapterMovie = AdapterMovie(context!!, arrMovieModel)
                rvMovie.layoutManager = LinearLayoutManager(context)
                rvMovie.adapter = adapterMovie
            }
            swipeContainer.isRefreshing = false
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopRateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TopRateFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
