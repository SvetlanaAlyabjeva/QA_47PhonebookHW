package dto;

import lombok.*;

@ToString
@Builder
@Getter
@Setter

public class UserLombok {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
