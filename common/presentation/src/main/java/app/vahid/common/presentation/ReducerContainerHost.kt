package app.vahid.common.presentation

import app.vahid.common.presentation.pattern.Effect
import app.vahid.common.presentation.pattern.Event
import app.vahid.common.presentation.pattern.Intent
import app.vahid.common.presentation.pattern.Reducer
import app.vahid.common.presentation.pattern.SideEffect
import app.vahid.common.presentation.pattern.State
import kotlinx.coroutines.flow.Flow
import org.orbitmvi.orbit.ContainerHost

interface ReducerContainerHost<INTENT : Intent, STATE : State, EFFECT : Effect, SIDE_EFFECT : SideEffect, EVENT : Event> :
    ContainerHost<STATE, SIDE_EFFECT> {

    val reducer: Reducer<STATE, EFFECT>

    suspend fun handleIntent(intent: INTENT): Flow<EVENT>
}
