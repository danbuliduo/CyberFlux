package io.cyberflux.meta.lang;

public class MetaObject implements MetaAttribute {
    protected MetaType type;

    public MetaObject() {
        type = MetaType.EMPTY;
    }

    public MetaObject(MetaType type) {
        this.type = type;
    }

    public MetaType type() {
        return type;
    }
}
