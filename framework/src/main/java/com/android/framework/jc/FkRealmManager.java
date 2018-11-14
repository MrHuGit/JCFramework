package com.android.framework.jc;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.annotations.RealmModule;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 10:04
 * @describe framework realm数据库管理类
 * @update
 */
public class FkRealmManager {
    private final Realm mRealm;

    private FkRealmManager() {
        Realm.init(JcFramework.getInstance().getApplication().getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("gesture.realm")
                .schemaVersion(0)
                .modules(Realm.getDefaultModule(), new FkRealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        mRealm = Realm.getInstance(realmConfiguration);
    }

    @RealmModule(library = true, allClasses = true)
    private final static class FkRealmModule {
    }

    private final static class Holder {
        private final static FkRealmManager INSTANCE = new FkRealmManager();
    }


    protected static FkRealmManager getInstance() {
        return Holder.INSTANCE;
    }

    protected void executeTransaction(Realm.Transaction transaction) {
        mRealm.executeTransaction(transaction);
    }

    /**
     * 更新或添加数据
     *
     * @param t
     *         数据
     * @param <T>
     *         数据类型
     */
    protected <T extends RealmModel> void copyToRealmOrUpdate(T t) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(t));

    }

    /**
     * 更新或添加数据
     *
     * @param list
     *         数据集合
     * @param <T>
     *         数据类型
     */
    protected <T extends RealmModel> void copyToRealmOrUpdate(List<T> list) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    /**
     * 查询数据
     *
     * @param tClass
     *         数据类
     * @param <T>
     *         数据类
     *
     * @return 查询
     */
    protected <T extends RealmModel> RealmQuery<T> query(Class<T> tClass) {
        return mRealm.where(tClass);
    }


    /**
     * 从数据复制数据
     *
     * @param realmObject
     *         数据
     * @param <T>
     *         泛型
     *
     * @return 复制后的数据
     */
    protected <T extends RealmModel> T copyFromRealm(T realmObject) {
        return mRealm.copyFromRealm(realmObject);
    }

    /**
     * 从数据复制数据集合
     *
     * @param realmObjects
     *         数据集合
     * @param <T>
     *         泛型
     *
     * @return 复制后的数据集合
     */
    protected <T extends RealmModel> List<T> copyFromRealm(Iterable<T> realmObjects) {
        return mRealm.copyFromRealm(realmObjects);
    }


    protected Realm getRealm() {
        return mRealm;
    }
}
