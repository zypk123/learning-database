package com.zhang.repository;

import com.zhang.entity.User;
import com.zhang.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyu
 * @create 2018-11-12 14:36
 **/
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * 通过姓名查找用户信息
     *
     * @param name
     * @return
     */
    User findByName(String name);

}
