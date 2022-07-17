package com.example.happygame

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.happygame.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log

class MainViewModel : ViewModel() {

    private var isOpened = false
    private var isFirst = true
    private var lastPic: CardModel? = null
    private lateinit var viewList: MutableList<ImageView>
    private var cardModels = arrayListOf<CardModel>()
    private val resIdLists = listOf(
        R.drawable.hema,
        R.drawable.huli,
        R.drawable.wa,
        R.drawable.yazi
    )

    fun bind(binding: ActivityMainBinding) {
        viewList = mutableListOf(
            binding.iv11, binding.iv12, binding.iv13, binding.iv14,
            binding.iv21, binding.iv22, binding.iv23, binding.iv24,
            binding.iv31, binding.iv32, binding.iv33, binding.iv34,
            binding.iv41, binding.iv42, binding.iv43, binding.iv44
        )
        viewList.shuffle()
        for ((index, id) in resIdLists.withIndex()) {
            for (i in index * 4..index * 4 + 3) {
                val model = CardModel(viewList[i], id)
//                model.view.setImageResource(model.resId)
                cardModels.add(model)
            }
        }
    }

    //需要绑定的时间
    fun cardClicked(view: View) {
        cardModels.forEach {
            if (it.view == view) {

                it.view.setImageResource(it.resId)
                isOpened = true


                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        MainScope().launch {
                            it.view.setImageResource(R.drawable.icbg)
                            isOpened = false

                        }
                    }
                }, 500)

                Log.d(TAG, "if: if")
                if (lastPic != null && lastPic != it && isOpened && lastPic?.resId == it.resId) {
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            MainScope().launch {
                                lastPic!!.view.visibility = View.INVISIBLE
                                Log.d(TAG, "last: ${lastPic!!.view.visibility}")
                                it.view.visibility = View.INVISIBLE
                                Log.d(TAG, "it: ${it!!.view.visibility}")
                                lastPic = null
                            }
                        }
                    }, 200)



                }else {
                    lastPic = it
                }
                if (lastPic == null){
                    lastPic = it
                    Log.d(TAG, "cardClicked: yse")
                }
            }
        }
    }
}