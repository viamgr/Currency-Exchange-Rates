package app.vahid.common.presentation.pattern


interface Reducer<STATE : State, EFFECT : Effect> {
    fun reduce(state: STATE, effect: EFFECT): STATE
}