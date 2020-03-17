package es.afmsoft.moviesapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule(okHttpClient: OkHttpClient) : TestRule {

    private val resource: IdlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)

    override fun apply(base: Statement, description: Description): Statement {
            return object : Statement() {
                override fun evaluate() {
                    IdlingRegistry.getInstance().register(resource)
                    base.evaluate()
                    IdlingRegistry.getInstance().unregister(resource)
                }
            }
    }
}
