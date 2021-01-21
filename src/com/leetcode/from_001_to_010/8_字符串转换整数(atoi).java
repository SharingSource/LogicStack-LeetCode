package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/string-to-integer-atoi/
// 题解地址: https://mp.weixin.qq.com/s/ZNL7VqJK9x6qo3UIEL73OQ

class SolutionStringToIntegerAuoi {
    /**
     * 朴素解法
     * 执行用时：执行用时：2 ms, 在所有 Java 提交中击败了 99.35% 的用户
     * 内存消耗：内存消耗：38.4 MB, 在所有 Java 提交中击败了 43.60% 的用户
     */
    public int myAtoi(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int idx = 0;

        // 去除前导空格，如果去完前导空格后无字符了，返回 0
        while (idx < n && chars[idx] == ' ') {
            idx++;
        }
        if (idx == n) {
            return 0;
        }

        // 检查第一个字符：可以为正负号/数字
        boolean isNeg = false;
        if (chars[idx] == '-') {
            idx++;
            isNeg = true;
        } else if (chars[idx] == '+') {
            idx++;
        } else if (!Character.isDigit(chars[idx])) {
            return 0;
        }

        int ans = 0;
        while (idx < n && Character.isDigit(chars[idx])) {
            int cur = chars[idx++] - '0';
            // 和上一题的“【刷穿 LeetCode】7. 整数反转”一样，防止 ans = ans * 10 + cur 溢出
            // 等价变形为 ans > (Integer.MAX_VALUE - cur) / 10 进行预判断
            if (ans > (Integer.MAX_VALUE - cur) / 10) {
                return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + cur;
        }
        return isNeg ? -ans : ans;
    }
}
