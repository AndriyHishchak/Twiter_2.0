package com.Spring.SpringLetsCode.Repo;

import com.Spring.SpringLetsCode.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUser extends CrudRepository<User,Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
