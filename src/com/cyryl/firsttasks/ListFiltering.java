package com.cyryl.firsttasks;

import java.util.ArrayList;
import java.util.List;

public class ListFiltering {

    public static void main(String[] args) {
        List<Object> test = new ArrayList<>();
        List<Object> result;
        test.add(100);
        test.add("Xyz");
        test.add("123");
        test.add(20);

        for(Object object: test)
            System.out.println(object);

        System.out.println("");

        result = filterList(test);
        for(Object object: result)
            System.out.println(object);
    }

    public static List<Object> filterList(final List<Object> list) {
        // Return the List with the Strings filtered out
        List<Object> filteredList = new ArrayList<>();

        for(Object object: list){

            if (object instanceof Integer){
                filteredList.add(object);
            }
        }
        return filteredList;
    }
}
