package com.dungeon.master.ipl.util;

import java.util.Properties;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

/*
 * This class is responsible for transforming the JSON Object type used in the entity mapping classes to a format that is
 * supported by the underlying database.
 */
public class JsonTypeDescriptor extends AbstractTypeDescriptor<Object> implements DynamicParameterizedType {

    private static final long serialVersionUID = -5268666263475948968L;
    /**
     * jsonObjectClass holds the underlying entity mapping JSON Object type.
     */
    private Class<?> jsonObjectClass;

    @Override
    public void setParameterValues(final Properties parameters) {
        jsonObjectClass = ((ParameterType)parameters.get(PARAMETER_TYPE)).getReturnedClass();
    }

    public JsonTypeDescriptor() {
        /**
         * The MutableMutabilityPlan specifies that the JSON Object can be updated.
         */
        super(Object.class, new MutableMutabilityPlan<Object>() {

            private static final long serialVersionUID = 4664870521370094336L;

            @Override
            protected Object deepCopyNotNull(final Object value) {
                return JacksonUtil.clone(value);
            }
        });
    }

    /**
     * This method determines if two JSON Object(s) are equivalent.
     */
    @Override
    public boolean areEqual(final Object one, final Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        return JacksonUtil.toJsonNode(JacksonUtil.toString(one))
            .equals(JacksonUtil.toJsonNode(JacksonUtil.toString(another)));
    }

    /**
     * This method allows Hibernate to converts a JSON Object to a String
     */
    @Override
    public String toString(final Object value) {
        return JacksonUtil.toString(value);
    }

    /**
     * This method allows Hibernate to reconstruct a JSON Object from its String representation.
     */
    @Override
    public Object fromString(final String string) {
        return JacksonUtil.fromString(string, jsonObjectClass);
    }

    /**
     * Unwrap a Json Object. The unwrap method is used prior to materializing the JSON Object to a format that is expected by
     * the currently used relational database. For this reason, we can unwrap a JSON Object to either a String or a JsonNode
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(final Object value, final Class<X> type, final WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X)toString(value);
        }
        if (Object.class.isAssignableFrom(type)) {
            return (X)JacksonUtil.toJsonNode(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Object wrap(final X value, final WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return fromString(value.toString());
    }
}