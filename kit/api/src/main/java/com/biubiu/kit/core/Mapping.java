package com.biubiu.kit.core;

/**
 * mapping
 * Created by looa on 2018/4/11.
 */

public class Mapping {
    private String dataClassName;
    private String kitClassName;

    public Mapping(String dataClassName, String kitClassName) {
        this.dataClassName = dataClassName;
        this.kitClassName = kitClassName;
    }

    public boolean isEquals(Class<?> data) {
        return data != null && data.getName().equals(this.dataClassName);
    }

    public String getKitClassName() {
        return kitClassName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mapping mapping = (Mapping) o;

        if (dataClassName != null ? !dataClassName.equals(mapping.dataClassName) : mapping.dataClassName != null)
            return false;
        return kitClassName != null ? kitClassName.equals(mapping.kitClassName) : mapping.kitClassName == null;
    }

    @Override
    public int hashCode() {
        int result = dataClassName != null ? dataClassName.hashCode() : 0;
        result = 31 * result + (kitClassName != null ? kitClassName.hashCode() : 0);
        return result;
    }
}
