package by.bsuir.spp.jewelryrentsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class DeleteActionRequestDto {
    @Min(value = 1, message = "Min value is 1")
    private long id;
}
