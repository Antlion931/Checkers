package com.example.checkersgrid.ListFunction;

import com.example.checkersgrid.judge.Cords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFunction {
    public static List<Cords> fromResponse(String response) {
        List<Cords> result = new ArrayList<Cords>();
        String[] arrOfStr = response.split(" ", 0);
        for (String s : arrOfStr) {
            String[] cord = s.split(",", 2);
            result.add(new Cords(Integer.parseInt(cord[0]), Integer.parseInt(cord[1])));
        }
        return result;
    }

    public static String string(List<Cords> list_of_cords) {
        String result = new String();
        for(Cords c : list_of_cords) {
            result += c.x;
            result += ",";
            result += c.y;
            result += " ";
        }
        return result;
    }
}
