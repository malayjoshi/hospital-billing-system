package in.jamuna.hms.dto.reports;

import lombok.Data;

import java.util.Date;

@Data
public class MiniTestStockDTO {
    private int id;
    private String batch;
    private Date expiry;
    private double free;
    private double qty;
    private double qtyLeft;
}
