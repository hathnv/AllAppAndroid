package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by giang on 11/30/2016.
 */

public class Rule2 extends Rule{
	/*
	 * Từ tiếng việt sai chính tả nếu không có nguyên âm nào trong từ đó
	 */

    @Override
    public boolean checkInvalidate(String x) {
        String consolnant = "qrtpsdghklxcvbnm đ";
        int count = 0;
        x = x.toLowerCase();
        for (int i = 0; i < x.length(); i++) {
            if (consolnant.contains("" + x.charAt(i))) {
                count++;
            }
        }
        if (count == x.length()) {
            return true;
        }
        return false;
    }
}
