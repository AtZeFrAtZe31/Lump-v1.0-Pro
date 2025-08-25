
package com.atze.lump

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atze.lump.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.random.Random

class DashboardFragment : Fragment() {
    private var _b: FragmentDashboardBinding? = null
    private val b get() = _b!!
    private var job: Job? = null

    private val lineEntries = ArrayList<Entry>()
    private lateinit avar lineDataSet: LineDataSet
    private val candleEntries = ArrayList<CandleEntry>()
    private lateinit var candleDataSet: CandleDataSet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentDashboardBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharts()
        startTicker()

        b.btnBuy.setOnClickListener { /* TODO: Integrate order action */ }
        b.btnSell.setOnClickListener { /* TODO: Integrate order action */ }
    }

    private fun setupCharts() {
        lineDataSet = LineDataSet(lineEntries, "BTC/USD").apply {
            setDrawCircles(false)
            lineWidth = 2.2f
            color = Color.CYAN
        }
        b.priceLineChart.apply {
            data = LineData(lineDataSet)
            axisLeft.textColor = Color.WHITE
            axisRight.isEnabled = false
            xAxis.textColor = Color.WHITE
            legend.textColor = Color.WHITE
            description = Description().apply { text = "" }
        }

        candleDataSet = CandleDataSet(candleEntries, "BTC/USD Candle").apply {
            decreasingColor = Color.RED
            increasingColor = Color.GREEN
            neutralColor = Color.GRAY
            shadowColor = Color.LTGRAY
            setDrawValues(false)
        }
        b.candleChart.apply {
            data = CandleData(candleDataSet)
            axisLeft.textColor = Color.WHITE
            axisRight.isEnabled = false
            xAxis.textColor = Color.WHITE
            legend.textColor = Color.WHITE
            description = Description().apply { text = "" }
        }
    }

    private fun startTicker() {
        var last = 13800f
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(1000)
                val x = if (lineEntries.isEmpty()) 0f else lineEntries.last().x + 1f
                last += Random.nextFloat() * 60f - 30f
                lineEntries.add(Entry(x, last))
                trim(lineEntries, 120)
                lineDataSet.notifyDataSetChanged()
                b.priceLineChart.data.notifyDataChanged()
                b.priceLineChart.notifyDataSetChanged()
                b.priceLineChart.moveViewToX(lineEntries.last().x)
                b.priceLineChart.invalidate()

                val open = last + Random.nextFloat() * 10f - 5f
                val close = open + Random.nextFloat() * 20f - 10f
                val high = max(open, close) + Random.nextFloat() * 10f
                val low = kotlin.math.min(open, close) - Random.nextFloat() * 10f
                candleEntries.add(CandleEntry(x, high, low, open, close))
                trim(candleEntries, 60)
                candleDataSet.notifyDataSetChanged()
                b.candleChart.data.notifyDataChanged()
                b.candleChart.notifyDataSetChanged()
                b.candleChart.moveViewToX(candleEntries.last().x)
                b.candleChart.invalidate()
            }
        }
    }

    private fun <T> trim(list: MutableList<T>, maxSize: Int) {
        while (list.size > maxSize) list.removeAt(0)
    }

    override fun onDestroyView() {
        job?.cancel()
        _b = null
        super.onDestroyView()
    }
}
