package com.olife.o_life.util;

import java.util.ArrayList;

/**
 * 删除的条件类 可以构造jSON发送给服务器 服务器根据这个可以形成SQL语句
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
