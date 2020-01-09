package nyomio.app.core

import nyomio.app.core.device.DeviceTable
import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import nyomio.commons.DbAccess
import org.slf4j.LoggerFactory
import javax.inject.Singleton

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        LoggerFactory.getLogger("test").info("Envvarval:" + System.getenv("JWT_SECRET_KEY"))
        Micronaut.build()
                .packages("")
                .mainClass(Application.javaClass)
                .start()
    }
}

@Singleton
class DbConfiguration(private val dbAccess: DbAccess) {
    @EventListener
    fun onStartup(event: ServerStartupEvent) {
        dbAccess.setTableListProvider { arrayOf(DeviceTable) }
    }
}
