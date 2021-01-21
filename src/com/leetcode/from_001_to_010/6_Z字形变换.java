package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/zigzag-conversion/
// 题解地址: https://mp.weixin.qq.com/s/XYfYbpTxLMmuykYRqf1NfA

class SolutionZigzagConversio {
    /**
     * 直观规律解法
     * 执行用时：执行用时：3 ms, 在所有 Java 提交中击败了 98.04% 的用户
     * 内存消耗：内存消耗：37.7 MB, 在所有 Java 提交中击败了 86.57% 的用户
     */
    public String convert_1(String s, int r) {
        int n = s.length();
        if (n == 1 || r == 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            if (i == 0 || i == r - 1) {
                int j = i;
                int rowOffset = (r - 1) * 2 - 1;
                while (j < n) {
                    sb.append(s.charAt(j));
                    j += rowOffset + 1;
                }
            } else {
                int j = i;

                int topOffset = i * 2 - 1;

                int bottomRow = r - i - 1;
                int bottomOffset = bottomRow * 2 - 1;

                boolean flag = true;
                while (j < n) {
                    sb.append(s.charAt(j));
                    j += flag ? bottomOffset + 1 : topOffset + 1;
                    flag = !flag;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 数学规律解法
     * 执行用时：执行用时：3 ms, 在所有 Java 提交中击败了 98.04% 的用户
     * 内存消耗：内存消耗：37.4 MB, 在所有 Java 提交中击败了 88.57% 的用户
     */
    public String convert_2(String s, int r) {
        int n = s.length();
        if (n == 1 || r == 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            if (i == 0 || i == r - 1) {
                int pos = i;
                int offset = 2 * (r - 1);
                while (pos < n) {
                    sb.append(s.charAt(pos));
                    pos += offset;
                }
            } else {
                int pos1 = i, pos2 = 2 * r - i - 2;
                int offset = 2 * (r - 1);
                while (pos1 < n || pos2 < n) {
                    if (pos1 < n) {
                        sb.append(s.charAt(pos1));
                        pos1 += offset;
                    }
                    if (pos2 < n) {
                        sb.append(s.charAt(pos2));
                        pos2 += offset;
                    }
                }
            }
        }
        return sb.toString();
    }
}
