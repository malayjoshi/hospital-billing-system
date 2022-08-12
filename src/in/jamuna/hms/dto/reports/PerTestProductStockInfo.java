package in.jamuna.hms.dto.reports;

import lombok.Data;

import java.util.List;

@Data
public class PerTestProductStockInfo {
    private int id;
    private String product;
    private String company;

    private double orgStock;
    private double totalStock;
    private double expired;
    private double effectiveStock;

    private double orgAllocated;
    private double allocatedLeft;
    private double spent;
}
