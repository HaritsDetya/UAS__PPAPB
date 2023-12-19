package com.example.uas__ppapb.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.example.uas__ppapb.databinding.PaymentBinding

class Payment : AppCompatActivity() {
    private lateinit var binding: PaymentBinding
    private lateinit var itemBioskop: Array<String>
    private lateinit var itemSeat: Array<String>
    private lateinit var itemPayment: Array<String>
    private lateinit var itemBank: Array<String>
//
//    companion object{
//        const val EXTRA_BIOSKOP = "extra_bioskop"
//        const val EXTRA_SEAT = "extra_seat"
//        const val EXTRA_PAY = "extra_pay"
//        const val EXTRA_BANK = "extra_bank"
//        const val EXTRA_TIME = "extra_time"
//        const val EXTRA_PRIZE = "extra_prize"
//        const val EXTRA_ADDED_PRIZE = "extra_added_prize"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = PaymentBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val title = intent.getStringExtra(EXTRA_TITLE)
//        val img = intent.getIntExtra(EXTRA_IMG, 0)
//        val name = intent.getStringExtra(EXTRA_NAME)
//
//        with(binding){
//            backButton.setOnClickListener{
//                val intentPaymentPageActivity =
//                    Intent(this@Payment, Home::class.java)
//                intentPaymentPageActivity.putExtra(EXTRA_NAME, name)
//                startActivity(intentPaymentPageActivity)
//            }
//
//            itemBioskop = resources.getStringArray(R.array.itemBioskop)
//            val adapterBioskop = ArrayAdapter(this@Payment, android.R.layout.simple_spinner_item, itemBioskop)
//            adapterBioskop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            bioskop.adapter = adapterBioskop
//
//            itemSeat = resources.getStringArray(R.array.itemSeat)
//            val adapterSeat = ArrayAdapter(this@PaymentPageActivity, android.R.layout.simple_spinner_item, itemSeat)
//            adapterSeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            seat.adapter = adapterSeat
//
//            itemPayment = resources.getStringArray(R.array.itemPay)
//            val adapterPayment = ArrayAdapter(this@PaymentPageActivity, android.R.layout.simple_spinner_item, itemPayment)
//            adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            payment.adapter = adapterPayment
//
//            itemBank = resources.getStringArray(R.array.itemBank)
//            val adapterBank = ArrayAdapter(this@PaymentPageActivity, android.R.layout.simple_spinner_item, itemBank)
//            adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            bank.adapter = adapterBank
//
//            val datePicker = findViewById<DatePicker>(R.id.tanggal)
//            val timePicker = findViewById<TimePicker>(R.id.time)
//
//            val textView = findViewById<TextView>(R.id.jenis_seat)
//            val prize = findViewById<TextView>(R.id.prize)
//            val finPrize = findViewById<TextView>(R.id.final_prize)
//            val itemPrize = mapOf(
//                itemSeat[0] to 40000,
//                itemSeat[1] to 50000,
//                itemSeat[2] to 60000)
//            seat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                    val selectedItem = parent.getItemAtPosition(position).toString()
//                    val selectedPrize = itemPrize[selectedItem]
//                    val addedPrize = 2000
//                    val totalPrize = addedPrize + selectedPrize!!
//
//                    textView.text = "$selectedItem"
//                    prize.text = "Rp. " + selectedPrize.toString()
//                    finPrize.text = "Rp. " + totalPrize.toString()
//                }
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                }
//            }
//
//            val numb = findViewById<TextView>(R.id.number)
//            payment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                    val selectedItem = parent.getItemAtPosition(position).toString()
//                    if (selectedItem == "Bank") {
//                        bank.visibility = View.VISIBLE
//                        numb.visibility = View.VISIBLE
//                    } else {
//                        bank.visibility = View.GONE
//                        numb.visibility = View.GONE
//                    }
//                }
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                }
//            }
//
//            buttonOrder.setOnClickListener {
//                val tahun = datePicker.year
//                val bulan = datePicker.month + 1
//                val hari = datePicker.dayOfMonth
//                val tanggal = "$hari/$bulan/$tahun"
//
//                val jam = timePicker.hour
//                val menit = timePicker.minute
//                val waktu = "$jam:$menit"
//                val dateTime = "$tanggal, $waktu"
//
//                val itemBioskopSelect = bioskop.selectedItem.toString()
//                val itemSeatSelect = seat.selectedItem.toString()
//                val itemPaySelect = payment.selectedItem.toString()
//                val itemBankSelect = bank.selectedItem.toString()
//
//                val selectedPrize = itemPrize[itemSeatSelect]
//                val addedPrize = 2000
//
//                val intentPaymentPageActivity =
//                    Intent(this@Payment, Order::class.java)
//                intentPaymentPageActivity.putExtra(EXTRA_BIOSKOP, itemBioskopSelect)
//                intentPaymentPageActivity.putExtra(EXTRA_SEAT, itemSeatSelect)
//                intentPaymentPageActivity.putExtra(EXTRA_PAY, itemPaySelect)
//                intentPaymentPageActivity.putExtra(EXTRA_TIME, dateTime)
//                intentPaymentPageActivity.putExtra(EXTRA_PRIZE, selectedPrize)
//                intentPaymentPageActivity.putExtra(EXTRA_ADDED_PRIZE, addedPrize)
//                intentPaymentPageActivity.putExtra(EXTRA_TITLE, title)
//                intentPaymentPageActivity.putExtra(EXTRA_IMG, img)
//                intentPaymentPageActivity.putExtra(EXTRA_NAME, name)
//
//                if (itemPaySelect == "Bank") {
//                    intentPaymentPageActivity.putExtra(EXTRA_BANK, itemBankSelect)
//                }
//                startActivity(intentPaymentPageActivity)
//            }
//
//        }
//    }
}