/*
 * This file is generated by jOOQ.
 */
package net.johanbasson.neo.database.jooq.tables.records;


import java.util.UUID;

import net.johanbasson.neo.database.jooq.tables.Users;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record4<UUID, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>neo.users.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>neo.users.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>neo.users.email</code>.
     */
    public void setEmail(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>neo.users.email</code>.
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>neo.users.hash</code>.
     */
    public void setHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>neo.users.hash</code>.
     */
    public String getHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>neo.users.role</code>.
     */
    public void setRole(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>neo.users.role</code>.
     */
    public String getRole() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Users.USERS.ID;
    }

    @Override
    public Field<String> field2() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.HASH;
    }

    @Override
    public Field<String> field4() {
        return Users.USERS.ROLE;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getEmail();
    }

    @Override
    public String component3() {
        return getHash();
    }

    @Override
    public String component4() {
        return getRole();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getEmail();
    }

    @Override
    public String value3() {
        return getHash();
    }

    @Override
    public String value4() {
        return getRole();
    }

    @Override
    public UsersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public UsersRecord value2(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord value3(String value) {
        setHash(value);
        return this;
    }

    @Override
    public UsersRecord value4(String value) {
        setRole(value);
        return this;
    }

    @Override
    public UsersRecord values(UUID value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(UUID id, String email, String hash, String role) {
        super(Users.USERS);

        setId(id);
        setEmail(email);
        setHash(hash);
        setRole(role);
    }
}
