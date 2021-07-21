### 题目描述

这是 LeetCode 上的 **[剑指 Offer 52. 两个链表的第一个公共节点](https://leetcode-cn.com/problems/liang-ge-lian-biao-de-di-yi-ge-gong-gong-jie-dian-lcof/solution/gong-shui-san-xie-zhao-liang-tiao-lian-b-ifqw/)** ，难度为 **简单**。

Tag : 「链表」



输入两个链表，找出它们的第一个公共节点。

如下面的两个链表：

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_statement.png)

在节点 c1 开始相交。





示例 1：
![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_example_1.png)

```
输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3

输出：Reference of the node with value = 8

输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
```



示例 2：
![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_example_2.png)

```
输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1

输出：Reference of the node with value = 2

输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
```



示例 3：
![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_example_3.png)

```
输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2

输出：null

输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。

解释：这两个链表不相交，因此返回 null。
```

注意：
* 如果两个链表没有交点，返回 null.
* 在返回结果后，两个链表仍须保持原有的结构。
* 可假定整个链表结构中没有循环。
* 程序尽量满足 $O(n)$ 时间复杂度，且仅用 $O(1)$ 内存。

---

### 朴素解法

一个朴素的解法自然是两层枚举，逐个检查哪个节点相同。

![image.png](https://pic.leetcode-cn.com/1626830901-LPHwDT-image.png)

代码：
```Java
public class Solution {
    public ListNode getIntersectionNode(ListNode a, ListNode b) {
        for (ListNode h1 = a; h1 != null ; h1 = h1.next) {
            for (ListNode h2 = b; h2 != null ; h2 = h2.next) {
                if (h1 == h2) return h1;
            }
        }
        return null;
    }
}
```
* 时间复杂度：$O(n * m)$
* 空间复杂度：$O(1)$

---

### 栈解法

这是一种「从后往前」找的方式。

将两条链表分别压入两个栈中，然后循环比较两个栈的栈顶元素，同时记录上一位栈顶元素。

当遇到第一个不同的节点时，结束循环，上一位栈顶元素即是答案。

![image.png](https://pic.leetcode-cn.com/1626830926-HwTmvx-image.png)

代码：
```Java
public class Solution {
    public ListNode getIntersectionNode(ListNode a, ListNode b) {
        Deque<ListNode> d1 = new ArrayDeque<>(), d2 = new ArrayDeque<>();
        while (a != null) {
            d1.add(a);
            a = a.next;
        }
        while (b != null) {
            d2.add(b);
            b = b.next;
        }
        ListNode ans = null;
        while (!d1.isEmpty() && !d2.isEmpty() && d1.peekLast() == d2.peekLast()) {
            ans = d1.pollLast();
            d2.pollLast();
        }
        return ans;
    }
}
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：$O(n + m)$

---

### Set 解法

这是一种「从前往后」找的方式。

使用 `Set` 数据结构，先对某一条链表进行遍历，同时记录下来所有的节点。

然后在对第二链条进行遍历时，检查当前节点是否在 `Set` 中出现过，第一个在 `Set` 出现过的节点即是交点。

![image.png](https://pic.leetcode-cn.com/1626830952-eYrqqV-image.png)

代码：
```Java
public class Solution {
    public ListNode getIntersectionNode(ListNode a, ListNode b) {
        Set<ListNode> set = new HashSet<>();
        while (a != null) {
            set.add(a);
            a = a.next;
        }
        while (b != null && !set.contains(b)) b = b.next;
        return b;
    }
}
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：$O(n)$

---

### 差值法

由于两条链表在相交节点后面的部分完全相同，因此我们可以先对两条链表进行遍历，分别得到两条链表的长度，并计算差值 `d`。

让长度较长的链表先走 `d` 步，然后两条链表同时走，第一个相同的节点即是节点。

![image.png](https://pic.leetcode-cn.com/1626830972-azmmse-image.png)

代码：
```Java
public class Solution {
    public ListNode getIntersectionNode(ListNode a, ListNode b) {
        int c1 = 0, c2 = 0;
        ListNode ta = a, tb = b;
        while (ta != null && c1++ >= 0) ta = ta.next;
        while (tb != null && c2++ >= 0) tb = tb.next;
        int d = c1 - c2;
        if (d > 0) {
            while (d-- > 0) a = a.next;
        } else if (d < 0) {
            d = -d;
            while (d-- > 0) b = b.next;
        }
        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return a;
    }
}
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：$O(1)$

---

### 等值法

这是「差值法」的另外一种实现形式，原理同样利用「两条链表在相交节点后面的部分完全相同」。

我们令第一条链表相交节点之前的长度为 `a`，第二条链表相交节点之前的长度为 `b`，相交节点后的公共长度为 `c`（注意 `c` 可能为 $0$，即不存在相交节点）。

分别对两条链表进行遍历：
* 当第一条链表遍历完，移动到第二条链表的头部进行遍历；
* 当第二条链表遍历完，移动到第一条链表的头部进行遍历。

如果存在交点：**第一条链表首次到达「第一个相交节点」的充要条件是第一条链表走了 $a + c + b$ 步，由于两条链表同时出发，并且步长相等，因此当第一条链表走了 $a + c + b$ 步时，第二条链表同样也是走了 $a + c + b$ 步，即 第二条同样停在「第一个相交节点」的位置。**

如果不存在交点：**两者会在走完 $a + c + b + c$ 之后同时变为 $null$，退出循环。**

![image.png](https://pic.leetcode-cn.com/1626830994-gsqrMn-image.png)

代码：
```Java
public class Solution {
    public ListNode getIntersectionNode(ListNode a, ListNode b) {
        ListNode ta = a, tb = b;
        while (ta != tb) {
            ta = ta == null ? b : ta.next;
            tb = tb == null ? a : tb.next;
        }
        return ta;
    }
}
```
* 时间复杂度：$O(n + m)$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 52` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

