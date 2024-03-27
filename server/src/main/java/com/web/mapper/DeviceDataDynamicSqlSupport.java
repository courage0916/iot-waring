package com.web.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class DeviceDataDynamicSqlSupport {
    
    public static final DeviceData deviceData = new DeviceData();

    
    public static final SqlColumn<Long> id = deviceData.id;

    
    public static final SqlColumn<String> deviceId = deviceData.deviceId;

    
    public static final SqlColumn<Date> createTime = deviceData.createTime;

    public static final SqlColumn<String> content = deviceData.content;

    
    public static final class DeviceData extends AliasableSqlTable<DeviceData> {
        public final SqlColumn<Long> id = column("t_id", JDBCType.BIGINT);

        public final SqlColumn<String> deviceId = column("t_device_id", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("t_create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> content = column("t_content", JDBCType.VARCHAR);

        public DeviceData() {
            super("t_device_data", DeviceData::new);
        }
    }
}