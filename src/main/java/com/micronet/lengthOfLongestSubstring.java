package com.micronet;

import java.util.HashSet;

public class lengthOfLongestSubstring {
    public static void main(String[] args) {



        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
       // System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
       // System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
    }
    public static int lengthOfLongestSubstring(String s) {

        HashSet<Character> set = new HashSet<>();

        int left = 0;
        int maxLength = 0;
        int start = 0;


        for (int right =0; right<s.length(); right++){

            while(set.contains(s.charAt(right))){
                set.remove(s.charAt(right));
                left++;
            }

            set.add(s.charAt(right));

            if(right - left + 1> maxLength){
                maxLength = right -left +1;
                start = left;
            }

        }

        return s.substring(start, start+maxLength).length();


        /*for (int right = 0; right < s.length(); right++) {

            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(s.charAt(right));

            maxLength = Math.max(maxLength, right - left + 1);
        }*/

        //return maxLength;
    }
}
