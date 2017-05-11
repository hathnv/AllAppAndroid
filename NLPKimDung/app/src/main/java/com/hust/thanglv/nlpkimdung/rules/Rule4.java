package com.hust.thanglv.nlpkimdung.rules;


public class Rule4 extends Rule{
	/*
	 * Từ tiếng Việt có tối đa 3 nguyên âm. Hãy viết Rule 4 cài đặt luật này
	 */

    @Override
    public boolean checkInvalidate(String x) {
        String consolnant = "qrtpsdghklxcvbnmđ";
        int count = 0; // dem so phu am
        x = x.toLowerCase();
        for (int i = 0; i < x.length(); i++) {
            if (consolnant.contains("" + x.charAt(i))) {
                count++;
            }
        }
        if ((x.length() - count) > 3) {
            return true;
        }
        return false;
    }
}
