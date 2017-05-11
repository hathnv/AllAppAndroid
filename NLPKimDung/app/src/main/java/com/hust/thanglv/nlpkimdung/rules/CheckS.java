package com.hust.thanglv.nlpkimdung.rules;

public class CheckS extends Rule {
    // Chu s khong dung truoc oa, oă,,oe,,uê,uâ ngoai tru soát, soạt, soạng,soạn,suất
    public final String nguyenamSauO = "aáạãảàăắẳẵặằeéẻẽèẹ";
    public final String nguyenamSauU = "êếềễệểâầấẫẩậ";


    @Override
    public boolean checkInvalidate(String x) {
        x = x.toLowerCase();
        if (x.equals("soát") || x.equals("soạt") || x.equals("soạng") || x.equals("suất") || x.equals("soạn")
                || x.equals("song") || x.equals("soái") || x.equals("soãi") ) {
            return false;
        }else if(x.length() > 2 && x.charAt(0) == 's'){
            if(x.charAt(1) == 'o' && nguyenamSauO.contains(x.charAt(2) + "")){
                return true;
            }else if (x.charAt(1) == 'u' && nguyenamSauU.contains(x.charAt(2) + "")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
