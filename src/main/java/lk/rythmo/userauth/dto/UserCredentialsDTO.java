package lk.rythmo.userauth.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCredentialsDTO {
    private String authToken;
    private String refreshToken;
    private String username;
    private ZonedDateTime accessDate;
    private ZonedDateTime authExpire;
    private ZonedDateTime refreshExpire;
}
