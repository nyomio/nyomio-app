package nyomio.app.core.admin

import nyomio.admin.organization.Organization
import nyomio.admin.organization.OrganizationTable
import nyomio.admin.organization.OrganizationTable.shortName
import nyomio.commons.DbAccess
import nyomio.commons.revisionedentity.BaseDbService
import nyomio.commons.revisionedentity.Entity
import nyomio.commons.revisionedentity.EntityTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.statements.InsertStatement
import javax.inject.Singleton


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
