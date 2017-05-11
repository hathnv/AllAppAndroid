package com.hust.thanglv.nlpkimdung.rules;

public class CacPhuAmNamCuoi extends Rule{
    @Override
    public boolean checkInvalidate(String x) {
        boolean check = false;
        String amKhongDungCuoi = "qrsdđklxvb";
        if(x.length() >=1 && amKhongDungCuoi.contains(x.charAt(x.length() - 1)+ "")){
            check = true;
        }
        return check;
    }
}
