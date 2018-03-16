package by.bsuir.spp.jewelryrentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    public interface Create {}
    public interface Update {}

    @Min(groups = {Update.class}, value = 1, message = "Min value is 1")
    private long id;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String status;

    @Pattern(groups = {Update.class, Create.class}, regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "Date format 2018-03-15 14:25")
    private String rentDate;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private int daysRent;

    @Min(groups = {Update.class, Create.class}, value = 0, message = "Min value is 0")
    private double cost;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private long clientId;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private long employeeId;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private long jewelryId;
}
