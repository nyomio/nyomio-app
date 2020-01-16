package nyomio.app.core.devicemanager.getdeviceinfo

import io.micronaut.http.annotation.CookieValue
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.reactivex.Single

interface GetDeviceInfoDefinition {

    @Get("/{nativeId}")
    fun execute(@CookieValue JWT: String, nativeId: String): Single<DeviceInfo>
}
