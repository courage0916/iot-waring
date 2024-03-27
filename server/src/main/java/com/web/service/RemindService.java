package com.web.service;

import com.github.pagehelper.PageInfo;
import com.web.model.dto.RemindDTO;
import com.web.model.query.RemindQuery;
import com.web.model.vo.RemindVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RemindService {

    void create(RemindDTO data);

    void update(RemindDTO data);

    void delete( Integer id );

    RemindVO get( Integer id );

    List<RemindVO> list(RemindQuery query);

    int total(RemindQuery query);

    PageInfo<RemindVO> page(RemindQuery query, int curr, int size, @PathVariable int navigatePages);

}
