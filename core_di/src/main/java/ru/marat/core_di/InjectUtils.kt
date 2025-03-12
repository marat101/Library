package ru.marat.core_di

object InjectUtils {

    private var _baseComponent: BaseComponent? = null

    val baseComponent: BaseComponent
        get() = _baseComponent ?: throw IllegalStateException("BaseComponent is not provided")

    fun provideAppComponent(component: BaseComponent) {
        if (_baseComponent == null) _baseComponent = component
        else throw IllegalStateException("BaseComponent is already provided")
    }

    inline fun <reified T> appDependencies(): T {
        if (baseComponent !is T)
            throw IllegalAccessException("AppComponent must implementation ${T::class.java.simpleName}")


        return baseComponent as (T & Any)
    }
}