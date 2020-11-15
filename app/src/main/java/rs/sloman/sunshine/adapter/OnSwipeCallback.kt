package rs.sloman.sunshine.adapter

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import rs.sloman.sunshine.viewmodels.FavoritesViewModel
import timber.log.Timber


class OnSwipeCallback(private val viewModel: FavoritesViewModel, private val context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,

    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Get RecyclerView item from the ViewHolder
            val itemView = viewHolder.itemView

            val p: Paint = Paint()
            p.color = getThemeAccentColor(context)
            if (dX > 0) {
                // Draw Rect with varying right side, equal to displacement dX
                c.drawRect(
                    itemView.left.toFloat(), itemView.top.toFloat(), dX,
                    itemView.bottom.toFloat(), p
                )
            } else {
                // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                c.drawRect(
                    itemView.right + dX, itemView.top.toFloat(),
                    itemView.right.toFloat(), itemView.bottom.toFloat(), p
                )
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Timber.d("Slo onsWiped ${viewHolder.adapterPosition}")


        val favorite = viewModel.getFavFromPosition(viewHolder.adapterPosition)
        viewModel.removeFavCity(favorite)

        Timber.d("Slo onsWiped ${viewModel.favList.value?.size}")

    }

    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorBackground, value, true)
        return value.data
    }
}