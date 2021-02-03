package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/4sum/
// 题解地址: https://mp.weixin.qq.com/s/Z53udnAX3J-P229FrATHpw

class Solution {
    /**
     * 排序 + 双指针解法
     * 执行用时：25 ms, 在所有 Java 提交中击败了 35.40% 的用户
     * 内存消耗：38.8 MB, 在所有 Java 提交中击败了 69.69% 的用户
     */
    public List<List<Integer>> fourSum(int[] nums, int t) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) { // 确定第一个数
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 对第一个数进行去重（相同的数只取第一个）
            for (int j = i + 1; j < n; j++) { // 确定第二个数
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 对第二个数进行去重（相同的数只取第一个）
                // 确定第三个数和第四个数
                int k = j + 1, p = n - 1;
                while (k < p) {

                    // 对第三个数进行去重（相同的数只取第一个）
                    while (k > j + 1 && k < n && nums[k] == nums[k - 1]) k++;
                    // 如果 k 跳过相同元素之后的位置超过了 p，本次循环结束
                    if (k >= p) break;

                    int sum = nums[i] + nums[j] + nums[k] + nums[p];
                    if (sum == t) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k], nums[p]));
                        k++;
                    } else if (sum > t) {
                        p--;
                    } else if (sum < t) {
                        k++;
                    }
                }
            }
        }
        return ans;
    }
}
