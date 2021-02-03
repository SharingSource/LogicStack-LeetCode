package com.leetcode.from_001_to_010;

import java.util.Arrays;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/longest-common-prefix/
// 题解地址: https://mp.weixin.qq.com/s/OI12dqPi9J6wng5LYKdtXA

class Solution {
    /**
     * 朴素解法
     * 执行用时：执行用时：1 ms, 在所有 Java 提交中击败了 94.35% 的用户
     * 内存消耗：内存消耗：36.3 MB, 在所有 Java 提交中击败了 85.60% 的用户
     */
    public String longestCommonPrefix(String[] ss) {
        String ans = "";
        if (ss.length == 0) return ans;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String s = ss[0];
            if (i >= s.length()) return ans;
            char c = ss[0].charAt(i);
            for (String item : ss) {
                if (i >= item.length() || item.charAt(i) != c) return ans;
            }
            ans += String.valueOf(c);
        }
        return ans;
    }
}