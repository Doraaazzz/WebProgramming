package com.dora.weblab4.data;

import com.dora.weblab4.model.CheckModel;
import com.dora.weblab4.model.WebUserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRepository extends CrudRepository<CheckModel, Long> {
    List<CheckModel> findAllByUser(WebUserModel user);
}
