package com.example.photovoltaics.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photovoltaics.CalculateActivity
import com.example.photovoltaics.R
import com.example.photovoltaics.ResultActivity
import com.example.photovoltaics.adapter.HistoryAdapter.HistoryViewHolder
import com.example.photovoltaics.databinding.ItemHistoryBinding
import com.example.photovoltaics.model.CalculateResultModel
import com.example.photovoltaics.room.HistoryEntity

class HistoryAdapter : ListAdapter<HistoryEntity, HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity) {
            binding.dateCreated.text = history.created

            binding.value1.titleItem.text =
                itemView.context.getString(R.string.title_installed_capacity)
            binding.value1.valueItem.text =
                itemView.context.getString(R.string.msg_watt_peak_double, history.installedCapacity)

            binding.value2.titleItem.text = itemView.context.getString(R.string.income_title_energy)
            binding.value2.valueItem.text =
                itemView.context.getString(R.string.msg_energy, history.energy)

            binding.value3.titleItem.text = itemView.context.getString(R.string.title_income)
            binding.value3.valueItem.text =
                itemView.context.getString(R.string.msg_price, history.income)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ResultActivity::class.java)
                val model = CalculateResultModel(
                    gmt = history.gmt,
                    longitude = history.longitude,
                    latitude = history.latitude,
                    collectorTilt = history.collectorTilt,
                    azimuthCollector = history.azimuthCollector,
                    roofLength = history.roofLength,
                    roofWidth = history.roofWidth,
                    powerPln = history.powerPln,
                    installedCapacityMax = history.installedCapacityMax,
                    installedCapacity = history.installedCapacity,
                    inverter = history.inverter,
                    pvPrice = history.pvPrice,
                    inverterPrice = history.inverterPrice,
                    mounting = history.mounting,
                    etc = history.etc,
                    totalPrice = history.totalPrice,
                    energy = history.energy,
                    income = history.income,
                    date = history.created
                )

                intent.putExtra(CalculateActivity.RESULT, model)
                itemView.context.startActivity(intent)
            }

        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HistoryEntity> =
            object : DiffUtil.ItemCallback<HistoryEntity>() {
                override fun areItemsTheSame(
                    oldUser: HistoryEntity,
                    newUser: HistoryEntity
                ): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: HistoryEntity,
                    newUser: HistoryEntity
                ): Boolean {
                    return oldUser == newUser
                }
            }

    }

}