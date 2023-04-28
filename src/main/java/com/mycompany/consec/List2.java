package com.mycompany.consec;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class List2 {

    List<List<String>> m_list2;

    // Конструктор класса

    public List2(String name) throws IOException {
        m_list2 = list2(name);
    }

    List<List<String>> list2(String name) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    public List<List<String>> get_list2() {
        return m_list2;
    }
}
