package domain.Impl;

import domain.Controllers.Utils;
import domain.Enums.FieldType;
import domain.Interfaces.Asset;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Map;

public class Field implements Asset {
    private static int idCounter = 0;

    private int id;
    private int seats;
    private String location;
    private String name;
    private FieldType fieldType;

    public Field(int seats, String location, String name, FieldType fieldType) {
        this.id = idCounter++;
        this.seats = seats;
        this.location = location;
        this.name = name;
        this.fieldType = fieldType;
    }

    @Override
    public boolean editAsset(HashMap<String, String> changes) throws Exception {
        for (Map.Entry<String, String> entry : changes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toLowerCase()) {
                case "seats":
                    if(seats >= 0) {
                        this.seats = Integer.valueOf(value);
                    }
                    else{
                        throw new Exception("Seats must be non negative number");
                    }
                case "location":
                    this.location = value;
                    break;
                case "name":
                    this.name = value;
                    break;
                case "fieldType":
                    this.fieldType = FieldType.valueOf(value);
                    break;
            }
        }
        return true;
    }

    public int getSeats() {
        return seats;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
