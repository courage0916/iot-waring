package com.web.mapper;

import static com.web.mapper.RemindDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.web.model.po.Remind;
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
public interface RemindMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<Remind>, CommonUpdateMapper {
    
    BasicColumn[] selectList = BasicColumn.columnList(id, time);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="RemindResult", value = {
        @Result(column="t_id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="t_time", property="time", jdbcType=JdbcType.TIME)
    })
    List<Remind> selectMany(SelectStatementProvider selectStatement);

    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("RemindResult")
    Optional<Remind> selectOne(SelectStatementProvider selectStatement);

    
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, remind, completer);
    }

    
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, remind, completer);
    }

    
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int insert(Remind row) {
        return MyBatis3Utils.insert(this::insert, row, remind, c ->
            c.map(id).toProperty("id")
            .map(time).toProperty("time")
        );
    }

    
    default int insertMultiple(Collection<Remind> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, remind, c ->
            c.map(id).toProperty("id")
            .map(time).toProperty("time")
        );
    }

    
    default int insertSelective(Remind row) {
        return MyBatis3Utils.insert(this::insert, row, remind, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(time).toPropertyWhenPresent("time", row::getTime)
        );
    }

    
    default Optional<Remind> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, remind, completer);
    }

    
    default List<Remind> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, remind, completer);
    }

    
    default List<Remind> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, remind, completer);
    }

    
    default Optional<Remind> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, remind, completer);
    }

    
    static UpdateDSL<UpdateModel> updateAllColumns(Remind row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(time).equalTo(row::getTime);
    }

    
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Remind row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(time).equalToWhenPresent(row::getTime);
    }

    
    default int updateByPrimaryKey(Remind row) {
        return update(c ->
            c.set(time).equalTo(row::getTime)
            .where(id, isEqualTo(row::getId))
        );
    }

    
    default int updateByPrimaryKeySelective(Remind row) {
        return update(c ->
            c.set(time).equalToWhenPresent(row::getTime)
            .where(id, isEqualTo(row::getId))
        );
    }
}