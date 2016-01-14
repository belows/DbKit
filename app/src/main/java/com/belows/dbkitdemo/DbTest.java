package com.belows.dbkitdemo;

import com.belows.dbkit.BaseDb;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by belows on 16/1/14.
 */
public class DbTest extends BaseDb<DbItemData,Long> {

    public DbTest(ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    protected String tableName() {
        return "dbtest";
    }

    @Override
    protected Class<DbItemData> getDataClass() {
        return DbItemData.class;
    }
}
