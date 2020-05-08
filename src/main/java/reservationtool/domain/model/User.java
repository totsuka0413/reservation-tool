package reservationtool.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private RoleName roleName;
}
