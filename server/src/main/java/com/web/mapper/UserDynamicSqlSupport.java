package com.web.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UserDynamicSqlSupport {
    
    public static final User user = new User();

    
    public static final SqlColumn<Long> id = user.id;

    
    public static final SqlColumn<String> username = user.username;

    
    public static final SqlColumn<String> password = user.password;

    
    public static final SqlColumn<String> type = user.type;

    
    public static final SqlColumn<String> phone = user.phone;

    
    public static final SqlColumn<Byte> lock = user.lock;

    
    public static final SqlColumn<Byte> smsNotice = user.smsNotice;

    
    public static final SqlColumn<Byte> phoneNotice = user.phoneNotice;

    
    public static final class User extends AliasableSqlTable<User> {
        public final SqlColumn<Long> id = column("t_id", JDBCType.BIGINT);

        public final SqlColumn<String> username = column("t_username", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("t_password", JDBCType.VARCHAR);

        public final SqlColumn<String> type = column("t_type", JDBCType.VARCHAR);

        public final SqlColumn<String> phone = column("t_phone", JDBCType.CHAR);

        public final SqlColumn<Byte> lock = column("t_lock", JDBCType.TINYINT);

        public final SqlColumn<Byte> smsNotice = column("t_sms_notice", JDBCType.TINYINT);

        public final SqlColumn<Byte> phoneNotice = column("t_phone_notice", JDBCType.TINYINT);

        public User() {
            super("t_user", User::new);
        }
    }
}