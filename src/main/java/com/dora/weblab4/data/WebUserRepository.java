package com.dora.weblab4.data;

import com.dora.weblab4.model.WebUser;
import com.dora.weblab4.model.WebUserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends CrudRepository<WebUserModel, Long> {
    WebUserModel findByUsername(String username);
    boolean existsByUsername(String username);

    default WebUserModel findByUserInterface(WebUser user) {
        return findByUsername(user.getUsername());
    }
}
