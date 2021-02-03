package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/3sum-closest/
// 题解地址: https://mp.weixin.qq.com/s/o-AIu20jBR9IPReVBVjhrQ

class Solution {
    /**
     * 排序 +双指针解法
     * 执行用时：执行用时：6 ms, 在所有 Java 提交中击败了 56.35% 的用户
     * 内存消耗：内存消耗：38 MB, 在所有 Java 提交中击败了 79.60% 的用户
     */
    public int threeSumClosest(int[] nums, int t) {
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (Math.abs(sum - t) < Math.abs(ans - t)) ans = sum;
                if (ans == t) {
                    return t;
                } else if (sum > t) {
                    k--;
                } else if (sum < t) {
                    j++;
                }
            }
        }
        return ans;
    }
}