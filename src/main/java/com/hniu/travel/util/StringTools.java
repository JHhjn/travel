package com.hniu.travel.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringTools {


    public String removeExcessText(String str){
        char[] chars = str.toCharArray();
        List<Character> newChars=new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i]=='【'){
                while (true){
                    if (chars[i]=='】'){
                        chars[i++]=0;
                        break;
                    }
                    chars[i++]=0;
                }
            }
            if (i>=chars.length-1)break;
            newChars.add(chars[i]);
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Character newChar : newChars) {
            stringBuffer.append(newChar);
        }
        String s = stringBuffer.toString();
        return s;
    }
}
