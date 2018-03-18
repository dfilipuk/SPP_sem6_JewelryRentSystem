package by.bsuir.spp.jewelryrentsystem.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotNull(message = "Required field")
    @Size(min = 3, max = 255, message = "Length between 3 and 255")
    private String login;

    @NotNull(message = "Required field")
    @Size(min = 6, max = 512, message = "Length between 6 and 512")
    private String password;
}
