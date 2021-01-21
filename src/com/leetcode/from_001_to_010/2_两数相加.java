package com.leetcode.from_001_to_010;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/add-two-numbers/
// 题解地址: https://mp.weixin.qq.com/s/V9r0ygOXl7nubP2ROkNJvw

class SolutionAddTwoNumbers {

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 朴素解法（哨兵技巧）
     * 执行用时：2 ms, 在所有 Java 提交中击败了 99.90% 的用户
     * 内存消耗：39 MB, 在所有 Java 提交中击败了 20.22% 的用户
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tmp = dummy;
        int t = 0;
        while (l1 != null || l2 != null) {
            int a = l1 != null ? l1.val : 0;
            int b = l2 != null ? l2.val : 0;
            t = a + b + t;
            tmp.next = new ListNode(t % 10);
            t /= 10;
            tmp = tmp.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (t > 0) {
            tmp.next = new ListNode(t);
        }
        return dummy.next;
    }
}
