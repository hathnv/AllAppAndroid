package com.hust.thanglv.nlpkimdung.model;

/**
 * Created by user on 3/14/17.
 */

public class NguyenAmCoDau {

    private static NguyenAmCoDau instance = null;
    public static NguyenAmCoDau getInstance() {
        if (instance == null){
            instance = new NguyenAmCoDau();
        }
        return instance;
    }
    public final String codau = A + AW + AA + E + EE + O + OO + OW + Y +UW + U + I;
    private static final String A = "áàạãả";
    private static final String AW = "ắằẵặẳ";
    private static final String AA = "ấầậẫẩ";
    private static final String E = "éèẹẽẻ";
    private static final String EE = "ếềệễể";
    private static final String O = "óòọõỏ";
    private static final String OO = "ốồộỗổ";
    private static final String OW = "ớờợỡở";
    private static final String Y = "ýỳỵỹỷ";
    private static final String UW = "ứừựữử";
    private static final String U = "úùụũủ";
    private static final String I = "íìịĩỉ";
}
