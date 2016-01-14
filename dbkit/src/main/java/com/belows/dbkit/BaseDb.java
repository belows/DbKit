package com.belows.dbkit;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by belows on 16/1/14.
 */
public abstract class BaseDb<T, ID> {

    private final ConnectionSource mConnectionSource;
    private Dao<T, ID> mDao;

    private final static String TAG = "tag_db";

    public BaseDb(ConnectionSource connectionSource) {
        mConnectionSource = connectionSource;
    }

    public synchronized void init() {
        try {
            unregisterDao(mDao);
            DatabaseTableConfig<T> tableConfig = tableConfig();
            TableUtils.createTableIfNotExists(mConnectionSource, tableConfig);
            mDao = DaoManager.createDao(mConnectionSource, tableConfig);
        } catch (Exception e) {
            Log.e(TAG, getDataClass().getSimpleName() + " init error:" + e);
        }
    }

    public synchronized void unInit() {
        unregisterDao(mDao);
        mDao = null;
        try {
            mConnectionSource.close();
        } catch (Exception e) {
            Log.e(TAG, "db close error:" + e.toString());
        }
    }

    public synchronized void save(T data) {
        try {
            mDao.createIfNotExists(data);
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "save data error:" + e);
        }
    }

    public synchronized void save(final Collection<T> dataList) {
        try {
            mDao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (T t : dataList) {
                        mDao.createIfNotExists(t);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "save data list error:" + e);
        }
    }

    public synchronized void update(T data) {
        try {
            mDao.update(data);
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "update data error:" + e);
        }
    }

    public synchronized void delete(T data) {
        try {
            mDao.delete(data);
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "delete data error:" + e);
        }
    }

    public synchronized void delete(Collection<T> dataList) {
        try {
            mDao.delete(dataList);
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "delete data list error:" + e);
        }
    }

    public synchronized List<T> queryAll() {
        try {
            return mDao.queryForAll();
        } catch (Exception e) {
            Log.e(getDataClass().getSimpleName(), "query all error:" + e);
            return null;
        }
    }

    private synchronized void unregisterDao(Dao<?, ?> dao) {
        if (dao != null) {
            DaoManager.unregisterDao(mConnectionSource, dao);
        }
    }

    private synchronized DatabaseTableConfig<T> tableConfig() {
        return new DatabaseTableConfig<T>(getDataClass(), tableName(), null);
    }

    protected abstract String tableName();

    protected abstract Class<T> getDataClass();
}
