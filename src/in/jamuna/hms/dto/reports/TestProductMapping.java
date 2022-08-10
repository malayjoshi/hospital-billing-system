package in.jamuna.hms.dto.reports;

import lombok.Data;

@Data
public class TestProductMapping {
    private int id;
    private String name;
    //totak by bill
    private long no1;
    //total by spent
    private long no2;
    //avg ratio
    private long no3;
    //total spent
    private double no4;

    private  boolean itemAndSpentBalanced;
}
