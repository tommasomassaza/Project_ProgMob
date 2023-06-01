package com.example.myloginapplication

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import okhttp3.OkHttpClient;

import okhttp3.Request
import java.io.IOException
import java.io.InputStream
import java.net.SocketTimeoutException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        val button = view.findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            button.setText("CIAO")


            val SDK_INT = Build.VERSION.SDK_INT
            if (SDK_INT > 8) {
                val policy = ThreadPolicy.Builder()
                    .permitAll().build()
                StrictMode.setThreadPolicy(policy)
                try {
                    val client = OkHttpClient.Builder()
                        .callTimeout(30000, TimeUnit.MILLISECONDS)
                        .build()

                    val request = Request.Builder()
                        .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/auto-complete?q=tesla&region=US")
                        .get()
                        .addHeader(
                            "X-RapidAPI-Key",
                            "0f3731783dmshbf77e223a93fd29p17b87fjsn662b2e150b17"
                        )
                        .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                        .build()


                    val response = client.newCall(request).execute()
                    println(response.body.toString())

                } catch (e: SocketTimeoutException){
                    println(e.toString())
                }



            }
        }



        return view
    }


}