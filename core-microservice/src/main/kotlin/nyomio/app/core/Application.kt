package nyomio.app.core

import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import nyomio.commons.DbAccess
import javax.inject.Singleton

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("nyomio.app.core")
                .mainClass(Application.javaClass)
                .start()
    }
}

@Singleton
class DbConfiguration(private val dbAccess: DbAccess) {
    @EventListener
    fun onStartup(event: ServerStartupEvent) {
//        dbAccess.setTableListProvider { arrayOf(OrganizationTable, UserTable ) }
    }
}
