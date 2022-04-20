package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botonCalcular.setOnClickListener { calculateTip() }
        binding.costeDelServicioEditText.setOnKeyListener { view, keyCode, _ ->  handleKeyEvent(view, keyCode)}
    }
    private fun calculateTip(){
        val costServiceString = binding.costeDelServicioEditText.text.toString()
        val costService = costServiceString.toDoubleOrNull()
        if(costService == null){
            binding.resultado.text = ""
            return
        }
        val tipPercentage = when(binding.opciones.checkedRadioButtonId){
            binding.veinte.id -> 0.20
            R.id.dieciocho -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * costService
        if(binding.redondeo.isChecked){
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.resultado.text = getString(R.string.cantidad_propina, formattedTip)


    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}