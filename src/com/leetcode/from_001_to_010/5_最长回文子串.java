package com.leetcode.from_001_to_010;

import java.util.Arrays;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/longest-palindromic-substring/
// 题解地址: https://mp.weixin.qq.com/s/XUMuACCYoH3MVJ0b5Bdqtg

class SolutionLongestPalindromicSubstring {
    /**
     * 朴素解法
     * 执行用时：执行用时：39 ms, 在所有 Java 提交中击败了 72.50% 的用户
     * 内存消耗：内存消耗：39.3 MB, 在所有 Java 提交中击败了 48.45% 的用户
     */
    public String longestPalindrome_1(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            // 回文串为奇数
            int l = i - 1, r = i + 1;
            String sub = getString(s, l, r);
            if (sub.length() > ans.length()) {
                ans = sub;
            }

            // 回文串为偶数
            l = i - 1;
            r = i + 1 - 1;
            sub = getString(s, l, r);
            if (sub.length() > ans.length()) {
                ans = sub;
            }
        }
        return ans;
    }
    String getString(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    /**
     * Manacher 算法
     * 执行用时：执行用时：9 ms, 在所有 Java 提交中击败了 95.37% 的用户
     * 内存消耗：内存消耗：38.6 MB, 在所有 Java 提交中击败了 73.48% 的用户
     */
    public String longestPalindrome_2(String s) {
        if (s.length() == 1) {
            return s;
        }

        char[] chars = manacherString(s);
        int n = chars.length;
        int[] pArr = new int[n];
        int C = -1, R = -1, pos = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            pArr[i] = i < R ? Math.min(pArr[C * 2 - i], R - i) : 1;
            while (i + pArr[i] < n && i - pArr[i] > -1) {
                if (chars[i + pArr[i]] == chars[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            if (pArr[i] > max) {
                max = pArr[i];
                pos = i;
            }
        }
        int offset = pArr[pos];
        StringBuilder sb = new StringBuilder();
        for (int i = pos - offset + 1; i <= pos + offset - 1; i++) {
            if (chars[i] != '#') {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
    char[] manacherString(String s) {
        char[] chars = new char[s.length() * 2 + 1];
        for (int i = 0, idx = 0; i < chars.length; i++) {
            chars[i] = (i & 1) == 0 ? '#' : s.charAt(idx++);
        }
        return chars;
    }
}
