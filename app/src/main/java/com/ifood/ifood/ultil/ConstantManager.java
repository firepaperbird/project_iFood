package com.ifood.ifood.ultil;

public class ConstantManager {
    public static String getUnitById(int unitId) {
        String unit;
        switch (unitId) {
            case 1:
                unit = "grams";
                break;
            case 2:
                unit = "mil";
                break;
            case 3:
                unit = "tsp";
                break;
            case 4:
                unit = "kilograms";
                break;
            case 5:
                unit = "piece";
                break;
            case 6:
                unit = "box";
                break;
            default:
                unit = "undefined";
                break;
        }
        return  unit;
    }
}
