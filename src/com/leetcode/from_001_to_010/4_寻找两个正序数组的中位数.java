package com.leetcode.from_001_to_010;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
// 题解地址: https://mp.weixin.qq.com/s/VpDQpyBs_s-gvsAxnODqmQ

class SolutionMedianOfWwoSortedArrays {
    /**
     * 朴素解法
     * 执行用时：执行用时：4 ms, 在所有 Java 提交中击败了 23.82% 的用户
     * 内存消耗：内存消耗：39.8 MB, 在所有 Java 提交中击败了 42.45% 的用户
     */
    public double findMedianSortedArrays_1(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[] arr = new int[n + m];
        int idx = 0;
        for (int i : nums1) {
            arr[idx++] = i;
        }
        for (int i : nums2) {
            arr[idx++] = i;
        }
        Arrays.sort(arr);
        int l = arr[(n + m) / 2], r = arr[(n + m - 1) / 2];
        return (l + r) / 2.0;
    }

    /**
     * 分治解法
     * 执行用时：执行用时：3 ms, 在所有 Java 提交中击败了 82.50% 的用户
     * 内存消耗：内存消耗：39.9 MB, 在所有 Java 提交中击败了 16.30% 的用户
     */
    public double findMedianSortedArrays_2(int[] nums1, int[] nums2) {
        int tot = nums1.length + nums2.length;
        if (tot % 2 == 0) {
            int left = find(nums1, 0, nums2, 0, tot / 2);
            int right = find(nums1, 0, nums2, 0, tot / 2 + 1);
            return (left + right) / 2.0;
        } else {
            return find(nums1, 0, nums2, 0, tot / 2 + 1);
        }
    }
    int find(int[] n1, int i, int[] n2, int j, int k) {
        if (n1.length - i > n2.length - j) {
            return find(n2, j, n1, i, k);
        }
        if (i >= n1.length) {
            return n2[j + k - 1];
        }
        if (k == 1) {
            return Math.min(n1[i], n2[j]);
        } else {
            int si = Math.min(i + (k / 2), n1.length), sj = j + k - (k / 2);
            if (n1[si - 1] > n2[sj - 1]) {
                return find(n1, i, n2, sj, k - (sj - j));
            } else {
                return find(n1, si, n2, j, k - (si - i));
            }
        }
    }
}
