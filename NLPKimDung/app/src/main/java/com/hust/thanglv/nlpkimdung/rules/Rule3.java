package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by giang on 11/30/2016.
 */

public class Rule3 extends Rule{
	/*
	 * Từ tiếng Việt bị giới hạn về số lượng chữ cái bên trong.
	 * Hãy cài đặt Rule3 sao cho kiểm tra được giới hạn số lượng chữ cái trong một từ
	 */

    @Override
    public boolean checkInvalidate(String x) {
        if (x.length() > 7) {
            return true;
        }
        return false;
    }
}
