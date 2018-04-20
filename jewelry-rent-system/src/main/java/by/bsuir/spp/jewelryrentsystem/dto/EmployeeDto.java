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
public class EmployeeDto {
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

    @Min(groups = {Update.class, Create.class}, value = 0, message = "Min value is 0")
    private double salary;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String position;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String login;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    private String password;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String role;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private long branchId;
}
