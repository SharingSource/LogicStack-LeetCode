package com.leetcode.from_001_to_010;

/**
 * @author: 宫水三叶
 * @公众号: 宫水三叶的刷题日记
 */

// 原题地址: https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
// 题解地址: https://mp.weixin.qq.com/s/M20J54VYbwCy0Echq9z4OQ

class Solution {
    /**
     * 快慢指针解法
     * 执行用时：0 ms, 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗：26.7 MB, 在所有 Java 提交中击败了 5.69% 的用户
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;
        while (n-- > 0) fast = fast.next;

        if (fast == null) {
            head = slow.next;
        } else {
            while (fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }
            slow.next = slow.next.next;
        }
        return head;
    }
}
