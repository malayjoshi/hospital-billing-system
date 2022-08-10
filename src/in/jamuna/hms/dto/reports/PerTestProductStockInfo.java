package in.jamuna.hms.dto.reports;

import lombok.Data;

import java.util.List;

@Data
public class PerTestProductStockInfo {
    private int id;
    private String product;
    private String company;
    private double openingStock;
    private double closingStock;


    private double expired;

    private double allocatedOpening;
    private double allocatedClosing;

    private double spent;

    private List<TestProductMapping> mapping;

}
