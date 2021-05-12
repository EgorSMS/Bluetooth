package com.example.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mBluetoothAdapter: BluetoothAdapter? = null
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        //Проверка на доступность модуля блютуз
        if(mBluetoothAdapter == null){
            statusBluetoothTv.text = "Bluetooth недоступен"
        }else{
            statusBluetoothTv.text = "Bluetooth доступен"
        }
        onBtn.setOnClickListener {
            if(!mBluetoothAdapter!!.isEnabled()){
                showToast("Включение Bluetooth...")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            }else{
                showToast("Bluetooth включен")
            }
        }

        // ДАЕМ ВОЗМОЖНОСТЬ ВИДЕТЬ НАШЕ УСТРОЙСТВО ДРУГИМ
        discorableBtn.setOnClickListener {
            if(!mBluetoothAdapter!!.isDiscovering()){
                showToast("Сделайте ваше устройство для других")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_DISCOVER_BT)
            }
        }

        //НАЖАТИЕ НА КНОПКУ ВЫКЛЮЧЕНИЯ BLUETOOTH
        offBtn.setOnClickListener {
            if(mBluetoothAdapter!!.isEnabled()){
                mBluetoothAdapter!!.disable()
                showToast("Bluetooth выключается")
            }else{
                showToast("Bluetooth Выключен")
            }
        }

        // НАХОДИМ СОПРЯЖЕНЫЕ БЛЮТУЗ УСТРОЙСТВА
        pairedBtn.setOnClickListener {
            if(mBluetoothAdapter!!.isEnabled()){
                pairedTv.setText("Сопряженые устройства")
                val devices = mBluetoothAdapter!!.getBondedDevices()
                for(device in devices){
                    pairedTv.append(""" Device: ${device.name}, $device""".trimIndent())
                }
            }else{
                //Блютуз не может найти сопряженые устройства
                showToast("Turn on Bluetooth to get paired devices")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    companion object{
        private const val REQUEST_ENABLE_BT = 0
        private const val REQUEST_DISCOVER_BT = 1
    }
}