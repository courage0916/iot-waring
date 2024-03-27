package com.web.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class DeviceDynamicSqlSupport {
    
    public static final Device device = new Device();

    
    public static final SqlColumn<Long> id = device.id;

    
    public static final SqlColumn<String> deviceId = device.deviceId;

    
    public static final SqlColumn<Long> userId = device.userId;

    
    public static final SqlColumn<String> phone = device.phone;
    public static final SqlColumn<String> phone2 = device.phone2;
    public static final SqlColumn<String> phone3 = device.phone3;

    public static final SqlColumn<String> phone4 = device.phone4;
    public static final SqlColumn<String> phone5 = device.phone5;
    public static final SqlColumn<String> phone6 = device.phone6;
    public static final SqlColumn<String> phone7 = device.phone7;
    public static final SqlColumn<String> phone8 = device.phone8;
    public static final SqlColumn<String> phone9 = device.phone9;
    public static final SqlColumn<String> phone10 = device.phone10;


    public static final SqlColumn<String> remark = device.remark;

    
    public static final SqlColumn<Byte> smsNotice = device.smsNotice;

    
    public static final SqlColumn<Byte> phoneNotice = device.phoneNotice;

    
    public static final SqlColumn<Integer> interval = device.interval;

    
    public static final SqlColumn<String> status = device.status;

    
    public static final class Device extends AliasableSqlTable<Device> {
        public final SqlColumn<Long> id = column("t_id", JDBCType.BIGINT);

        public final SqlColumn<String> deviceId = column("t_device_id", JDBCType.VARCHAR);

        public final SqlColumn<Long> userId = column("t_user_id", JDBCType.BIGINT);

        public final SqlColumn<String> phone = column("t_phone", JDBCType.VARCHAR);
        public final SqlColumn<String> phone2 = column("t_phone2", JDBCType.VARCHAR);
        public final SqlColumn<String> phone3 = column("t_phone3", JDBCType.VARCHAR);

        public final SqlColumn<String> phone4 = column("t_phone4", JDBCType.VARCHAR);
        public final SqlColumn<String> phone5 = column("t_phone5", JDBCType.VARCHAR);
        public final SqlColumn<String> phone6 = column("t_phone6", JDBCType.VARCHAR);
        public final SqlColumn<String> phone7 = column("t_phone7", JDBCType.VARCHAR);
        public final SqlColumn<String> phone8 = column("t_phone8", JDBCType.VARCHAR);
        public final SqlColumn<String> phone9 = column("t_phone9", JDBCType.VARCHAR);
        public final SqlColumn<String> phone10 = column("t_phone10", JDBCType.VARCHAR);

        public final SqlColumn<String> remark = column("t_remark", JDBCType.CHAR);

        public final SqlColumn<Byte> smsNotice = column("t_sms_notice", JDBCType.TINYINT);

        public final SqlColumn<Byte> phoneNotice = column("t_phone_notice", JDBCType.TINYINT);

        public final SqlColumn<Integer> interval = column("t_interval", JDBCType.INTEGER);

        public final SqlColumn<String> status = column("t_status", JDBCType.VARCHAR);

        public Device() {
            super("t_device", Device::new);
        }
    }
}