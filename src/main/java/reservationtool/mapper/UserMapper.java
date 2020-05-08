package reservationtool.mapper;

import org.apache.ibatis.annotations.Mapper;
import reservationtool.domain.model.User;

@Mapper
public interface UserMapper {
    User findUserByUserId(String userId);
}
