package app.ctrlyati.iqarpeggio.setting

import app.ctrlyati.iqarpeggio.typedaction.ArpeggioTypedActionHandler
import com.intellij.openapi.options.Configurable
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JEditorPane
import javax.swing.JLabel
import javax.swing.JPanel

class ApplicationSettingConfigurable : Configurable {

    lateinit var component: JComponent
    lateinit var title: JLabel
    lateinit var input: JEditorPane

    override fun createComponent(): JComponent? {
        component = JPanel()
        component.layout = BorderLayout()

        title = JLabel().apply {
            text = displayName
        }

        input = JEditorPane().apply {
            toolTipText = ArpeggioTypedActionHandler.ARPEGGIO_DELAY.toString()
        }

        component.add(title)
        component.add(input, BorderLayout.EAST)

        return component
    }

    override fun isModified(): Boolean = input.text != ArpeggioTypedActionHandler.ARPEGGIO_DELAY.toString()

    override fun apply() {

    }

    override fun getDisplayName(): String = "Set the delay between keys before reset"

}