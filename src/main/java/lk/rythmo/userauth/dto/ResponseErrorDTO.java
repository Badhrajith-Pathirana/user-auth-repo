package lk.rythmo.userauth.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDTO {
    private String message;
    private String errorCode;
}
