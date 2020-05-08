package reservationtool.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reservationtool.domain.model.User;
import reservationtool.mapper.UserMapper;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findUserByUserId(userId);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(userId + "is not found");
        }
        return new ReservationUserDetails(user);
    }
}
