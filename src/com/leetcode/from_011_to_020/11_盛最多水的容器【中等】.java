package com.leetcode.from_001_to_010;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/container-with-most-water/
// 题解地址: https://mp.weixin.qq.com/s/y81GCvI4KpqUWIwgjlNNQg

class Solution {
    /**
     * 朴素解法
     * 执行用时：执行用时：846 ms, 在所有 Java 提交中击败了 16.31% 的用户
     * 内存消耗：内存消耗：40.3 MB, 在所有 Java 提交中击败了 5.60% 的用户
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int w = j - i;
                int h = Math.min(height[i], height[j]);
                ans = Math.max(w * h, ans);
            }
        }
        return ans;
    }


    /**
     * 双指针 + 贪心解法
     * 执行用时：执行用时：3 ms, 在所有 Java 提交中击败了 92.35% 的用户
     * 内存消耗：内存消耗：40.2 MB, 在所有 Java 提交中击败了 15.72% 的用户
     */
    public int maxArea_2(int[] height) {
        int n = height.length;
        int i = 0, j = n - 1;
        int ans = 0;
        while (i < j) {
            ans = Math.max(ans, (j - i) * Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return ans;
    }
}