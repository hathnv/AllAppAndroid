/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nlp.nb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import com.nlp.utils.Utils;

public class GanNhan {

    private float swordHeroStoryProbInln, otherTypeStoryProbInln;
    private HashMap<String, Float> swordHeroDisProbInln;
    private HashMap<String, Float> otherTypeDisProbInln;

    public static final int BOOK_TYPE_SWORD_HERO = 1, BOOK_TYPE_OTHER = 2;

    public GanNhan(String naiveBayesFile, int numKeyWordUse) {
        swordHeroDisProbInln = new HashMap<>();
        otherTypeDisProbInln = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(naiveBayesFile));
            String line;
            String[] parts;
       
            line = br.readLine();
            parts = line.split(" ");
            swordHeroStoryProbInln = (float)Math.log(Float.parseFloat(parts[0]));
            otherTypeStoryProbInln = (float)Math.log(Float.parseFloat(parts[1]));
      
            for (int i = 0; i < numKeyWordUse && (line = br.readLine()) != null; i++) {
                parts = line.split(" ");
                swordHeroDisProbInln.put(parts[2], (float) Math.log(Float.parseFloat(parts[0])));
                otherTypeDisProbInln.put(parts[2], (float) Math.log(Float.parseFloat(parts[1])));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GanNhan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GanNhan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(GanNhan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Pair<Integer, Float> detectStory(String filePath) {
        HashMap<String, Integer> keywords = Utils.loadBookIKeywordIntoMemory(filePath);
        float swordHeroNaiveBayesVal = swordHeroStoryProbInln, otherTypeNaiveBayesVal = otherTypeStoryProbInln;//naive bayes value for classify
        Float tempSwordHero, tempOtherType;
        if (keywords != null) {
            for (String key : keywords.keySet()) {
                tempSwordHero = swordHeroDisProbInln.get(key);
                tempOtherType = otherTypeDisProbInln.get(key);
                if (tempSwordHero != null && tempOtherType != null) {
                    swordHeroNaiveBayesVal += tempSwordHero;
                    otherTypeNaiveBayesVal += tempOtherType;
                }
            }
            if (swordHeroNaiveBayesVal > otherTypeNaiveBayesVal) {
                return new Pair<>(BOOK_TYPE_SWORD_HERO, 100 * otherTypeNaiveBayesVal / (swordHeroNaiveBayesVal + otherTypeNaiveBayesVal));
            } else {
                return new Pair<>(BOOK_TYPE_OTHER, 100 * swordHeroNaiveBayesVal / (swordHeroNaiveBayesVal + otherTypeNaiveBayesVal));
            }
        }
        return null;
    }
}
