package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularTimePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var selectedRangeCount: Int = 1
    private var ranges = mutableListOf<Pair<Float, Float>>()
    private var activeHandle: Int? = null
    private var startAngle: Float = 60f // 초기 시작 각도 (2:00 PM)
    private var endAngle: Float = 120f   // 초기 끝 각도 (4:00 PM)

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.GRAY
        strokeWidth = 10f
    }

    private val selectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        strokeWidth = 15f
    }

    private val handlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 30f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val availableRangePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.DKGRAY // 선택 가능한 영역의 진한 색상
        strokeWidth = 15f
    }

    init {
        ranges.add(Pair(startAngle, endAngle))  // 초기 시작 각도와 끝 각도를 설정
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        radius = (min(width, height) / 2 * 0.8).toFloat()
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()

        canvas.drawCircle(centerX, centerY, radius, circlePaint)
        drawAvailableRange(canvas)
        drawSelectionArcs(canvas)
        drawHandles(canvas)

        // 시작 시간 텍스트
        val startTime = "2:00 PM"
        val startTimeTextSize = 60f // 변경된 폰트 크기
        textPaint.textSize = startTimeTextSize
        val startTimeTextWidth = textPaint.measureText(startTime)
        val startTimeTextHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top
        val startX = centerX - startTimeTextWidth / 2
        val startY = centerY + startTimeTextHeight / 2 - 85f
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.tmoney_round_wind_regular)
        canvas.drawText(startTime, startX, startY, textPaint)

        // 끝 시간 텍스트
        val endTime = "4:00 PM"
        val endTimeTextSize = 60f // 변경된 폰트 크기
        textPaint.textSize = endTimeTextSize
        val endTimeTextWidth = textPaint.measureText(endTime)
        val endX = centerX - endTimeTextWidth / 2
        val endY = centerY + startTimeTextHeight / 2 + 85f
        canvas.drawText(endTime, endX, endY, textPaint)
    }

    private fun drawAvailableRange(canvas: Canvas) {
        val path = Path().apply {
            arcTo(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                startAngle, endAngle - startAngle, false
            )
        }
        canvas.drawPath(path, availableRangePaint)
    }

    private fun drawSelectionArcs(canvas: Canvas) {
        for ((startAngle, endAngle) in ranges) {
            val path = Path().apply {
                arcTo(
                    centerX - radius, centerY - radius,
                    centerX + radius, centerY + radius,
                    startAngle, endAngle - startAngle, false
                )
            }
            canvas.drawPath(path, selectionPaint)
        }
    }

    private fun drawHandles(canvas: Canvas) {
        for ((startAngle, endAngle) in ranges) {
            val startAngleInRadians = Math.toRadians(startAngle.toDouble())
            val endAngleInRadians = Math.toRadians(endAngle.toDouble())

            val startX = (centerX + radius * cos(startAngleInRadians)).toFloat()
            val startY = (centerY + radius * sin(startAngleInRadians)).toFloat()

            val endX = (centerX + radius * cos(endAngleInRadians)).toFloat()
            val endY = (centerY + radius * sin(endAngleInRadians)).toFloat()

            canvas.drawCircle(startX, startY, 20f, handlePaint)
            canvas.drawCircle(endX, endY, 20f, handlePaint)
        }
    }

    private fun isAngleInRange(angle: Float): Boolean {
        val normalizedStartAngle = startAngle % 360
        val normalizedEndAngle = endAngle % 360
        return when {
            normalizedStartAngle < normalizedEndAngle -> angle in normalizedStartAngle..normalizedEndAngle
            else -> angle in normalizedStartAngle..360f || angle in 0f..normalizedEndAngle
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val angle = calculateAngle(event.x, event.y)
                if (isAngleInRange(angle)) {
                    handleTouch(angle)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                activeHandle = null
            }
        }
        return true
    }

    private fun handleTouch(angle: Float) {
        val (startAngle, endAngle) = ranges.first()
        ranges[0] = Pair(angle, endAngle)
    }

    private fun calculateAngle(x: Float, y: Float): Float {
        val dx = x - centerX
        val dy = y - centerY
        val angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
        return if (angle < 0) angle + 360 else angle
    }

    fun setRangeCount(count: Int) {
        selectedRangeCount = count
        ranges.clear()
        ranges.add(Pair(startAngle, endAngle))  // 초기 범위 설정 (2:00 PM ~ 4:00 PM)
        invalidate()
    }

    fun setAvailableRange(startAngle: Float, endAngle: Float) {
        this.startAngle = startAngle
        this.endAngle = endAngle
        ranges[0] = Pair(startAngle, endAngle)
        invalidate()
    }
}