package com.web.mapper;

import static com.web.mapper.DeviceDataDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.web.model.po.DeviceData;
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
public interface DeviceDataMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<DeviceData>, CommonUpdateMapper {
    
    BasicColumn[] selectList = BasicColumn.columnList(id, deviceId, createTime,content);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="DeviceDataResult", value = {
        @Result(column="t_id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="t_device_id", property="deviceId", jdbcType=JdbcType.VARCHAR),
        @Result(column="t_create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="t_content", property="content", jdbcType=JdbcType.VARCHAR),
    })
    List<DeviceData> selectMany(SelectStatementProvider selectStatement);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("DeviceDataResult")
    Optional<DeviceData> selectOne(SelectStatementProvider selectStatement);

    
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, deviceData, completer);
    }

    
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, deviceData, completer);
    }

    
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int insert(DeviceData row) {
        return MyBatis3Utils.insert(this::insert, row, deviceData, c ->
            c.map(id).toProperty("id")
            .map(deviceId).toProperty("deviceId")
            .map(createTime).toProperty("createTime")
                    .map(content).toProperty("content")
        );
    }

    
    default int insertMultiple(Collection<DeviceData> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, deviceData, c ->
            c.map(id).toProperty("id")
            .map(deviceId).toProperty("deviceId")
            .map(createTime).toProperty("createTime") .map(content).toProperty("content")
        );
    }

    
    default int insertSelective(DeviceData row) {
        return MyBatis3Utils.insert(this::insert, row, deviceData, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(deviceId).toPropertyWhenPresent("deviceId", row::getDeviceId)
            .map(createTime).toPropertyWhenPresent("createTime", row::getCreateTime)
                    .map(content).toPropertyWhenPresent("content", row::getContent)
        );
    }

    
    default Optional<DeviceData> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, deviceData, completer);
    }

    
    default List<DeviceData> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, deviceData, completer);
    }

    
    default List<DeviceData> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, deviceData, completer);
    }

    
    default Optional<DeviceData> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, deviceData, completer);
    }

    
    static UpdateDSL<UpdateModel> updateAllColumns(DeviceData row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(deviceId).equalTo(row::getDeviceId)
                .set(createTime).equalTo(row::getCreateTime).set(content).equalTo(row::getContent);

    }

    
    static UpdateDSL<UpdateModel> updateSelectiveColumns(DeviceData row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(deviceId).equalToWhenPresent(row::getDeviceId)
                .set(createTime).equalToWhenPresent(row::getCreateTime).set(content).equalTo(row::getContent);
    }

    
    default int updateByPrimaryKey(DeviceData row) {
        return update(c ->
            c.set(deviceId).equalTo(row::getDeviceId)
            .set(createTime).equalTo(row::getCreateTime).set(content).equalTo(row::getContent)
            .where(id, isEqualTo(row::getId))
        );
    }

    
    default int updateByPrimaryKeySelective(DeviceData row) {
        return update(c ->
            c.set(deviceId).equalToWhenPresent(row::getDeviceId)
            .set(createTime).equalToWhenPresent(row::getCreateTime).set(content).equalTo(row::getContent)
            .where(id, isEqualTo(row::getId))
        );
    }
}