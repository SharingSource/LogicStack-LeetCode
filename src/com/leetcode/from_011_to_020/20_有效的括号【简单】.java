package com.leetcode.from_001_to_010;

import java.util.*;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/valid-parentheses/
// 题解地址: https://mp.weixin.qq.com/s/BHPh4N6X909KMzqhchRGMg

class Solution {
    /**
     * 栈 + 哈希表解法
     * 执行用时：2 ms, 在所有 Java 提交中击败了 74.00% 的用户
     * 内存消耗：36.7 MB, 在所有 Java 提交中击败了 32.69% 的用户
     */
    HashMap<Character, Character> map = new HashMap<Character, Character>(){{
        put(']', '[');
        put('}', '{');
        put(')', '(');
    }};
    public boolean isValid(String s) {
        Deque<Character> d = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                d.addLast(c);
            } else {
                if (!d.isEmpty() && d.peekLast() == map.get(c)) {
                    d.pollLast();
                } else {
                    return false;
                }
            }
        }
        return d.isEmpty();
    }

    /**
     * 栈 + ASCII 差值解法
     * 执行用时：2 ms, 在所有 Java 提交中击败了 74.00% 的用户
     * 内存消耗：36.5 MB, 在所有 Java 提交中击败了 75.28% 的用户
     */
    public boolean isValid_2(String s) {
        Deque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int u = c - '0';
            if (c == '(' || c == '{' || c == '[') {
                d.addLast(u);
            } else {
                if (!d.isEmpty() && Math.abs(d.peekLast() - u) <= 2) {
                    d.pollLast();
                } else {
                    return false;
                }
            }
        }
        return d.isEmpty();
    }

}