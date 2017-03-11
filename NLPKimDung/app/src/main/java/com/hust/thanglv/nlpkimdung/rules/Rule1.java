package com.hust.thanglv.nlpkimdung.rules;

/**
 * Created by giang on 11/30/2016.
 */

public class Rule1 extends Rule{
	/*
	 * Viết làm CheckInvalidate (String x) trả về true nếu xâu x không phải là xâu tiếng Việt căn cứ theo luật sau:
		- Một xâu không phải là tiếng Việt nếu nó có chứa các ký t\+ không nằm trong tiêng Việt: bao gồm 0 - 9, các ký tự đặc biệt trong bảng mã ASCII, các ký tự chỉ xuất hiện trong tiếng Anh.
		- Giả sử rằng x chỉ là một từ đơn nhất (tức không có khoảng trắng) bên trong
	 */

    @Override
    public boolean checkInvalidate(String x) {
        String notInVietNamese = "!@#$%^&*(){}?/'\'|=+[]<>wfjz0123456789";
        if (x.equals("")) return true;
        x = x.toLowerCase();
        for (int i = 0; i < x.length(); i++) {
            if (notInVietNamese.contains("" + x.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
