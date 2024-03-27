package com.web.mapper;

import static com.web.mapper.DeviceDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.web.model.po.Device;
import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface DeviceMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<Device>, CommonUpdateMapper {
    
    BasicColumn[] selectList = BasicColumn.columnList(id, deviceId, userId, phone,phone2,phone3,phone4,phone5,phone6,phone7,phone8,phone9,phone10, remark, smsNotice, phoneNotice, interval, status);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="DeviceResult", value = {
        @Result(column="t_id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="t_device_id", property="deviceId", jdbcType=JdbcType.VARCHAR),
        @Result(column="t_user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="t_phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone2", property="phone2", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone3", property="phone3", jdbcType=JdbcType.VARCHAR),

            @Result(column="t_phone4", property="phone4", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone5", property="phone5", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone6", property="phone6", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone7", property="phone7", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone8", property="phone8", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone9", property="phone9", jdbcType=JdbcType.VARCHAR),
            @Result(column="t_phone10", property="phone10", jdbcType=JdbcType.VARCHAR),

        @Result(column="t_remark", property="remark", jdbcType=JdbcType.CHAR),
        @Result(column="t_sms_notice", property="smsNotice", jdbcType=JdbcType.TINYINT),
        @Result(column="t_phone_notice", property="phoneNotice", jdbcType=JdbcType.TINYINT),
        @Result(column="t_interval", property="interval", jdbcType=JdbcType.INTEGER),
        @Result(column="t_status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<Device> selectMany(SelectStatementProvider selectStatement);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("DeviceResult")
    Optional<Device> selectOne(SelectStatementProvider selectStatement);

    
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, device, completer);
    }

    
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, device, completer);
    }

    
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int insert(Device row) {
        return MyBatis3Utils.insert(this::insert, row, device, c ->
            c.map(id).toProperty("id")
            .map(deviceId).toProperty("deviceId")
            .map(userId).toProperty("userId")
            .map(phone).toProperty("phone")
                    .map(phone2).toProperty("phone2")
                    .map(phone3).toProperty("phone3")

                    .map(phone4).toProperty("phone4")
                    .map(phone5).toProperty("phone5")
                    .map(phone6).toProperty("phone6")
                    .map(phone7).toProperty("phone7")
                    .map(phone8).toProperty("phone8")
                    .map(phone9).toProperty("phone9")
                    .map(phone10).toProperty("phone10")

            .map(remark).toProperty("remark")
            .map(smsNotice).toProperty("smsNotice")
            .map(phoneNotice).toProperty("phoneNotice")
            .map(interval).toProperty("interval")
            .map(status).toProperty("status")
        );
    }

    
    default int insertMultiple(Collection<Device> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, device, c ->
            c.map(id).toProperty("id")
            .map(deviceId).toProperty("deviceId")
            .map(userId).toProperty("userId")
            .map(phone).toProperty("phone") .map(phone2).toProperty("phone2")
                    .map(phone3).toProperty("phone3")

                    .map(phone4).toProperty("phone4")
                    .map(phone5).toProperty("phone5")
                    .map(phone6).toProperty("phone6")
                    .map(phone7).toProperty("phone7")
                    .map(phone8).toProperty("phone8")
                    .map(phone9).toProperty("phone9")
                    .map(phone10).toProperty("phone10")
            .map(remark).toProperty("remark")
            .map(smsNotice).toProperty("smsNotice")
            .map(phoneNotice).toProperty("phoneNotice")
            .map(interval).toProperty("interval")
            .map(status).toProperty("status")
        );
    }

    
    default int insertSelective(Device row) {
        return MyBatis3Utils.insert(this::insert, row, device, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(deviceId).toPropertyWhenPresent("deviceId", row::getDeviceId)
            .map(userId).toPropertyWhenPresent("userId", row::getUserId)
            .map(phone).toPropertyWhenPresent("phone", row::getPhone)
                    .map(phone2).toPropertyWhenPresent("phone2", row::getPhone2)
                    .map(phone3).toPropertyWhenPresent("phone3", row::getPhone3)

                    .map(phone4).toPropertyWhenPresent("phone4", row::getPhone4)
                    .map(phone5).toPropertyWhenPresent("phone5", row::getPhone5)
                    .map(phone6).toPropertyWhenPresent("phone6", row::getPhone6)
                    .map(phone7).toPropertyWhenPresent("phone7", row::getPhone7)
                    .map(phone8).toPropertyWhenPresent("phone8", row::getPhone8)
                    .map(phone9).toPropertyWhenPresent("phone9", row::getPhone9)
                    .map(phone10).toPropertyWhenPresent("phone10", row::getPhone10)

            .map(remark).toPropertyWhenPresent("remark", row::getRemark)
            .map(smsNotice).toPropertyWhenPresent("smsNotice", row::getSmsNotice)
            .map(phoneNotice).toPropertyWhenPresent("phoneNotice", row::getPhoneNotice)
            .map(interval).toPropertyWhenPresent("interval", row::getInterval)
            .map(status).toPropertyWhenPresent("status", row::getStatus)
        );
    }

    
    default Optional<Device> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, device, completer);
    }

    
    default List<Device> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, device, completer);
    }

    
    default List<Device> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, device, completer);
    }

    
    default Optional<Device> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, device, completer);
    }

    
    static UpdateDSL<UpdateModel> updateAllColumns(Device row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(deviceId).equalTo(row::getDeviceId)
                .set(userId).equalTo(row::getUserId)
                .set(phone).equalTo(row::getPhone)
                .set(phone2).equalTo(row::getPhone2)
                .set(phone3).equalTo(row::getPhone3)
                .set(phone4).equalTo( row::getPhone4)
                .set(phone5).equalTo(row::getPhone5)
                .set(phone6).equalTo(row::getPhone6)
                .set(phone7).equalTo( row::getPhone7)
                .set(phone8).equalTo(row::getPhone8)
                .set(phone9).equalTo( row::getPhone9)
                .set(phone10).equalTo( row::getPhone10)
                .set(remark).equalTo(row::getRemark)
                .set(smsNotice).equalTo(row::getSmsNotice)
                .set(phoneNotice).equalTo(row::getPhoneNotice)
                .set(interval).equalTo(row::getInterval)
                .set(status).equalTo(row::getStatus);
    }

    
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Device row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(deviceId).equalToWhenPresent(row::getDeviceId)
                .set(userId).equalToWhenPresent(row::getUserId)
                .set(phone).equalToWhenPresent(row::getPhone)
                .set(phone2).equalToWhenPresent(row::getPhone2)
                .set(phone3).equalToWhenPresent(row::getPhone3)
                .set(phone4).equalToWhenPresent( row::getPhone4)
                .set(phone5).equalToWhenPresent(row::getPhone5)
                .set(phone6).equalToWhenPresent(row::getPhone6)
                .set(phone7).equalToWhenPresent( row::getPhone7)
                .set(phone8).equalToWhenPresent(row::getPhone8)
                .set(phone9).equalToWhenPresent( row::getPhone9)
                .set(phone10).equalToWhenPresent( row::getPhone10)
                .set(remark).equalToWhenPresent(row::getRemark)
                .set(smsNotice).equalToWhenPresent(row::getSmsNotice)
                .set(phoneNotice).equalToWhenPresent(row::getPhoneNotice)
                .set(interval).equalToWhenPresent(row::getInterval)
                .set(status).equalToWhenPresent(row::getStatus);
    }

    
    default int updateByPrimaryKey(Device row) {
        return update(c ->
            c.set(deviceId).equalTo(row::getDeviceId)
            .set(userId).equalTo(row::getUserId)
            .set(phone).equalTo(row::getPhone)
                    .set(phone2).equalTo(row::getPhone2)
                    .set(phone3).equalTo(row::getPhone3)

                    .set(phone4).equalTo( row::getPhone4)
                    .set(phone5).equalTo(row::getPhone5)
                    .set(phone6).equalTo(row::getPhone6)
                    .set(phone7).equalTo( row::getPhone7)
                    .set(phone8).equalTo(row::getPhone8)
                    .set(phone9).equalTo( row::getPhone9)
                    .set(phone10).equalTo( row::getPhone10)
            .set(remark).equalTo(row::getRemark)
            .set(smsNotice).equalTo(row::getSmsNotice)
            .set(phoneNotice).equalTo(row::getPhoneNotice)
            .set(interval).equalTo(row::getInterval)
            .set(status).equalTo(row::getStatus)
            .where(id, isEqualTo(row::getId))
        );
    }

    
    default int updateByPrimaryKeySelective(Device row) {
        return update(c ->
            c.set(deviceId).equalToWhenPresent(row::getDeviceId)
            .set(userId).equalToWhenPresent(row::getUserId)
            .set(phone).equalToWhenPresent(row::getPhone)
                    .set(phone2).equalToWhenPresent(row::getPhone2)
                    .set(phone3).equalToWhenPresent(row::getPhone3)

                    .set(phone4).equalToWhenPresent( row::getPhone4)
                    .set(phone5).equalToWhenPresent(row::getPhone5)
                    .set(phone6).equalToWhenPresent(row::getPhone6)
                    .set(phone7).equalToWhenPresent( row::getPhone7)
                    .set(phone8).equalToWhenPresent(row::getPhone8)
                    .set(phone9).equalToWhenPresent( row::getPhone9)
                    .set(phone10).equalToWhenPresent( row::getPhone10)
            .set(remark).equalToWhenPresent(row::getRemark)
            .set(smsNotice).equalToWhenPresent(row::getSmsNotice)
            .set(phoneNotice).equalToWhenPresent(row::getPhoneNotice)
            .set(interval).equalToWhenPresent(row::getInterval)
            .set(status).equalToWhenPresent(row::getStatus)
            .where(id, isEqualTo(row::getId))
        );
    }
}