package app.ctrlyati.iqarpeggio.state

import com.intellij.openapi.components.PersistentStateComponent

class ArpeggioPersistent : PersistentStateComponent<ArpeggioPersistent.State> {

    companion object {
        const val ARPEGGIO_DELAY = 100L // ms
        const val MAX_ARPEGGIO = 10 // chars
        const val MIN_ARPEGGIO = 3 // chars

        const val TEMPLATE_PREFIX = "iq>"

        private const val STOP_CHAR = ' '
    }

    private var state = State()

    data class State (
        var arpeggioDelay: Long = ARPEGGIO_DELAY,
        var arpeggioMax: Int = MAX_ARPEGGIO,
        var arpeggioMin: Int = MIN_ARPEGGIO,
        var templatePrefix: String = TEMPLATE_PREFIX,
        var stopChar: Char = STOP_CHAR
    )

    override fun getState(): State? {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

}