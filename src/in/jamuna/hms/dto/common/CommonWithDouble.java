package in.jamuna.hms.dto.common;

import lombok.Data;

import java.util.Date;

@Data
public class CommonWithDouble {
    private int id;
    private String name;
    private double no;
    private Date expiry;
}
