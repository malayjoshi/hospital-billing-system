package in.jamuna.hms.dto.reports;

import lombok.Data;

import java.util.List;

@Data
public class SpentStockSummaryDTO {
    private int id;
    private String name;
    private double qty;

    private List<CommonIdAndNameWithDoubleListDTO> list;

}

