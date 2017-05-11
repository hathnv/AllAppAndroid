/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nlp.nb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JOptionPane;
import static com.nlp.utils.Utils.loadAllBookIntoMemory;

public class Trainning {

    public static void trainingData(String swordHeroStoryFolder, String otherTypeStoryFolder) {
        try {
            Pair<Integer, HashMap<String, Integer>> allSwordHeroBookKeywords = loadAllBookIntoMemory(swordHeroStoryFolder);
            Pair<Integer, HashMap<String, Integer>> allOtherTypeBookKeywords = loadAllBookIntoMemory(otherTypeStoryFolder);
            writeDistributeProbabilityToFile(allSwordHeroBookKeywords.getKey(), allOtherTypeBookKeywords.getKey(), allSwordHeroBookKeywords.getValue(), allOtherTypeBookKeywords.getValue());
            JOptionPane.showMessageDialog(null, "naive bayes file created");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void writeDistributeProbabilityToFile(float numSwordHeroStory, float numOtherTypeStory, HashMap<String, Integer> swordHeroKeyWord, HashMap<String, Integer> otherTypeKeyword) {
        try {
            String fileName = "naiveBayes.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(String.format("%.10f %.10f", numSwordHeroStory / (numSwordHeroStory + numOtherTypeStory), numOtherTypeStory / (numSwordHeroStory + numOtherTypeStory)));
            bw.newLine();
            Map<String, Pair<Double, Double>> allKeyWords = new HashMap<>();
            long countSwordHeroKeyWord = 0, countOtherTypeKeyword = 0;
            for (Map.Entry<String, Integer> swordHeroKeyword : swordHeroKeyWord.entrySet()) {
                countSwordHeroKeyWord += swordHeroKeyword.getValue();
                allKeyWords.put(swordHeroKeyword.getKey(), null);
            }
            for (Map.Entry<String, Integer> swordHeroKeyword : otherTypeKeyword.entrySet()) {
                countOtherTypeKeyword += swordHeroKeyword.getValue();
                allKeyWords.put(swordHeroKeyword.getKey(), null);
            }
            Iterator allKeyIter = allKeyWords.entrySet().iterator();
            while (allKeyIter.hasNext()) {//calculate distribute probability
                Map.Entry<String, Pair<Double, Double>> entry = (Map.Entry<String, Pair<Double, Double>>) allKeyIter.next();
                double swordHeroCount = swordHeroKeyWord.get(entry.getKey()) == null ? 0.0 : (float) (swordHeroKeyWord.get(entry.getKey()));
                double otherTypeCount = otherTypeKeyword.get(entry.getKey()) == null ? 0.0 : (float) (otherTypeKeyword.get(entry.getKey()));
                double swordHeroProb = (swordHeroCount + 1.0) / (allKeyWords.size() + countSwordHeroKeyWord);
                double otherTypeProb = (otherTypeCount + 1.0) / (allKeyWords.size() + countOtherTypeKeyword);
                if (swordHeroProb > 0 && otherTypeProb > 0) {
                    allKeyWords.put(entry.getKey(), new Pair<>(swordHeroProb, otherTypeProb));
                } else {
                    System.out.println("error for word " + entry.getKey());
                }
            }
            allKeyWords = sortByValues(allKeyWords);
            int a = 1;
            for (Map.Entry<String, Pair<Double, Double>> entry : allKeyWords.entrySet()) {
                bw.write(String.format("%.10f %.10f %s", entry.getValue().getKey(), entry.getValue().getValue(), entry.getKey()));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception ex) {
            Logger.getLogger(Trainning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static <K, V extends Comparable<V>> Map<K, Pair<Double, Double>> sortByValues(final Map<K, Pair<Double, Double>> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                double firtVal = Math.abs(map.get(k1).getKey() - map.get(k1).getValue());
                double secondVal = Math.abs(map.get(k2).getKey() - map.get(k2).getValue());
                return firtVal < secondVal ? 1 : -1;
            }
        };
        Map<K, Pair<Double, Double>> sortedByValues = new TreeMap<K, Pair<Double, Double>>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
