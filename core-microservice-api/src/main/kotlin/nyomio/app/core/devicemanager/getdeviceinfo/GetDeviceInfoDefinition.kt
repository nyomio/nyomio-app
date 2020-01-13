package nyomio.app.core.devicemanager.getdeviceinfo

import io.reactivex.Single

interface GetDeviceInfoDefinition {

    fun execute(timestamp: Long, nativeId: String): Single<DeviceInfo>
}
