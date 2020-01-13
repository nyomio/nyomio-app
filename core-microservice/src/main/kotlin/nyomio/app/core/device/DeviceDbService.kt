package nyomio.app.core.device

import io.reactivex.Single
import nyomio.admin.organization.OrganizationTable
import nyomio.app.core.admin.OrganizationDbService
import nyomio.app.core.devicemanager.getdeviceinfo.DeviceInfo
import nyomio.app.core.devicemanager.getdeviceinfo.Organization
import nyomio.commons.DbAccess
import nyomio.commons.revisionedentity.BaseDbService
import nyomio.commons.revisionedentity.EntityTable
import nyomio.commons.revisionedentity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import javax.inject.Singleton

class Device(
        var name: String,
        var imei: String,
        var organizationId: Long,
        entityId: Long? = null
) : Entity(entityId) {
    constructor(row: ResultRow) :
            this(
                    row[DeviceTable.name],
                    row[DeviceTable.imei],
                    row[DeviceTable.organizationId],
                    row[DeviceTable.entityId]
            )
}

object DeviceTable : EntityTable() {
    val name: Column<String> = varchar("name", 100)
    val imei: Column<String> = varchar("imei", 100)
    val organizationId: Column<Long> = long("organization_id")

    fun insertFrom(stmt: InsertStatement<Number>, device: Device) {
        stmt[name] = device.name
        stmt[imei] = device.imei
        stmt[organizationId] = device.organizationId
    }
}

@Singleton
class DeviceRevisionedDbService
constructor(private val dba: DbAccess,
            private val orgDbService: OrganizationDbService)
    : BaseDbService<Device, DeviceTable>(dba) {

    fun addOwn(organization: String, device: Device): Long {
        return orgDbService.getByShortName(organization).map { org ->
            device.organizationId = org.id!!
            add(device)

        }.blockingGet()
    }

    fun listOwnAt(organizationName: String, timestamp: Long = System.currentTimeMillis(), filter: String? = null) =
            orgDbService.getByShortName(organizationName).flatMap { org ->
                executeSelectQueryWith(atTimestamp(timestamp).filter(filter)
                        .andWhere { DeviceTable.organizationId.eq(org.id!!) })
            }

    fun getDeviceInfo(timestamp: Long, nativeId: String): Single<DeviceInfo> {
        return executeSelectQueryWithCustomMapping(
                DeviceTable.join(
                        OrganizationTable, JoinType.INNER, DeviceTable.organizationId,
                        OrganizationTable.entityId)
                        .selectAll()
                        .atTimestamp(timestamp)
                        .andWhere { DeviceTable.imei.eq(nativeId) }) {
            DeviceInfo(
                    nyomio.app.core.devicemanager.getdeviceinfo.Device(it[DeviceTable.entityId],
                            it[DeviceTable.name], it[DeviceTable.imei]),
                    Organization(it[OrganizationTable.entityId], it[OrganizationTable.shortName],
                            ""))
        }.map {
            it.first()
        }
    }

    override fun table() = DeviceTable

    override fun mapResultRowToEntity(resultRow: ResultRow) = Device(resultRow)

    override fun mapEntityToInsertStatement(stmt: InsertStatement<Number>, entity: Device) =
            DeviceTable.insertFrom(stmt, entity)

}
