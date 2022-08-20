package in.jamuna.hms.dto.reports;

import lombok.Data;

import java.util.List;

@Data
public class CommonIdAndNameWithDoubleListDTO {
    private int id;
    private String name;
    private List<Double> values;
}
