package com.belows.dbkitdemo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by belows on 16/1/14.
 */
@DatabaseTable
public class DbItemData {

    @DatabaseField (index = true,generatedId = true)
    public long id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String course;
}
