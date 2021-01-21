package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/reverse-integer/
// 题解地址: https://mp.weixin.qq.com/s/4DaowDYlMOSKyRmBx44SkQ

class SolutionReverseInteger {
    /**
     * 不完美解法
     * 执行用时：执行用时：1 ms, 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗：内存消耗：35.4 MB, 在所有 Java 提交中击败了 39.60% 的用户
     */
    public int reverse_1(int x) {
        long ans = 0;
        while (x != 0) {
            ans = ans * 10 + x % 10;
            x = x / 10;
        }
        return (int)ans == ans ? (int)ans : 0;
    }

    /**
     * 完美解法
     * 执行用时：执行用时：1 ms, 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗：内存消耗：35.4 MB, 在所有 Java 提交中击败了 39.60% 的用户
     */
    public int reverse_2(int x) {
        int ans = 0;
        while (x != 0) {
            if (x > 0 && ans > (Integer.MAX_VALUE - x % 10) / 10) {
                return 0;
            }
            if (x < 0 && ans < (Integer.MIN_VALUE - x % 10) / 10) {
                return 0;
            }
            ans = ans * 10 + x % 10;
            x /= 10;
        }
        return ans;
    }
}
