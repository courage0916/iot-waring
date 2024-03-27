package com.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.mapper.DeviceMapper;
import com.web.mapper.RemindMapper;
import com.web.mapper.UserMapper;
import com.web.model.dto.UserDTO;
import com.web.model.dto.UserSettingDTO;
import com.web.model.po.Remind;
import com.web.model.po.User;
import com.web.model.query.DeviceQuery;
import com.web.model.query.UserQuery;
import com.web.model.vo.UserVO;
import com.web.service.DeviceService;
import com.web.service.UserService;
import com.web.utils.IdWorker;
import com.web.utils.UserUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;

import static com.web.mapper.UserDynamicSqlSupport.id;
import static com.web.mapper.UserDynamicSqlSupport.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;
import org.springframework.beans.BeanUtils;


@Service
public class UserServiceImpl implements UserService {


    @Resource
    UserMapper userMapper;

    @Resource
    RemindMapper remindMapper;

    @Resource
    DeviceService deviceService;

    @Resource
    DeviceMapper deviceMapper;

    @Override
    public UserVO queryByName(String name_) {
        return userMapper.selectOne(data -> data.
                where(username, isEqualTo(name_))
        ).stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setPhoneNotice(user.getPhoneNotice()==1);
            userVO.setSmsNotice(user.getSmsNotice()==1);
            return userVO;
        }).findFirst().orElse(null);
    }

    @Override
    public void create(UserDTO data) {
        check(data);
        User user = data.build();
        user.setId(IdWorker.getId());
        user.setType("操作员");
        user.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
        Assert.isTrue(userMapper.insertSelective(user) == 1, "新增失败");
    }

    @Override
    public void update(UserDTO data) {
        if(!StringUtils.isEmpty(data.getPassword())){
            check(data);
        }else{
            UserVO userVO = queryByName(data.getUsername());
            Assert.isTrue(ObjectUtils.isEmpty(userVO) || (Objects.equals(userVO.getId(), data.getId())), "用户名重复");
        }

        User user = data.build();
        if(!StringUtils.isEmpty(data.getPassword())){
            user.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
        }
        Assert.isTrue(userMapper.updateByPrimaryKeySelective(user) == 1, "修改失败");
    }

    private void check(UserDTO data){
        String password = data.getPassword();
        Assert.isTrue(!StringUtils.isEmpty(password), "密码不能为空");
        Assert.isTrue(!password.contains(" "), "密码不能包含空格");
        Assert.isTrue(password.length()>6, "密码需要大于6位数字");
        String username = data.getUsername();
        Assert.isTrue(!username.contains(" "), "密码不能包含空格");
        UserVO userVO = queryByName(username);
        Assert.isTrue(ObjectUtils.isEmpty(userVO) || (Objects.equals(userVO.getId(), data.getId())), "用户名重复");
    }

    @Override
    @Transactional
    public void delete( Long id ) {
        get( id );
        Assert.isTrue(!Objects.equals(UserUtil.getCurrUser().getId(), id), "管理员账号不能删");
        Assert.isTrue(UserUtil.getCurrUser().getId()!=null, "账号异常");
        Assert.isTrue(userMapper.deleteByPrimaryKey(id) == 1, "删除失败");

        DeviceQuery query = new DeviceQuery();
        query.setUserId(id);
        deviceService.list(query).forEach(item->{

            deviceMapper.deleteByPrimaryKey(item.getId());
        });
    }

    @Override
    public UserVO get( Long id ) {
        UserQuery user = new UserQuery();
        user.setId(id);
        List<UserVO> list = list(user);
        Assert.isTrue( list.size() == 1 , "数据有误："+id);
        return list.get(0);
    }

    @Override
    public List<UserVO> list(UserQuery query) {
        return userMapper.select(data -> data.
        where(id, isEqualToWhenPresent(query.getId()))
                .and(username,isLikeWhenPresent(StringUtils.isEmpty(query.getUsername())?null:"%"+query.getUsername()+"%"))
                        .configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))
            ).stream().map(user -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(user, userVO);
                    return userVO;
                }).collect(Collectors.toList());
    }

    @Override
    public int total(UserQuery query) {
        return (int) userMapper.count(data ->
                data.where(id, isEqualToWhenPresent(query.getId()))
                        .and(username,isLikeWhenPresent(StringUtils.isEmpty(query.getUsername())?null:"%"+query.getUsername()+"%"))
                        .configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))
);
    }

    @Override
    public PageInfo<UserVO> page(UserQuery query, int curr, int size, @PathVariable int navigatePages) {
        PageHelper.startPage(curr, size);
        List<UserVO> list = list(query);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list, navigatePages);
        pageInfo.setTotal(total(query));
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setting(UserSettingDTO setting) {
        User user = new User();
        user.setId(UserUtil.getCurrUser().getId());
        user.setPhone(setting.getPhone());
        user.setPhoneNotice((byte) (setting.getPhoneNotice()?1:0));
        user.setSmsNotice((byte) (setting.getSmsNotice()?1:0));
        userMapper.updateByPrimaryKeySelective(user);

        remindMapper.select(data -> data.configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))).forEach(item->{
            remindMapper.deleteByPrimaryKey(item.getId());
        });
        AtomicInteger atomicInteger = new AtomicInteger(1);
        setting.getTimes().stream().filter(s->!s.isEmpty()).distinct().forEach(item->{
            Remind remind = new Remind();
            remind.setId(atomicInteger.get());
            remind.setTime(item);
            remindMapper.insert(remind);
            atomicInteger.incrementAndGet();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSettingDTO setting() {
        UserSettingDTO userSettingDTO = new UserSettingDTO();
        BeanUtils.copyProperties(UserUtil.getCurrUser(),userSettingDTO);
        userSettingDTO.setTimes( remindMapper.select(data -> data.configureStatement(c -> c.setNonRenderingWhereClauseAllowed(true))).stream().map(Remind::getTime).collect(Collectors.toList()));
        return userSettingDTO;
    }


}
