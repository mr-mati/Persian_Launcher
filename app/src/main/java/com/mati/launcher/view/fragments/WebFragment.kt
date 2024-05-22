package com.mati.launcher.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mati.FiveGames.util.NetworkChecker
import com.mati.game.R
import com.mati.game.databinding.FragmentWebBinding

class WebFragment : Fragment() {
    lateinit var binding: FragmentWebBinding
    private var loadingUrl = "https://PRPMOBILE.ir/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWebBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // getUrl
        /*loadingUrl = WebFragmentArgs.fromBundle(requireArguments()).webUrl*/

        val webView = binding.testWv
        webView.webViewClient = object : WebViewClient() {}
        webView.settings.javaScriptEnabled = true
        if (NetworkChecker(requireActivity()).isInternetConnected) {
            webView.loadUrl(loadingUrl)
        } else {
            Toast.makeText(requireActivity(), "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show()
        }

        refreshPage(webView)

        WebView.setWebContentsDebuggingEnabled(true)
        WebView.setWebContentsDebuggingEnabled(true)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_webFragment_to_mainFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val webView = binding.testWv
                if (webView.url != loadingUrl) {
                    webView.goBack()
                    return
                } else {
                    findNavController().navigate(R.id.action_webFragment_to_mainFragment)
                }
            }
        })
    }


    private fun refreshPage(webView: WebView) {
        binding.swipeRefreshMain.setOnRefreshListener {
            if (NetworkChecker(requireActivity()).isInternetConnected) {
                binding.swipeRefreshMain.isRefreshing = false
                webView.reload()
            } else {
                Toast.makeText(requireActivity(), "خطا در برقراری ارتباط", Toast.LENGTH_SHORT)
                    .show()
            }

            Handler(Looper.getMainLooper()).postDelayed({
            }, 1500)
        }
    }

}