package klo0812.mlaserna.bayadmuna.pages.main.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TransactionItemDecoration(
    val verticalSpaceHeight: Int,
    val horizontalSpaceWidth: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpaceHeight
        outRect.right = horizontalSpaceWidth

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight
            outRect.left = horizontalSpaceWidth
        }
    }

}