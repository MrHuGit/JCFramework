package com.android.framework.jc.adapter.recyclerview;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-22 15:36
 * @describe
 * @update
 */
 class WrapData<T> {
    private T t;
    private int itemType;

     WrapData( int itemType,T t) {
        this.t = t;
        this.itemType = itemType;
    }

     T getData() {
        return t;
    }

     int getItemType() {
        return itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WrapData)) {
            return false;
        }

        WrapData<?> wrapData = (WrapData<?>) o;

        if (getItemType() != wrapData.getItemType()) {
            return false;
        }
        return t.equals(wrapData.t);
    }

    @Override
    public int hashCode() {
        int result = t.hashCode();
        result = 31 * result + getItemType();
        return result;
    }
}
