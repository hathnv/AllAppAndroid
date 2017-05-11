package com.hust.thanglv.nlpkimdung.rules;

import java.util.ArrayList;
import java.util.List;

public class Rule6 extends Rule {
	/*
	 * check g
	 */

    @Override
    public boolean checkInvalidate(String x) {
        boolean b = false;
        x = x.toLowerCase();
        List<Integer> list = new ArrayList<>(); // list chi so vi tri cua g
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == 'g') {
                list.add(i);
            }
        }
        if (list.size() == 0) {
            b = false;
        } else {
            if (x.charAt(x.length() - 1) == 'g') {
                // nếu g ở cuối từ thì trước g phải là n
                if (x.length() >= 2 && x.charAt(x.length() - 2) == 'n') {
                    b = false;
                } else {
                    b = true;
                }
            }
            if (x.length() >= 3 && list.get(0) != 0 && list.get(list.size() - 1) != (x.length() - 1)) {
                // nếu g ở giữa câu
                //System.out.println("g o giua");
                for (int j = 1; j < x.length() - 1; j++) {
                    if (x.charAt(j) == 'g') {
                        // trước g phải là n
                        if (x.charAt(j - 1) == 'n') {
                            b = false;
                        } else if (x.charAt(j + 1) == 'h') {
                            // sau g phải là h
                            b = false;
                        } else {
                            b = true;
                        }
                    }
                }
            }
        }

        return b;
    }
}
