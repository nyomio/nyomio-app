package nyomio.app.core.devicemanager.getdeviceinfo

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single
import nyomio.app.core.device.DeviceRevisionedDbService

@Controller("/api/v1/core/devicemanager/getdeviceinfo")
class GetDeviceInfoController(private val deviceDbService: DeviceRevisionedDbService): GetDeviceInfoDefinition {

    @Get("/{timestamp}/{nativeId}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    override fun execute(timestamp: Long, nativeId: String): Single<DeviceInfo> {
        return deviceDbService.getDeviceInfo(timestamp, nativeId)
    }
}
