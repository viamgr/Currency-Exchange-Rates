package app.vahid.common.presentation.fake

import app.vahid.common.presentation.pattern.Effect
import app.vahid.common.presentation.pattern.Intent
import app.vahid.common.presentation.pattern.State

sealed interface FakeIntent : Intent

object IntentOne : FakeIntent

object IntentTwo : FakeIntent

data class FakeCounterState(val count: Int) : State

sealed interface FakeEffect : Effect

object FakeEffect1 : FakeEffect

object FakeEffect2 : FakeEffect
