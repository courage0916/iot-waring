package com.web.service;

import com.github.pagehelper.PageInfo;
import com.web.model.dto.UserDTO;
import com.web.model.dto.UserSettingDTO;
import com.web.model.query.UserQuery;
import com.web.model.vo.UserVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    UserVO queryByName(String name);
    void create(UserDTO data);

    void update(UserDTO data);

    void delete( Long id );

    UserVO get( Long id );

    List<UserVO> list(UserQuery query);

    int total(UserQuery query);

    PageInfo<UserVO> page(UserQuery query, int curr, int size, @PathVariable int navigatePages);

    void setting(UserSettingDTO setting);
    UserSettingDTO setting();
}
