import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration : RecyclerView.ItemDecoration {

    private var left: Int
    private var top: Int
    private var right: Int
    private var bottom: Int

    // Constructor for 1 value (same spacing for all sides)
    constructor(spacing: Int) {
        left = spacing
        top = spacing
        right = spacing
        bottom = spacing
    }

    // Constructor for 2 values (left/right, top/bottom)
    constructor(horizontal: Int, vertical: Int) {
        left = horizontal
        right = horizontal
        top = vertical
        bottom = vertical
    }

    // Constructor for 4 values (left, top, right, bottom)
    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(left, top, right, bottom)
    }
}
