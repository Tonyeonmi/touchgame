package com.example.mynavapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.mynavapplication.databinding.FragmentPlayBinding
import java.text.DecimalFormat
import kotlin.concurrent.thread
import kotlin.concurrent.timer

class PlayFragment : Fragment() {

    var binding: FragmentPlayBinding?= null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root

    }

    private fun changeAns(putNumber: Int){
        var current_text =  binding?.fTouchGameTvEnterNum?.text.toString()
        if ( current_text != "Enter your num") {
            current_text += putNumber.toString()
            binding?.fTouchGameTvEnterNum?.text = current_text
        }else{
            current_text = putNumber.toString()
            binding?.fTouchGameTvEnterNum?.text = putNumber.toString()
        }
        check_answer(current_text)
    }

    private fun check_answer(currentAns: String){
        val currentGoal = binding?.fTouchGameTvGoalNum?.text.toString()
        if (currentAns.toInt() == currentGoal.toInt()) {
            val new_goal =  currentGoal.toInt() + 1
            binding?.fTouchGameTvGoalNum?.text = new_goal.toString()
            clearAns()

            if (currentGoal.toInt() == 10) {
                val record = binding?.fTouchGameTvTime?.text.toString().split(" ")[2]
                recordScore(record)
                //inflate 된 요소들이 사라지기 전에 먼저 스코어를 가져와야 함 (혹시 모름)
                val bundle = Bundle().apply {
                    putString("SCORE", record)
                }
                findNavController().navigate(R.id.action_PlayFragment_to_resultFragment,bundle)
            }
        }
    }

    private fun clearAns(){
        binding?.fTouchGameTvEnterNum?.text = "Enter your num"
    }

    private fun backStep() {
        var current = binding?.fTouchGameTvEnterNum?.text.toString()
        if (current != "Enter your num"){
            current = current.substring(0 until (current.length)-1)
            if(current == ""){
                clearAns()
            }else{
                binding?.fTouchGameTvEnterNum?.text = current
            }
        }
    }

    private fun recordScore(record: String){
        val result = "12581203"
        setFragmentResult("SCORE", bundleOf("TOUCH_GAME" to result))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = MainActivity()

        binding?.fTouchGameTvGoalNum?.text = "1"

        binding?.fTouchGameBtn1?.setOnClickListener{
            changeAns(1)
        }
        binding?.fTouchGameBtn2?.setOnClickListener{
            changeAns(2)
        }
        binding?.fTouchGameBtn3?.setOnClickListener{
            changeAns(3)
        }
        binding?.fTouchGameBtn4?.setOnClickListener{
            changeAns(4)
        }
        binding?.fTouchGameBtn5?.setOnClickListener{
            changeAns(5)
        }
        binding?.fTouchGameBtn6?.setOnClickListener{
            changeAns(6)
        }
        binding?.fTouchGameBtn7?.setOnClickListener{
            changeAns(7)
        }
        binding?.fTouchGameBtn8?.setOnClickListener{
            changeAns(8)
        }
        binding?.fTouchGameBtn9?.setOnClickListener{
            changeAns(9)
        }
        binding?.fTouchGameBtn0?.setOnClickListener{
            changeAns(0)
        }
        binding?.fTouchGameBtnClear?.setOnClickListener{
            clearAns()
        }
        binding?.fTouchGameBtnBack?.setOnClickListener{
            backStep()
        }
        binding?.btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_PlayFragment_to_entryFragment)
        }



    //타이머는 터질위험이 있어서 runOnUiThread 사용하는게 좋다.
        thread(start = true) {
            val df2 = DecimalFormat("0.0")
            var second : Double = 0.0

            while (true){
                Thread.sleep(100)
                second += 0.1

                mainActivity.runOnUiThread {
                    binding?.fTouchGameTvTime?.text = "Time : " + df2.format(second).toString() +" sec"
                }
            }
        }
    }


}