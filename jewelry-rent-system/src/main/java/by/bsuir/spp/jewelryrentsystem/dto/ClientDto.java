package by.bsuir.spp.jewelryrentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    public interface Create {}
    public interface Update {}

    @Min(groups = {Update.class}, value = 1, message = "Min value is 1")
    private long id;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String name;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String surname;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String secondName;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 7, max = 255, message = "Length between 7 and 255")
    private String passportNumber;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String address;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 7, max = 255, message = "Length between 7 and 255")
    private String telephone;
}
