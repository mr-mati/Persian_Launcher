package com.mati.launcher.view.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mati.game.R
import com.mati.game.core.Config
import com.mati.game.core.GTASA
import com.mati.game.databinding.FragmentMainBinding
import com.mati.game.databinding.ShopDialogBinding
import com.mati.launcher.activity.LoaderActivity
import com.mati.launcher.activity.UpdateActivity
import com.mati.launcher.adapter.NewsAdapter
import com.mati.launcher.data.model.News
import com.mati.launcher.data.model.Update
import com.mati.launcher.utils.Interface
import com.mati.launcher.utils.Lists
import com.mati.mati.reg.Preferences
import org.ini4j.Ini
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FilenameFilter
import java.io.IOException

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private lateinit var recyclerNews: RecyclerView
    private lateinit var storiesAdapter: NewsAdapter

    private val handler = Handler(Looper.getMainLooper())
    val pingRunnable = object : Runnable {
        override fun run() {
            val ping = getPing("62.3.14.22")
            showPing(ping)
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.post(pingRunnable)

        LoadNick()
        CheckNewUpdate()

        if (CheckFile()) {
            CeloeErrorDialog()
        }

        if (IsGameInstalled()) {
            binding.download.visibility = View.INVISIBLE
        } else if (IsUpdateInstalled()) {
            binding.download.visibility = View.INVISIBLE
        } else {
            binding.download.visibility = View.VISIBLE
        }


        binding.PlayGame.setOnClickListener {
            onClickPlay()
        }

        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
        }

        binding.shop.setOnClickListener {
            shopDialog()
        }

        binding.website.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_webFragment)
        }

        binding.download.setOnClickListener {
            if (IsGameInstalled()) {
                if (!IsUpdateInstalled()) {
                    ToUpdate()
                }
            } else {
                ToLoad()
            }
        }

        binding.telegram.setOnClickListener {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/PRPMOBILE"))
            telegram.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)
        }

        binding.discord.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_webFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(pingRunnable)

    }

    fun onClickPlay() {
        if (CheckFile()) {
            CeloeErrorDialog()
        } else {
            if (IsGameInstalled()) {
                if (IsUpdateInstalled()) {
                    try {
                        val ini = Ini(
                            File(
                                Environment.getExternalStorageDirectory()
                                    .toString() + "/PersianRp/version.ini"
                            )
                        )
                        if (ini["version", "code"] == Config.VERSION_CODE_DATA) {
                            startActivity(Intent(requireContext(), GTASA::class.java))
                            if (checkValidNick()) {
                                startActivity(Intent(requireActivity(), GTASA::class.java))
                            } else {
                                checkValidNick()
                            }
                        } else {
                            ToUpdate()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    ToUpdate()
                }
            } else {
                ToLoad()
            }
        }
    }

    private fun IsGameInstalled(): Boolean {
        val CheckFile =
            Environment.getExternalStorageDirectory().toString() + "/PersianRp/texdb/gta3.img"
        val file = File(CheckFile)
        return file.exists()
    }

    private fun IsUpdateInstalled(): Boolean {
        val CheckFile =
            Environment.getExternalStorageDirectory().toString() + "/PersianRp/version.ini"
        val file = File(CheckFile)
        return file.exists()
    }

    fun CheckFile(): Boolean {
        val directoryPath = Environment.getExternalStorageDirectory().toString() + "/PersianRp/"
        val directoryCloe = Environment.getExternalStorageDirectory().toString() + "/PersianRp/cloe"
        val directoryData = Environment.getExternalStorageDirectory().toString() + "/PersianRp/data"
        val directorySamp = Environment.getExternalStorageDirectory().toString() + "/PersianRp/samp"
        val directoryAnim = Environment.getExternalStorageDirectory().toString() + "/PersianRp/anim"
        val directoryPersian =
            Environment.getExternalStorageDirectory().toString() + "/PersianRp/persian"
        val fileExtensions = arrayOf("cs", "asi", "csa", "csi", "so")

        return if (hasFiles(directoryAnim, fileExtensions) || hasFiles(
                directoryPath,
                fileExtensions
            ) || hasFiles(directoryCloe, fileExtensions) || hasFiles(
                directoryData,
                fileExtensions
            ) || hasFiles(directorySamp, fileExtensions) || hasFiles(
                directoryPersian,
                fileExtensions
            )
        ) {
            true
        } else {
            false
        }
    }

    fun hasFiles(directoryPath: String, fileExtensions: Array<String>): Boolean {
        val directory = File(directoryPath)
        val filter = FilenameFilter { _, name ->
            for (extension in fileExtensions) {
                if (name.endsWith(".$extension")) {
                    return@FilenameFilter true
                }
            }
            false
        }
        val files = directory.listFiles(filter)
        return files != null && files.size > 0
    }

    private fun tost(pon: String) {
        Toast.makeText(requireContext(), pon, Toast.LENGTH_LONG).show()
    }

    private fun LoadNick() {
        try {
            val w = Ini(
                File(
                    Environment.getExternalStorageDirectory()
                        .toString() + "/PersianRp/persian/settings.ini"
                )
            )
            Preferences.setNick(w["client", "name"])
            w.store()
            binding.UserName.text = w["client", "name"]
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkValidNick(): Boolean {
        val nick = binding.UserName
        if (nick.text.toString().isEmpty()) {
            tost("نام مستعار را وارد کنید")
            return false
        }
        if (!(nick.text.toString().contains("_"))) {
            tost("نام مستعار باید دارای نماد \"_\" باشد ")
            return false
        }
        if (nick.text.toString().length < 4) {
            tost(
                """
                
                نام مستعار باید حداقل 4 کاراکتر باشد
                """.trimIndent()
            )
            return false
        }
        return true
    }

    fun CheckNewUpdate() {
        val retrofit = Retrofit.Builder().baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val sInterface = retrofit.create(
            Interface::class.java
        )
        val scall = sInterface.update
        scall.enqueue(object : Callback<Update> {
            override fun onResponse(call: Call<Update>, response: Response<Update>) {
                val data = response.body()!!
                    .values

                if (data[0] != null) {
                    if (data[0]!!.version_code != Config.VERSION_CODE) {
                        UpdateDialog(data[0]!!.mandatory)
                    }
                } else {
                    tost("خطا در اتصال با سروور")
                }
            }

            override fun onFailure(call: Call<Update>, t: Throwable) {
                ErrorDialog()
            }
        })
    }

    private fun ToLoad() {
        val intent = Intent(requireContext(), LoaderActivity::class.java)
        startActivity(intent)
    }

    private fun ToUpdate() {
        val intent = Intent(requireContext(), UpdateActivity::class.java)
        startActivity(intent)
    }

    private fun UpdateDialog(visible: Int) {
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setView(layoutInflater.inflate(R.layout.update_dialog, null))
        dialog.setCancelable(false)
        dialog.show()

        val btnCancel = dialog.findViewById<ConstraintLayout>(R.id.cancel_btn)
        val btnUpdate = dialog.findViewById<ConstraintLayout>(R.id.Update_Btn)

        if (visible == 0) {
            btnCancel!!.visibility = View.VISIBLE
        } else {
            btnCancel!!.visibility = View.GONE
        }

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnUpdate!!.setOnClickListener {
            val telegram =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Mati_Source"))
            telegram.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)
        }
    }

    private fun ErrorDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setView(layoutInflater.inflate(R.layout.error_dialog, null))
        dialog.setCancelable(false)
        dialog.show()

        val btnUpdate = dialog.findViewById<ConstraintLayout>(R.id.Update_Btn)

        btnUpdate!!.setOnClickListener {
            requireActivity().recreate()
        }
    }

    private fun shopDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding = ShopDialogBinding.inflate(layoutInflater)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setView(dialogBinding.root)
        dialog.show()

        dialogBinding.btnOpenTelegram.setOnClickListener {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/mr_mati"))
            telegram.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)
            dialog.dismiss()
        }
    }


    private fun CeloeErrorDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setView(layoutInflater.inflate(R.layout.cheat_dialog, null))
        dialog.setCancelable(false)
        dialog.show()

        val btnUpdate = dialog.findViewById<ConstraintLayout>(R.id.Update_Btn)

        btnUpdate!!.setOnClickListener {
            requireActivity().recreate()
        }
    }

    private fun showPing(ping: Int) {

        val text = binding.txtPing
        text.text = "$ping"

        val icon = binding.icPing

        when (ping) {
            in 0..100 -> {
                text.setTextColor(Color.parseColor("#2ECC71"))
                icon.setColorFilter(Color.parseColor("#2ECC71"))
            }

            in 100..300 -> {
                text.setTextColor(Color.parseColor("#FF8600"))
                icon.setColorFilter(Color.parseColor("#FF8600"))
            }

            else -> {
                text.setTextColor(Color.parseColor("#f0655e"))
                icon.setColorFilter(Color.parseColor("#f0655e"))
            }
        }

    }

    private fun getPing(ipAddress: String): Int {
        return try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $ipAddress")
            val returnVal = process.waitFor()
            if (returnVal == 0) {
                val outPut = process.inputStream.bufferedReader().use { it.readText() }
                val timeString = outPut.split("time=")[1].split(" ")[0]

                timeString.toFloat().toInt()
            } else {
                -1
            }
        } catch (e: IOException) {
            e.printStackTrace()
            -1
        } catch (e: InterruptedException) {
            e.printStackTrace()
            -1
        }
    }


    /*fun getStories(context: Context?) {
        val retrofit = Retrofit.Builder().baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val sInterface = retrofit.create(
            Interface::class.java
        )
        val scall = sInterface.stories
        scall.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val data = response.body()!!.values
                if (data != null) {
                    if (Lists.nlist.isEmpty()) {
                        data.reverse()
                    }
                } else {
                    recyclerNews.visibility = View.INVISIBLE
                }

                recyclerNews = binding.storiesRecycler
                recyclerNews.setHasFixedSize(true)
                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerNews.setLayoutManager(layoutManager)

                storiesAdapter = NewsAdapter(
                    requireContext(),
                    Lists.nlist as ArrayList<News.Value>?
                )
                recyclerNews.setAdapter(storiesAdapter)

                val pagerSnapHelper = PagerSnapHelper()
                pagerSnapHelper.attachToRecyclerView(recyclerNews)

                recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (recyclerNews.getScrollState() > 3) {
                                tost(response.body()!!.values.size.toString())
                                recyclerView.scrollToPosition(0)
                            }
                        }
                    }
                })
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                ErrorDialog()
            }
        })
    }*/

}