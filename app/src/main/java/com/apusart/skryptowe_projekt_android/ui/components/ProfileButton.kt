package com.apusart.skryptowe_projekt_android.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.apusart.skryptowe_projekt_android.R

class ProfileButton(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    private val titleView: TextView
    private val underLine: View
    private val icon: ImageView

    init {
        context.theme.obtainStyledAttributes(attributeSet,
            R.styleable.ProfileButton,
            0,0).apply {

            val name = getText(R.styleable.ProfileButton_profile_button_name) ?: ""
            val tintColor = getColor(R.styleable.ProfileButton_profile_button_icon_tint, ResourcesCompat.getColor(resources, R.color.black, null))
            val _icon = getDrawable(R.styleable.ProfileButton_profile_button_icon)
            val buttonUnderlineColor = getColor(R.styleable.ProfileButton_profile_button_underline_color, ResourcesCompat.getColor(resources, R.color.cool_grey, null))
            val isUnderlineVisible = getBoolean(R.styleable.ProfileButton_profile_button_is_underline_visible, true)

            val titleParams = LayoutParams(0, 0)
            titleView = TextView(context)
            titleView.id = View.generateViewId()
            titleView.text = name
            TextViewCompat.setTextAppearance(titleView, R.style.TitleRoboto20Pt)
            titleView.gravity = Gravity.CENTER or Gravity.START


            //underline
            val underLineParams = LayoutParams(0,1)
            underLine = View(context)
            underLine.id = View.generateViewId()
            underLine.setBackgroundColor(buttonUnderlineColor)
            underLine.isVisible = isUnderlineVisible


            //icon
            val iconDims = (1.5 * resources.getDimensionPixelOffset(R.dimen._32dp)).toInt()
            val iconParams = LayoutParams( iconDims, iconDims)
            icon = ImageView(context)
            icon.id = View.generateViewId()
            icon.setImageDrawable(_icon)
            icon.setColorFilter(tintColor)
            icon.setPadding(30, 30, 30, 30)


            //iconParams
            iconParams.topToTop = 0
            iconParams.bottomToBottom = 0
            iconParams.startToStart = 0


            //underlineParams
            underLineParams.startToEnd = icon.id
            underLineParams.bottomToBottom = 0
            underLineParams.endToEnd = 0


            //titleParams
            titleParams.startToEnd = icon.id
            titleParams.topToTop = 0
            titleParams.bottomToBottom = 0
            titleParams.endToEnd = 0

            //addViews
            addView(icon, iconParams)
            addView(titleView, titleParams)
            addView(underLine, underLineParams)
        }
    }

}