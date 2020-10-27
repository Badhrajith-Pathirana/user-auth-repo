package lk.rythmo.userauth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRegistrationDataDTO {
    private String username;
    private String password;
    private String userId;
}
