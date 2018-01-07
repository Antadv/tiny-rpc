package com.somelogs.demo;

import com.somelogs.annotation.RequestRoute;
import com.somelogs.model.Response;

/**
 * 描述
 *
 * @author LBG - 2018/1/7 13:43
 */
@RequestRoute(url = "/user")
public interface UserService {

    @RequestRoute(url = "/addUser")
    Response<Long> addUser(String username, String mobile);
}
