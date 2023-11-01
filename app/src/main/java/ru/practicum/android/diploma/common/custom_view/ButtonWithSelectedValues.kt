package ru.practicum.android.diploma.common.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.custom_view.model.ButtonWithSelectedValuesState
import ru.practicum.android.diploma.databinding.ViewButtonWithSelectedValuesBinding

class ButtonWithSelectedValues @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewButtonWithSelectedValuesBinding.inflate(
        LayoutInflater.from(context), this
    )

    fun render(state: ButtonWithSelectedValuesState) {
        when (state) {
            is ButtonWithSelectedValuesState.Content -> {
                binding.hintBig.text = state.hint
                binding.hintSmall.text = state.hint
                binding.text.text = state.text
                binding.text.isVisible = true
                binding.hintSmall.isVisible = true
                binding.hintBig.isVisible = false
                binding.icon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_cross, null
                    )
                )
            }

            is ButtonWithSelectedValuesState.Empty -> {
                binding.hintBig.text = state.hint
                binding.hintSmall.text = state.hint
                binding.text.isVisible = false
                binding.hintSmall.isVisible = false
                binding.hintBig.isVisible = true
                binding.icon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_chevron_right, null
                    )
                )
            }
        }
    }

    fun onButtonClick(click: () -> Unit) {
        binding.icon.setOnClickListener {
            click.invoke()
        }
    }
}
