package nyomio.app.core.admin

import nyomio.app.core.admin.OrganizationTable.shortName
import nyomio.commons.DbAccess
import nyomio.commons.revisionedentity.BaseDbService
import nyomio.commons.revisionedentity.Entity
import nyomio.commons.revisionedentity.EntityTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.statements.InsertStatement
import javax.inject.Singleton

class Organization(val org_name: String, val org_shortName: String, val org_address: String, entityId: Long? = null,
                   val revisionId: Long? = null) : Entity(entityId) {
    constructor(row: ResultRow) :
            this(row[OrganizationTable.name],
                    row[OrganizationTable.shortName],
                    row[OrganizationTable.address],
                    row[OrganizationTable.entityId])
}

object OrganizationTable : EntityTable() {
    val name: Column<String> = varchar("name", 100)
    val shortName: Column<String> = varchar("shortName", 10)
    val address: Column<String> = varchar("address", 200)

    fun insertFrom(stmt: InsertStatement<Number>, organization: Organization) {
        stmt[name] = organization.org_name
        stmt[shortName] = organization.org_shortName
        stmt[address] = organization.org_address
    }
}

@Singleton
class OrganizationDbService
constructor(private val dba: DbAccess)
    : BaseDbService<Organization, OrganizationTable>(dba) {

    override fun table() = OrganizationTable

    override fun mapResultRowToEntity(resultRow: ResultRow) = Organization(resultRow)

    override fun mapEntityToInsertStatement(stmt: InsertStatement<Number>, entity: Organization) =
            OrganizationTable.insertFrom(stmt, entity)

    fun getByShortName(organizationName: String) =
            executeSelectQueryWith(atTimestamp(System.currentTimeMillis()).andWhere { shortName eq organizationName })
                    .map { it.first() }

}
