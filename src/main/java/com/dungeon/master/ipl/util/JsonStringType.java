package com.dungeon.master.ipl.util;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

public class JsonStringType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    private static final long serialVersionUID = -1675725308279251090L;

    public JsonStringType() {
        super(JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public void setParameterValues(final Properties parameters) {
        ((JsonTypeDescriptor)getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
