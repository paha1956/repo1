package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

interface RoadChecker {
    int getRestrictedRoads(String fileName, LocalDate localDate) throws IOException;
}

class MyRoadChecker implements RoadChecker {

    @Override
    public int getRestrictedRoads(String fileName, LocalDate localDate) {
        String[] dataList = new String[0];
        try {
            dataList = Files.readAllLines(Paths.get(fileName)).toArray(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }

        int counter = 0;
        for (String item : dataList) {

            String[] dataStr = item.split(",");

            boolean formatError = false;
            LocalDate dateFrom = null;
            LocalDate dateTo = null;

            if (dataStr[10].length() != 8 || (dataStr[12].length() != 8 && dataStr[12].length() != 1)) continue;

            try {
                dateFrom = LocalDate.parse(dataStr[10], DateTimeFormatter.ofPattern("yyyyMMdd"));
            } catch (Exception e) {
                e.printStackTrace();
                formatError = true;
            }

            if (dataStr[12].equals("-") == false) {
                try {
                    dateTo = LocalDate.parse(dataStr[12], DateTimeFormatter.ofPattern("yyyyMMdd"));
                } catch (Exception e) {
                    e.printStackTrace();
                    formatError = true;
                }
            } else {
                dateTo = localDate;
            }

            if (formatError == false && localDate.compareTo(dateFrom) >= 0 && localDate.compareTo(dateTo) <= 0) {
                counter++;
            }

        }
        return counter;
    }
}

public class Main {

    public static void main(String[] args) {
        // write your code here

        LocalDate localDate = LocalDate.of(2015, Month.MARCH, 15);
        System.out.println(localDate);
        RoadChecker roadChecker = new MyRoadChecker();
        int count = 0;
        try {
            count = roadChecker.getRestrictedRoads("C:\\Users\\sp\\Documents\\Занятие 11\\restrictions\\data-20191105T072439-structure-20160117T173719.csv", localDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(count);
    }
}


