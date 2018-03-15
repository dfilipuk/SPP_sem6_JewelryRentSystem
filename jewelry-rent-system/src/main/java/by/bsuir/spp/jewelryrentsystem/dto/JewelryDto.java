package by.bsuir.spp.jewelryrentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JewelryDto {
    public interface Create {}
    public interface Update {}

    @Min(groups = {Update.class}, value = 1, message = "Min value is 1")
    private long id;

    @NotNull(groups = {Create.class, MaterialDto.Update.class}, message = "Required field")
    @Size(groups = {Create.class, MaterialDto.Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String name;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String producer;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String description;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String pictureUrl;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String type;

    @Min(groups = {Update.class, Create.class}, value = 0, message = "Min value is 0")
    private double weight;

    @NotNull(groups = {Create.class, Update.class}, message = "Required field")
    @Size(groups = {Create.class, Update.class}, min = 3, max = 255, message = "Length between 3 and 255")
    private String status;

    @Min(groups = {Update.class, Create.class}, value = 0, message = "Min value is 0")
    private double costPerDay;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private int daysRental;

    @Min(groups = {Update.class, Create.class}, value = 1, message = "Min value is 1")
    private long branchId;

    private List<Long> materialsIds;
}
