### 题目描述

这是 LeetCode 上的 **[剑指 Offer 22. 链表中倒数第k个节点](https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/solution/gong-shui-san-xie-yi-ti-san-jie-zhan-dui-w3rz/)** ，难度为 **简单**。

Tag : 「链表」、「栈」、「队列」、「快慢指针」




输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。

例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。

示例：
```
给定一个链表: 1->2->3->4->5, 和 k = 2.

返回链表 4->5.
```

---

### 栈/队列 解法

一个使用额外空间的解法是利用栈（队列），将所有的节点压入占中栈（队列）中，令当前栈（队列）容量为 $cnt$。

然后从栈顶（队列头）弹出 $k$ 个（$cnt - k + 1$ 个）元素，最后一个出栈（出队列）的元素即是答案。

![image.png](https://pic.leetcode-cn.com/1630544371-cFgLAj-image.png)

代码（栈）：
```Java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        Deque<ListNode> d = new ArrayDeque<>();
        while (head != null) {
            d.addLast(head);
            head = head.next;
        }
        ListNode ans = null;
        while (k-- > 0) ans = d.pollLast();
        return ans;
    }
}
```
代码（队列）：
```Java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        Deque<ListNode> d = new ArrayDeque<>();
        while (head != null) {
            d.addLast(head);
            head = head.next;
        }
        k = d.size() - k + 1;
        ListNode ans = null;
        while (k-- > 0) ans = d.pollFirst();
        return ans;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 差值法

我们可以先对链表进行一次完整遍历，拿到总长度 $cnt$，最后由 $cnt - k$ 即是倒数第 $k$ 个节点距离 $head$ 节点的距离。

![image.png](https://pic.leetcode-cn.com/1630543998-jOEXKC-image.png)

代码：
```Java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        int cnt = 0;
        ListNode tmp = head;
        while (tmp != null && ++cnt > 0) tmp = tmp.next;
        cnt -= k;
        while (cnt-- > 0) head = head.next;
        return head; 
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 快慢指针

事实上，我们还可以使用「快慢指针」进行求解。

起始先让快指针 `fast` 先走 $k$ 步，此时 `fast` 和 `slow` 之间距离为 $k$，之后让 `fast` 和 `slow` 指针一起走（始终维持相对距离为 $k$），当 `fast` 到达链表尾部，`slow` 即停在倒数第 $k$ 个节点处。

![image.png](https://pic.leetcode-cn.com/1630544021-KRXyeD-image.png)

代码：
```Java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode slow = head, fast = head;
        while (k-- > 0) fast = fast.next;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 22. 链表中倒数第k个节点` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

