package com.example.uas__ppapb.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.uas__ppapb.databinding.OrderBinding
import java.util.Random
import kotlin.math.pow

class Order : AppCompatActivity() {
    private lateinit var binding: OrderBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = OrderBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val name = intent.getStringExtra(EXTRA_NAME)
//
//        val mainImageView = findViewById<ImageView>(R.id.image_movie)
//        val img = intent.getIntExtra(EXTRA_IMG, 0)
//        if (img != 0) {
//            mainImageView.setImageResource(img)
//        }
//
//        with(binding){
//            backButton.setOnClickListener{
//                val intentOrderPageActivity =
//                    Intent(this@Order, Home::class.java)
//                intentOrderPageActivity.putExtra(EXTRA_NAME, name)
//                intentOrderPageActivity.putExtra(EXTRA_TITLE, title)
//                intentOrderPageActivity.putExtra(EXTRA_IMG, img)
//                startActivity(intentOrderPageActivity)
//            }
//
//            val bioskop = intent.getStringExtra(EXTRA_BIOSKOP)
//            val seat = intent.getStringExtra(EXTRA_SEAT)
//            val pay = intent.getStringExtra(EXTRA_PAY)
//            val bank = intent.getStringExtra(EXTRA_BANK)
//            val prize = intent.getIntExtra(EXTRA_PRIZE, 0)
//            val addedPrize = intent.getIntExtra(EXTRA_ADDED_PRIZE, 0)
//            val time = intent.getStringExtra(EXTRA_TIME)
//            val title = intent.getStringExtra(EXTRA_TITLE)
//
//            val finPrize = prize + addedPrize
//
//            titleMovie.text = title
//            bioskopMovie.text = bioskop
//            tanggalWaktu.text = time
//            seatMovie.text = seat
//            if (pay == "Bank"){
//                paymentMethod.text = pay + "($bank)"
//            } else {
//                paymentMethod.text = pay
//            }
//            jenisSeatMovie.text = seat + " Seat"
//            prizeAccept.text = "Rp. " + prize
//            convenienceFee.text = "Rp. " +  addedPrize
//            actualPrize.text = "Rp. " + finPrize
//        }
//        val orderNumb = findViewById<TextView>(R.id.order_number)
//        val randomLength = Random().nextInt(4) + 1
//        val randomNumber = generateRandomNumber(randomLength)
//        orderNumb.text = randomNumber.toString()
//    }
//    private fun generateRandomNumber(length: Int): Int {
//        require(length > 0) {}
//
//        val minValue = 10.0.pow(length - 1).toInt()
//        val maxValue = 10.0.pow(length).toInt()
//
//        val random = Random(System.currentTimeMillis())
//        return random.nextInt(maxValue - minValue) + minValue
//    }
}