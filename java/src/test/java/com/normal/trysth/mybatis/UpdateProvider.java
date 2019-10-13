package com.normal.trysth.mybatis;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author hf
 * @date 18-11-5
 */
public class UpdateProvider {
    public String updateSqlWithSimpleParam(String mobile) {
        return new SQL() {
            {
                UPDATE("sys_user");
                SET("mobile=#{mobile}");
                WHERE("user_id = 1");
            }
        }.toString();
    }

    public String updateSqlWithBeanParam() {
        return new SQL() {
            {
                UPDATE("sys_user");
                SET("mobile=#{bean.mobile}");
                WHERE("user_id = 1");
            }
        }.toString();
    }
}
