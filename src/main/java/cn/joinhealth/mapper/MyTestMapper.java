package cn.joinhealth.mapper;

import cn.joinhealth.model.AccountApplicationUserModel;

import java.util.List;
import java.util.Map;

/**
 * Created by shilei on 2018/4/19.
 */
public interface MyTestMapper {
    List<AccountApplicationUserModel> selectList(Map<String, Object> map);
}
