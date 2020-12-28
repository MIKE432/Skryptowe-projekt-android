package com.apusart.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import com.apusart.skryptowe_projekt_android.R
import kotlinx.android.synthetic.main.information_modal.view.*

class InfoModal(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    private val view = LayoutInflater.from(context)
        .inflate(R.layout.information_modal, this, false)

    var modalTitle: CharSequence = ""
        set(value) {
            field = value
            view.modal_title.text = value
        }

    var containerBackground: Int = ResourcesCompat.getColor(resources, R.color.primary_400, null)
        set(value) {
            field = value
            view.modal_container.setCardBackgroundColor(value)
        }

    var contentColor: Int = ResourcesCompat.getColor(resources, R.color.white, null)
        set(value) {
            field = value
            view.modal_title.setTextColor(value)
            view.modal_information.setTextColor(value)
            view.modal_icon.setColorFilter(value)
        }

    var modalIcon: Drawable? = null
        set(value) {
            field = value
            view.modal_icon.setImageDrawable(value)
        }

    var modalInformation: String? = ""
        set(value) {
            if(value == null) {
                field = ""
                return
            }

            field = value
            view.modal_information.text = value
        }

    var isActive: Boolean = false
        set(value) {
            if (isActive == value)
                return
            else {
                field = value
                if (value)
                    transitionToEnd()
                else
                    transitionToStart()
            }
        }

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.InfoModal,
            0, 0
        ).apply {
            val title = getText(R.styleable.InfoModal_modal_title) ?: ""
            val info = getText(R.styleable.InfoModal_modal_information) ?: ""
            val backgroundColor = getColor(R.styleable.InfoModal_modal_background, ResourcesCompat.getColor(resources, R.color.error_background_color, null))
            val textColor = getColor(R.styleable.InfoModal_modal_text_color, ResourcesCompat.getColor(resources, R.color.white, null))
            val icon = getDrawable(R.styleable.InfoModal_modal_icon)

            //set content
            modalTitle = title
            modalInformation = info.toString()
            modalIcon = icon

            //set colors
            containerBackground = backgroundColor
            contentColor = textColor
        }
        setOnClickListener {
            isActive = false
        }
        addView(view)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        view.setOnClickListener(l)
        isActive = false
    }

    private fun transitionToEnd() {
        (view as MotionLayout).transitionToEnd()
    }

    private fun transitionToStart() {
        (view as MotionLayout).transitionToStart()
    }
}