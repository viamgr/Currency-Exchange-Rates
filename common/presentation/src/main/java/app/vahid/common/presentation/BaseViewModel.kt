package app.vahid.common.presentation

import androidx.lifecycle.ViewModel
import app.vahid.common.presentation.pattern.Effect
import app.vahid.common.presentation.pattern.Event
import app.vahid.common.presentation.pattern.Intent
import app.vahid.common.presentation.pattern.SideEffect
import app.vahid.common.presentation.pattern.State
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel<INTENT : Intent, STATE : State, EFFECT : Effect, SIDE_EFFECT : SideEffect, EVENT : Event>(
    initialState: STATE,
) : ViewModel(),
    ReducerContainerHost<INTENT, STATE, EFFECT, SIDE_EFFECT, EVENT> {

    override val container: Container<STATE, SIDE_EFFECT> = container(initialState)

}