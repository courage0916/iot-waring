package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.mapper.RemindMapper;
import com.web.model.dto.RemindDTO;
import com.web.model.po.Remind;
import com.web.model.query.RemindQuery;
import com.web.model.vo.RemindVO;
import com.web.service.RemindService;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import static com.web.mapper.RemindDynamicSqlSupport.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;
import org.springframework.beans.BeanUtils;


@Service
public class RemindServiceImpl implements RemindService {

    private final RemindMapper remindMapper;


    public RemindServiceImpl(RemindMapper remindMapper ) {
        this.remindMapper = remindMapper;
    }

    @Override
    public void create(RemindDTO data) {
        Remind remind = data.build();
        Assert.isTrue(remindMapper.insertSelective(remind) == 1, "新增失败");
    }

    @Override
    public void update(RemindDTO data) {
        Remind remind = data.build();
        Assert.isTrue(remindMapper.updateByPrimaryKeySelective(remind) == 1, "修改失败");
    }

    @Override
    public void delete( Integer id ) {
        get( id );
        Remind remind = new Remind();
        remind.setId(id);
        Assert.isTrue(remindMapper.updateByPrimaryKeySelective(remind) == 1, "删除失败");
    }

    @Override
    public RemindVO get( Integer id ) {
        RemindQuery remind = new RemindQuery();
        remind.setId(id);
        List<RemindVO> list = list(remind);
        Assert.isTrue( list.size() == 1 , "数据有误："+id);
        return list.get(0);
    }

    @Override
    public List<RemindVO> list(RemindQuery query) {
        return remindMapper.select(data -> data.
        where(id, isEqualTo(query.getId()))
).stream().map(remind -> {
                    RemindVO remindVO = new RemindVO();
                    BeanUtils.copyProperties(remind, remindVO);
                    return remindVO;
                }).collect(Collectors.toList());
    }

    @Override
    public int total(RemindQuery query) {
        return (int) remindMapper.count(data ->
                data. where(id, isEqualTo(query.getId()))
);
    }

    @Override
    public PageInfo<RemindVO> page(RemindQuery query, int curr, int size, @PathVariable int navigatePages) {
        PageHelper.startPage(curr, size);
        List<RemindVO> list = list(query);
        PageInfo<RemindVO> pageInfo = new PageInfo<>(list, navigatePages);
        pageInfo.setTotal(total(query));
        return pageInfo;
    }



}
