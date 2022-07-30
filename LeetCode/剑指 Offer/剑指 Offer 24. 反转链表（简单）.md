### 题目描述

这是 LeetCode 上的 **[剑指 Offer 24. 反转链表](https://leetcode.cn/problems/fan-zhuan-lian-biao-lcof/solution/by-ac_oier-nqfc/)** ，难度为 **简单**。

Tag : 「链表」、「迭代」、「递归」



定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

示例:
```
输入: 1->2->3->4->5->NULL

输出: 5->4->3->2->1->NULL
```

限制：
* $0 <= 节点个数 <= 5000$

---

### 迭代

迭代做法只需要使用两个指针即可，复用 `head` 变量来代指当前处理到的节点，使用 `prev` 变量代指原链表中 `head` 的上一节点，只需使用循环构造出所有的 `head.next = prev` 关系即可。

Java 代码：
```Java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;
        }
        return prev;
    }
}
```
TypeScript 代码：
```TypeScript
function reverseList(head: ListNode | null): ListNode | null {
    let prev = null
    while (head != null) {
        const temp = head.next
        head.next = prev
        prev = head
        head = temp
    }
    return prev
};
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 递归

相对而言，递归方法需要一定的抽象能力：将 `reverseList` 作为我们的递归函数，函数的含义为将以 `head` 为头节点进行翻转，并返回新的头节点。

不失一般性考虑某个递归回合：当传入节点 `head` 为空，或者其 `next` 节点为空（即只有单个节点）时，我们 直接返回 `head` 即可。而对于剩余情况，对于某个节点 `head` 而言，我们可以先递归处理它的剩余部分 `reverseList(head.next)` ，此时我们拿到的返回值既是以 `head.next` 为头结点进行翻转后的新链表头结点，也是以 `head` 为头结点进行翻转后的新链表头结点，将其记为 `ans`。我们只需要关注原有 `head` 部分如何处理，即将原来的 `head -> head.next` 关系进行翻转，则有 `head.next.next = head`，并 `head.next` 指向空即可，最后返回 `ans`。

Java 代码：
```Java
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode ans = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return ans;
    }
}
```
TypeScript 代码：
```TypeScript
function reverseList(head: ListNode | null): ListNode | null {
    if (head == null || head.next == null) return head
    const ans = reverseList(head.next)
    head.next.next = head
    head.next = null
    return ans
};
```
* 时间复杂度：$O(n)$
* 空间复杂度：忽略递归带来的额外空间开销，复杂度为 $O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 24` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

