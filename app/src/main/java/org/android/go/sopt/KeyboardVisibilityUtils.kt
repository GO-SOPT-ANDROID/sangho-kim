package org.android.go.sopt

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window

// 구글링에서 발견한,, 엄청난 고수의 코드
// https://googry.tistory.com/m/43

class KeyboardVisibilityUtils(
    private val window: Window,
    private val onShowKeyboard: ((keyboardHeight: Int) -> Unit)? = null,
    private val onHideKeyboard: (() -> Unit)? = null
) {

    private val MIN_KEYBOARD_HEIGHT_PX = 150

    private val windowVisibleDisplayFrame = Rect()
    private var lastVisibleDecorViewHeight: Int = 0


    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        window.decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
        val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

        // Decide whether keyboard is visible from changing decor view height.
        if (lastVisibleDecorViewHeight != 0) {
            // 이전에 보여준 화면 height가 현재 화면 height + 최소 키보드 크기 값 보다 크면 키보드가 올라온 것으로 보고 키보드가 보였다는 이벤트를 전달
            if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
                val currentKeyboardHeight = window.decorView.height - windowVisibleDisplayFrame.bottom
                // Notify listener about keyboard being shown.
                onShowKeyboard?.invoke(currentKeyboardHeight)
            // 이전에 보여준 화면 height + 최소 키보드 크기 값이 현재 화면 height 보다 작으면 키보드가 내려간 것으로 보고 키보드가 사라졌다는 이벤트를 전달
            } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                // Notify listener about keyboard being hidden.
                onHideKeyboard?.invoke()
            }
        }
        // Save current decor view height for the next call.
        lastVisibleDecorViewHeight = visibleDecorViewHeight
    }

    init {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun detachKeyboardListeners() {
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}