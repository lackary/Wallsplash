package com.lacklab.app.wallsplash.log

import timber.log.Timber

class LackLabProductionTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format("[%s#%s]",
            super.createStackElementTag(element),
            element.methodName)
    }
}