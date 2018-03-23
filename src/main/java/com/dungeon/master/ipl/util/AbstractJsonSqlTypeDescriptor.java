package com.dungeon.master.ipl.util;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public abstract class AbstractJsonSqlTypeDescriptor implements SqlTypeDescriptor {

    /**
     *
     */
    private static final long serialVersionUID = -6424323192829007052L;

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public boolean canBeRemapped() {
        return true;
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {

            @Override
            protected X doExtract(final ResultSet rs, final String name, final WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(rs.getObject(name), options);
            }

            @Override
            protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options)
                throws SQLException {
                return javaTypeDescriptor.wrap(statement.getObject(index), options);
            }

            @Override
            protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options)
                throws SQLException {
                return javaTypeDescriptor.wrap(statement.getObject(name), options);
            }
        };
    }
}