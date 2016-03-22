package clean.news.flow

import android.app.Activity
import android.content.Context
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import clean.news.R
import flow.Direction
import flow.KeyChanger
import flow.TraversalCallback

class SceneDispatcher(private val activity: Activity) : KeyChanger() {
	override fun changeKey(
			outgoingState: flow.State?,
			incomingState: flow.State,
			direction: Direction,
			incomingContexts: MutableMap<Any, Context>,
			callback: TraversalCallback) {

		val destination = incomingState.getKey<HasLayout>()
		val context = incomingContexts[destination]
		val layout = destination.getLayoutResId()
		val frame = activity.findViewById(R.id.container) as ViewGroup
		val incomingView = LayoutInflater.from(context).inflate(layout, frame, false)

		outgoingState?.save(frame.getChildAt(0))

		val transition = if (outgoingState != null) AutoTransition() else null
		val scene = Scene(frame, incomingView)

		TransitionManager.go(scene, transition)
		incomingState.restore(incomingView)

		callback.onTraversalCompleted()
	}
}
