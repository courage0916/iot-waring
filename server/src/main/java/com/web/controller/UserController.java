package com.web.controller;

import com.github.pagehelper.PageInfo;
import com.web.model.dto.UserDTO;
import com.web.model.dto.UserSettingDTO;
import com.web.model.query.UserQuery;
import com.web.model.vo.UserVO;
import com.web.service.UserService;


import com.web.utils.Result;
import com.web.utils.UserUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping
    public void create(@Valid @RequestBody UserDTO data) {
        userService.create(data);
    }


    
    @PutMapping
    public void update(@Valid @RequestBody UserDTO data) {
        userService.update(data);
    }


    
    @DeleteMapping("{id}")
    public void delete( @PathVariable Long id ) {
        userService.delete(id );
    }


    
    @GetMapping("{id}")
    public UserVO get( @PathVariable Long id ) {
        return userService.get(id );
    }

    @GetMapping("/curr/user")
    public UserVO curr( ) {
        return UserUtil.getCurrUser();
    }
   
    @GetMapping
    public List<UserVO> list(@RequestBody UserQuery query) {
        return userService.list(query);
    }

    
    @GetMapping("/total")
    public int total(@RequestBody  UserQuery query) {
        return userService.total(query);
    }

    
    @PostMapping("{curr}/{size}/{navigatePages}")
    public PageInfo<UserVO> page(@RequestBody UserQuery query, @PathVariable int curr, @PathVariable int size, @PathVariable int navigatePages) {
        return userService.page(query, curr, size, navigatePages);
    }

    @PostMapping("/setting")
    public void setting(@RequestBody UserSettingDTO setting) {
        userService.setting(setting);
    }

    @GetMapping("/setting")
    public UserSettingDTO setting() {


        return userService.setting();
    }
}
