package com.leetcode.from_001_to_010;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/roman-to-integer/
// 题解地址: https://mp.weixin.qq.com/s/QeQbqzJQYOonjPAAZDx01A

class Solution {
    /**
     * 朴素解法
     * 执行用时：执行用时：10 ms, 在所有 Java 提交中击败了 20.45% 的用户
     * 内存消耗：内存消耗：39.1 MB, 在所有 Java 提交中击败了 16.60% 的用户
     */
    Map<String, Integer> map = new HashMap<>(){{
        put("M", 1000);
        put("CM", 900);
        put("D",  500);
        put("CD", 400);
        put("C",  100);
        put("XC", 90);
        put("L",  50);
        put("XL", 40);
        put("X",  10);
        put("IX", 9);
        put("V",  5);
        put("IV", 4);
        put("I",  1);
    }};
    public int romanToInt(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n;) {
            String str = null;
            if (i + 1 < n && map.get(s.substring(i + 1, i + 2)) > map.get(s.substring(i, i + 1))) {
                str = s.substring(i, i + 2);
                i += 2;
            } else {
                str = s.substring(i, i + 1);
                i++;
            }
            ans += map.get(str);
        }
        return ans;
    }
}