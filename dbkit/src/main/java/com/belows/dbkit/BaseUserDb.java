package com.belows.dbkit;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by belows on 16/1/14.
 */
public abstract class BaseUserDb<T, ID> extends BaseDb<T, ID> {

    private long mUid = -1;

    public BaseUserDb(ConnectionSource connectionSource) {
        super(connectionSource);
    }

    public void updateUser(long uid) {
        if (this.mUid == uid) {
            return;
        }
        this.mUid = uid;
        switchUser();
    }

    private void switchUser() {
        init();
    }

    @Override
    protected String tableName() {
        return getDataClass().getSimpleName() + "_" + mUid + "_database";
    }
}
