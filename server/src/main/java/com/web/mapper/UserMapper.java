package com.web.mapper;

import static com.web.mapper.UserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.web.model.po.User;
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
public interface UserMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<User>, CommonUpdateMapper {
    
    BasicColumn[] selectList = BasicColumn.columnList(id, username, password, type, phone, lock, smsNotice, phoneNotice);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserResult", value = {
        @Result(column="t_id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="t_username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="t_password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="t_type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="t_phone", property="phone", jdbcType=JdbcType.CHAR),
        @Result(column="t_lock", property="lock", jdbcType=JdbcType.TINYINT),
        @Result(column="t_sms_notice", property="smsNotice", jdbcType=JdbcType.TINYINT),
        @Result(column="t_phone_notice", property="phoneNotice", jdbcType=JdbcType.TINYINT)
    })
    List<User> selectMany(SelectStatementProvider selectStatement);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserResult")
    Optional<User> selectOne(SelectStatementProvider selectStatement);

    
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, user, completer);
    }

    
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, user, completer);
    }

    
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int insert(User row) {
        return MyBatis3Utils.insert(this::insert, row, user, c ->
            c.map(id).toProperty("id")
            .map(username).toProperty("username")
            .map(password).toProperty("password")
            .map(type).toProperty("type")
            .map(phone).toProperty("phone")
            .map(lock).toProperty("lock")
            .map(smsNotice).toProperty("smsNotice")
            .map(phoneNotice).toProperty("phoneNotice")
        );
    }

    
    default int insertMultiple(Collection<User> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, user, c ->
            c.map(id).toProperty("id")
            .map(username).toProperty("username")
            .map(password).toProperty("password")
            .map(type).toProperty("type")
            .map(phone).toProperty("phone")
            .map(lock).toProperty("lock")
            .map(smsNotice).toProperty("smsNotice")
            .map(phoneNotice).toProperty("phoneNotice")
        );
    }

    
    default int insertSelective(User row) {
        return MyBatis3Utils.insert(this::insert, row, user, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(username).toPropertyWhenPresent("username", row::getUsername)
            .map(password).toPropertyWhenPresent("password", row::getPassword)
            .map(type).toPropertyWhenPresent("type", row::getType)
            .map(phone).toPropertyWhenPresent("phone", row::getPhone)
            .map(lock).toPropertyWhenPresent("lock", row::getLock)
            .map(smsNotice).toPropertyWhenPresent("smsNotice", row::getSmsNotice)
            .map(phoneNotice).toPropertyWhenPresent("phoneNotice", row::getPhoneNotice)
        );
    }

    
    default Optional<User> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, user, completer);
    }

    
    default List<User> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, user, completer);
    }

    
    default List<User> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, user, completer);
    }

    
    default Optional<User> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, user, completer);
    }

    
    static UpdateDSL<UpdateModel> updateAllColumns(User row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(username).equalTo(row::getUsername)
                .set(password).equalTo(row::getPassword)
                .set(type).equalTo(row::getType)
                .set(phone).equalTo(row::getPhone)
                .set(lock).equalTo(row::getLock)
                .set(smsNotice).equalTo(row::getSmsNotice)
                .set(phoneNotice).equalTo(row::getPhoneNotice);
    }

    
    static UpdateDSL<UpdateModel> updateSelectiveColumns(User row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(username).equalToWhenPresent(row::getUsername)
                .set(password).equalToWhenPresent(row::getPassword)
                .set(type).equalToWhenPresent(row::getType)
                .set(phone).equalToWhenPresent(row::getPhone)
                .set(lock).equalToWhenPresent(row::getLock)
                .set(smsNotice).equalToWhenPresent(row::getSmsNotice)
                .set(phoneNotice).equalToWhenPresent(row::getPhoneNotice);
    }

    
    default int updateByPrimaryKey(User row) {
        return update(c ->
            c.set(username).equalTo(row::getUsername)
            .set(password).equalTo(row::getPassword)
            .set(type).equalTo(row::getType)
            .set(phone).equalTo(row::getPhone)
            .set(lock).equalTo(row::getLock)
            .set(smsNotice).equalTo(row::getSmsNotice)
            .set(phoneNotice).equalTo(row::getPhoneNotice)
            .where(id, isEqualTo(row::getId))
        );
    }

    
    default int updateByPrimaryKeySelective(User row) {
        return update(c ->
            c.set(username).equalToWhenPresent(row::getUsername)
            .set(password).equalToWhenPresent(row::getPassword)
            .set(type).equalToWhenPresent(row::getType)
            .set(phone).equalToWhenPresent(row::getPhone)
            .set(lock).equalToWhenPresent(row::getLock)
            .set(smsNotice).equalToWhenPresent(row::getSmsNotice)
            .set(phoneNotice).equalToWhenPresent(row::getPhoneNotice)
            .where(id, isEqualTo(row::getId))
        );
    }
}