package vn.tuanjava.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vn.tuanjava.util.Gender;
import vn.tuanjava.util.UserStatus;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class UserDetailResponse implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Date dateOfBirth;

    private Gender gender;

    private String username;

    private String type;

    private UserStatus status;
}
