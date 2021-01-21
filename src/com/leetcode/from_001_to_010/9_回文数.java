package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/palindrome-number/
// 题解地址: https://mp.weixin.qq.com/s/flEoRK2L9VIerNDVrZwR6Q

class SolutionPalindromeNumber {
    /**
     * 字符串解法
     * 执行用时：执行用时：11 ms, 在所有 Java 提交中击败了 48.99% 的用户
     * 内存消耗：内存消耗：38.5 MB, 在所有 Java 提交中击败了 7.60% 的用户
     */
    public boolean isPalindrome_1(int x) {
        String s = String.valueOf(x);
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        return sb.toString().equals(s);
    }

    /**
     * 非字符串解法（完全翻转）
     * 执行用时：执行用时：14 ms, 在所有 Java 提交中击败了 18.01% 的用户
     * 内存消耗：内存消耗：37.9 MB, 在所有 Java 提交中击败了 42.08% 的用户
     */
    public boolean isPalindrome_2_1(int x) {
        if (x < 0) {
            return false;
        }
        long ans = 0;
        int t = x;
        while (x > 0) {
            ans = ans * 10 + x % 10;
            x /= 10;
        }
        return ans - t == 0;
    }

    /**
     * 非字符串解法（部分翻转）
     * 执行用时：执行用时：9 ms, 在所有 Java 提交中击败了 87.98% 的用户
     * 内存消耗：内存消耗：38.1 MB, 在所有 Java 提交中击败了 26.55% 的用户
     */
    public boolean isPalindrome_2_2(int x) {
        // 对于 负数 和 x0、x00、x000 格式的数，直接返回 flase
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int t = 0;
        while (x > t) {
            t = t * 10 + x % 10;
            x /= 10;
        }
        // 回文长度的两种情况：直接比较 & 忽略中心点（t 的最后一位）进行比较
        return x == t || x == t / 10;
    }
}
