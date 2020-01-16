package nyomio.app.core.devicemanager.getdeviceinfo

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.CookieValue
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single
import nyomio.app.core.device.DeviceRevisionedDbService

@Controller("/api/v1/core/devicemanager/getdeviceinfo")
@Secured(SecurityRule.IS_AUTHENTICATED)
class GetDeviceInfoController(private val deviceDbService: DeviceRevisionedDbService): GetDeviceInfoDefinition {

    @Get("/{nativeId}")
    @Secured("admin")
    override fun execute(@CookieValue JWT: String, nativeId: String): Single<DeviceInfo> {
        return deviceDbService.getDeviceInfo(nativeId)
    }
}
