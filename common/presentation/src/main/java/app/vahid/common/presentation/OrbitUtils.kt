package app.vahid.common.presentation

import app.vahid.common.presentation.pattern.Effect
import app.vahid.common.presentation.pattern.Event
import app.vahid.common.presentation.pattern.Intent
import app.vahid.common.presentation.pattern.SideEffect
import app.vahid.common.presentation.pattern.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@Suppress("UNCHECKED_CAST")
fun <INTENT : Intent, STATE : State, EFFECT : Effect, SIDE_EFFECT : SideEffect, EVENT : Event>
        ReducerContainerHost<INTENT, STATE, EFFECT, SIDE_EFFECT, EVENT>.dispatchIntent(
    intent: INTENT,
) =
    intent {
        handleIntent(intent)
            .onEach { event ->
                when (event) {
                    is SideEffect -> postSideEffect(event as SIDE_EFFECT)
                    is Effect -> reduce {
                        reducer.reduce(state, event as EFFECT)
                    }
                }

            }.collect()
    }
