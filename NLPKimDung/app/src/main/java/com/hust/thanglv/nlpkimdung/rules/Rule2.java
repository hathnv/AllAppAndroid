package com.hust.thanglv.nlpkimdung.rules;

public class Rule2 extends Rule{
	/*
	 * Từ tiếng việt sai chính tả nếu không có nguyên âm nào trong từ đó
	 */

    @Override
    public boolean checkInvalidate(String x) {
        String consolnant = "qrtpsdghklxcvbnmđ";
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
