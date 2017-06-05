package com.olife.test;

/**
 * 存放查询的参数 方便服务器解析 服务器和客户端都有一个一样的类 然后用Gson将信息传递上去
 * 依据里面的信息来制作SQL 语句
 *
 * order 正负开头代表正序 逆序 然后是属性名
 */
public class Query {
    private String tableName;
    private Integer limit;
    private String order;
    private String whereEqualTo;
    private Integer skip;





    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getWhereEqualTo() {
        return whereEqualTo;
    }

    public void setWhereEqualTo(String whereEqualTo) {
        this.whereEqualTo = whereEqualTo;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    @Override
    public String toString() {
        return "Query{" +
                "tableName='" + tableName + '\'' +
                ", limit=" + limit +
                ", order='" + order + '\'' +
                ", whereEqualTo='" + whereEqualTo + '\'' +
                ", skip=" + skip +
                '}';
    }
}
