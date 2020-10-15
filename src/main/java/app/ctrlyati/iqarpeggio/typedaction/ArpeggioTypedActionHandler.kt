package app.ctrlyati.iqarpeggio.typedaction

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import kotlinx.coroutines.*
import java.util.*

class ArpeggioTypedActionHandler : TypedHandlerDelegate() {

    companion object {
        private var typed = ""
        private var endAt = 0L
        private var lastTyped = 0L

        private var startLocation = 0

        fun clearType() {
            typed = ""
        }
    }

    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        val now = Date().time
        if (now > endAt) {
            if(isStop(c)) {
                processType(editor)
            }
            println("clear")
            clearType()
        }
        if(typed.length > MAX_ARPEGGIO) {
            typed = typed.drop(1)
        }

        println("type $c")

        lastTyped = now
        endAt = lastTyped + ARPEGGIO_DELAY

        if(isStop(c)){
            processType(editor)
        } else {
            typed += c
        }

        return Result.DEFAULT
    }

    private fun isStop(c: Char) = c == STOP_CHAR && typed.length >= MIN_ARPEGGIO

    private fun processType(editor: Editor): Boolean {
        val template = getString(typed) ?: return false
        if (editor.document.isWritable) {
            val nowAt = editor.caretModel.offset
            editor.document.deleteString(nowAt - (typed.length + 1), nowAt)
            TemplateManager.getInstance(editor.project).startTemplate(editor, template)
//            editor.document.insertString(editor.caretModel.offset, string)
//            editor.caretModel.moveCaretRelatively(string.length, 0, false, false, false)
        }
        clearType()
        return true
    }

    private fun getString(typed: String): TemplateImpl? {
        TemplateSettings.getInstance().templates
            .filter { it.key.startsWith(TEMPLATE_PREFIX) }
            .map { it.key.removePrefix(TEMPLATE_PREFIX) to it }
            .forEach {
                println("compare ${it.first} to $typed")
                if (isAnagram(typed, it.first)) {
                    return it.second
                }
            }
        return null
    }

    private fun isAnagram(s1: String, s2: String): Boolean {
        if (s1 == s2) return true
        if (s1.length != s2.length) return false
        val a1 = s1.chars().sorted().toArray()
        val a2 = s2.chars().sorted().toArray()
        return a1 contentEquals a2
    }

}