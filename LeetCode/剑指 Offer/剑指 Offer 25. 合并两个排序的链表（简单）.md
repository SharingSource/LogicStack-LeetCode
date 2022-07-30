### 题目描述

这是 LeetCode 上的 **[剑指 Offer 25. 合并两个排序的链表](https://leetcode.cn/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/solution/by-ac_oier-d6wf/)** ，难度为 **简单**。

Tag : 「链表」、「迭代」、「递归」



输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。

示例1：
```
输入：1->2->4, 1->3->4

输出：1->1->2->3->4->4
```

限制：
* $0 <= 链表长度 <= 1000$

---

### 迭代 - 二路归并

使用「迭代」方式求解的话，本质与合并两个有序数组并无区别。

创建一个虚拟头结点 `dummy` 来拼接合并后的链表，`cur` 代表当前合并链表的末尾位置，同时复用入参的 `l1` 和 `l2` 来进行构造。

当 `l1` 和 `l2` 均不为空的时候，找到两者中的较小值拼接到 `cur ` 的后面，若 `l1` 和 `l2` 任一为空，则将另外的链表拼接完。

Java 代码：
```Java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), cur = dummy;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    cur.next = l1;
                    cur = cur.next; l1 = l1.next;
                } else {
                    cur.next = l2;
                    cur = cur.next; l2 = l2.next;
                }
            } else if (l1 != null) {
                cur.next = l1;
                cur = cur.next; l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next; l2 = l2.next;
            }
        }
        return dummy.next;
    }
}
```
TypeScript 代码：
```TypeScript
function mergeTwoLists(l1: ListNode | null, l2: ListNode | null): ListNode | null {
    let dummy = new ListNode(-1), cur = dummy
    while (l1 != null || l2 != null) {
        if (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1
                cur = cur.next; l1 = l1.next
            } else {
                cur.next = l2
                cur = cur.next; l2 = l2.next
            }
        } else if (l1 != null) {
            cur.next = l1
            cur = cur.next; l1 = l1.next
        } else {
            cur.next = l2
            cur = cur.next; l2 = l2.next
        }
    }
    return dummy.next
};
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：$O(1)$

---

### 递归

另一种实现方式是使用「递归」进行构造。

直接将 `mergeTwoLists` 作为递归函数，该函数的定义为传入两个「有序」链表，并返回合并链表的头结点。

显然当 `l1` 或者 `l2` 任一为空时，返回另一链表即可。

而对于其余一般情况而言，根据 `l1` 和 `l2` 的节点值分情况讨论：

* 若 `l1.val < l2.val`，说明此时 `l1` 必然是当前构造回合中值最小的节点，也就是需要被返回的头结点，而 `l1` 后接的内容应该是 `mergeTwoLists(l1.next, l2)`，含义 `l1` 后接的内容应该是由有序链表 `l1.next` 和 `l2` 合并而来；
* 其余情况 `l1.val >= l2.val`，分析同理，此时有 `l2.next = mergeTwoLists(l2.next, l1)`，同时返回 `l2`。

Java 代码：
```Java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        else if (l2 == null) return l1;
        else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l2.next, l1);
            return l2;
        }
    }
}
```
TypeScript 代码：
```TypeScript
function mergeTwoLists(l1: ListNode | null, l2: ListNode | null): ListNode | null {
    if (l1 == null) return l2
    else if (l2 == null) return l1
    else if (l1.val < l2.val) {
        l1.next = mergeTwoLists(l1.next, l2)
        return l1
    } else {
        l2.next = mergeTwoLists(l2.next, l1)
        return l2
    }
};
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：忽略递归带来的额外空间开销，复杂度为 $O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 25` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

