package com.evcharging.system.service;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class ChargingDataExporter {

    public void exportData(String data) {

        try (FileWriter writer = new FileWriter("charging-data.txt")) {

            writer.write(data);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to export charging data",
                    e
            );
        }
    }
}