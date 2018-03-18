package by.bsuir.spp.jewelryrentsystem.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtTokenPayload {
    private Long userId;
    private long exp;

    public JwtTokenPayload(Long userId, long exp) {
        this.userId = userId;
        this.exp = exp;
    }
}
