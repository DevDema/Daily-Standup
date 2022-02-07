package com.andreadematteis.dailystandup.data.local.model

import androidx.room.*

@Entity(
    tableName = "team_member_table",
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("team_id")
    )],
    indices = [Index(value = ["team_id"])]
)
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "team_id")
    val teamId: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamMember

        if (name != other.name) return false
        if (surname != other.surname) return false
        if (teamId != other.teamId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + teamId.hashCode()
        return result
    }
}