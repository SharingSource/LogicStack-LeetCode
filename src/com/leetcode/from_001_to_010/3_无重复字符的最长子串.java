package com.leetcode.from_001_to_010;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
// 题解地址: https://mp.weixin.qq.com/s/6if4K8c4foQW1FtYK4B-XA

class SolutionLongestSubstring {
    /**
     * 双指针 + 哈希表解法
     * 执行用时：执行用时：14 ms, 在所有 Java 提交中击败了 24.64% 的用户
     * 内存消耗：内存消耗：38.5 MB, 在所有 Java 提交中击败了 75.14% 的用户
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0, j = 0; j < s.length(); j++) {
            char r = s.charAt(j);
            map.put(r, map.getOrDefault(r, 0) + 1);
            while (map.get(r) > 1) {
                char l = s.charAt(i);
                map.put(l, map.get(l) - 1);
                i++;
            }
            ans = Math.max(ans, j - i + 1);
        }
        return ans;
    }
}
