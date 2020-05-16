package com.example.viewpager2bug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class PagerFragment : Fragment() {

    private lateinit var textView: TextView

    private var fragmentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentId = arguments!!.getInt(KEY_ID)
        log("onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_page, container, false)
        textView = root.findViewById(R.id.fragment_page_text)
        textView.text = fragmentId.toString()
        return root
    }

    private fun log(message: String) {
        Log.d("ViewPager2Bug", "PagerFragment ($fragmentId) | $message")
    }

    companion object {
        private const val KEY_ID = "keyId"

        fun newInstance(id: Int): PagerFragment {
            return PagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ID, id)
                }
            }
        }
    }

}
