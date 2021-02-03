package com.leetcode.from_001_to_010;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/integer-to-roman/
// 题解地址: https://mp.weixin.qq.com/s/D6DL1LeIehPmudWmrsaT5w

class Solution {
    /**
     * 贪心解法
     * 执行用时：执行用时：5 ms, 在所有 Java 提交中击败了 92.35% 的用户
     * 内存消耗：内存消耗：37.9 MB, 在所有 Java 提交中击败了 77.60% 的用户
     */
    int[] val = new int[]{1000,900,500,400,100,90,50,40,10,9,5,4,1};
    String[] str = new String[]{"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    public String intToRoman(int x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length && x > 0; i++) {
            int cv = val[i];
            String cs = str[i];
            while (x >= cv) {
                sb.append(cs);
                x -= cv;
            }
        }
        return sb.toString();
    }
}