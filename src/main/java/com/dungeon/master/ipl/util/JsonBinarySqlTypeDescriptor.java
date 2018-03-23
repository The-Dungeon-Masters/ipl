package com.dungeon.master.ipl.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonBinarySqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {

    /**
     *
     */
    private static final long serialVersionUID = -1019115460193680306L;
    public static final JsonBinarySqlTypeDescriptor INSTANCE = new JsonBinarySqlTypeDescriptor();

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {

            @Override
            protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options)
                throws SQLException {
                st.setObject(index, javaTypeDescriptor.unwrap(value, JsonNode.class, options), getSqlType());
            }

            @Override
            protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
                throws SQLException {
                st.setObject(name, javaTypeDescriptor.unwrap(value, JsonNode.class, options), getSqlType());
            }
        };
    }
}