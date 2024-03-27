package com.web.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class RemindDynamicSqlSupport {
    
    public static final Remind remind = new Remind();

    
    public static final SqlColumn<Integer> id = remind.id;

    
    public static final SqlColumn<String> time = remind.time;

    
    public static final class Remind extends AliasableSqlTable<Remind> {
        public final SqlColumn<Integer> id = column("t_id", JDBCType.INTEGER);

        public final SqlColumn<String> time = column("t_time", JDBCType.TIME);

        public Remind() {
            super("t_remind", Remind::new);
        }
    }
}