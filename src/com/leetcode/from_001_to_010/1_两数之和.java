package com.leetcode.from_001_to_010;

import java.util.*;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/two-sum/
// 题解地址: https://mp.weixin.qq.com/s/6n1aCrxo-Bs6ApMkrIseFA

class SolutionTwoSum {
    /**
     * 朴素解法
     * 执行用时：0 ms, 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗：38.8 MB, 在所有 Java 提交中击败了 21.69% 的用户
     */
    public int[] twoSum_1(int[] nums, int t) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (t == nums[i] + nums[j]) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{};
    }

    /**
     * 哈希表解法(1)
     * 执行用时：4 ms, 在所有 Java 提交中击败了 42.44% 的用户
     * 内存消耗：38.3 MB, 在所有 Java 提交中击败了 90.81% 的用户
     */
    public int[] twoSum_2_1(int[] nums, int t) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i], b = t - a;
            if (map.get(a) == i) {
                map.remove(a);
            }
            if (map.containsKey(b)) {
                return new int[]{i, map.get(b)};
            }
        }
        return new int[]{};
    }

    /**
     * 哈希表解法(2)
     * 执行用时：0 ms, 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗：38.6 MB, 在所有 Java 提交中击败了 50.37% 的用户
     */
    public int[] twoSum_2_2(int[] nums, int t) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i], b = t - a;
            if (map.containsKey(b)) {
                return new int[]{map.get(b), i};
            }
            map.put(a, i);
        }
        return new int[]{};
    }
}
