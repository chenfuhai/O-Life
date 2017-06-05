package com.olife.o_life.util;

import java.util.ArrayList;

/**
 * Created by chenfuhai on 2017/6/5 0005.
 */

public class Delete {
    private String tableName;
    private String[] WhereEquelTo;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getWhereEquelTos() {
        return WhereEquelTo;
    }

    public void setWhereEquelTo(String[] whereEquelTo) {
        WhereEquelTo = whereEquelTo;
    }
}
