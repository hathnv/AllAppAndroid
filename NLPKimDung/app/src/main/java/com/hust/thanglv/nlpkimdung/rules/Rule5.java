package com.hust.thanglv.nlpkimdung.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giang on 11/30/2016.
 */

public class Rule5 extends Rule {
    /*
     * Từ tiếng Việt nếu có > 1 nguyên âm thì các nguyên âm này phải nằm cạnh
	 * nhau
	 */

    public int countVowels(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (isVowel(input.charAt(i)))
                count++;
        }
        return count;
    }

    public boolean isVowel(char c) {
        char[] vowels = {'a', 'á', 'à', 'ả', 'ạ', 'ã',
                'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ',
                'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ',
                'e', 'é', 'è', 'ẻ', 'ẽ', 'ẹ',
                'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ',
                'o', 'ó', 'ò', 'ỏ', 'õ', 'ọ',
                'i', 'í', 'ì', 'ỉ', 'ĩ', 'ị',
                'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ',
                'ơ', 'ớ', 'ờ', 'ở', 'ỡ', 'ợ',
                'u', 'ú', 'ù', 'ủ', 'ũ', 'ụ',
                'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự',
                'y', 'ý', 'ỳ', 'ỷ', 'ỵ'
        };
        for (int i = 0; i < vowels.length; i++) {
            if (vowels[i] == c)
                return true;
        }
        return false;
    }

    @Override
    public boolean checkInvalidate(String input) {
        int count = countVowels(input);
        if (count < 2)
            return false;
        if (count > 3)
            return true;
        int first = 0;
        for (int i = 0; i < input.length(); i++) {
            if (isVowel(input.charAt(i))) {
                first = i;
                break;
            }
        }
        for (int i = first + 1; i < first + count; i++) {
            if (!isVowel(input.charAt(i)))
                return true;
        }
        return false;
    }
}
