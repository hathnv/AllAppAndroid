/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nlp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.mozilla.universalchardet.UniversalDetector;

public class Utils {

    public static Pair<Integer, HashMap<String, Integer>> loadAllBookIntoMemory(String directoryPath) {
        BufferedReader fbReader = null;
        int bookLoaded = 0;
        try {
            File[] files = Utils.getAllFileInFolder(directoryPath);
            HashMap<String, Integer> keywords = new HashMap<>(20000);
            for (File file : files) {
                String charset = Utils.detectCharset(file);
                if (charset == null) {
                    System.out.println("auto detect charset failed for" + file.getName() + ". File has been ignored");
                    continue;
                }
                fbReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
                String line;
                Integer keyWordCount;
                while ((line = fbReader.readLine()) != null) {
                    String parts[] = line.split("( |̃|´||%|Ï|#|Û|ä|ø|û|ï|Ÿ|¡|−|⩠|ꮧ|⮨|俠|ⵠ|ᣍ|>|¼|ϣ|褐|ᣠ| |\\.|,|!|:|-|\\?|\\*|\\(|\\)|\"|\'|;|–|“|”|[0-9]|„|\\\\|…|¿|’|\\/|•|½|¤|‘|Ä|ü|\\]|\\[|\\+|&|	|`)");
                    for (String part : parts) {
                        if (part.matches(".*[^a-zA-Z_ýếÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐÐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ].*| +")) {
                        }
                        if (part.length() > 2 && part.length() < 8) {
                            if (keywords.get(part) == null) {
                                keywords.put(part, 1);
                            } else {
                                keyWordCount = keywords.get(part);
                                if (keyWordCount == null) {
                                    keywords.put(part, 1);
                                } else {
                                    keywords.put(part, keyWordCount + 1);
                                }
                            }
                        }
                    }
                }
                System.out.println("loaded: " + file.getName());
                bookLoaded++;
            }
            return new Pair(bookLoaded, keywords);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static File[] getAllFileInFolder(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        return null;
    }

    public static String detectCharset(File file) {
        BufferedInputStream bis = null;
        try {
            UniversalDetector detector = new UniversalDetector(null);
            bis = new BufferedInputStream(new FileInputStream(file));
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = bis.read(buffer)) > 0 && !detector.isDone()) {
                detector.handleData(buffer, 0, length);
            }
            return detector.getDetectedCharset();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static HashMap<String, Integer> loadBookIKeywordIntoMemory(String filePath) {
        BufferedReader fbReader = null;
        try {
            File file = new File(filePath);
            HashMap<String, Integer> keywords = new HashMap<>();
            String charset = detectCharset(file);
            if (charset == null) {
                System.out.println("failed for detect charset for " +file.getName());
                return null;
            }
            fbReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            String line;
            while ((line = fbReader.readLine()) != null) {
                String parts[] = line.split("(́|̉|̀|̃| |\\.|,|!|:|-|\\?|\\*|\\(|\\)|\"|\'|;|–|“|”|[0-9]|„|\\\\|…|¿|’|\\/|•|½|¤|‘|Ä|ü|\\]|\\[|\\+|&|	|`)");
                for (String part : parts) {
                    if (part.matches(".*[^a-zA-Z_ýếÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐÐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ].*| +")) {
                        //System.out.println("-" + part + "-");
                    }
                    if (part.length() > 2 && part.length() < 8) {
                        keywords.put(part, 1);
                    }
                }
            }
            return keywords;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            try {
                fbReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
