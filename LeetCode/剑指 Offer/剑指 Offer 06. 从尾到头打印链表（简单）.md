### 题目描述

这是 LeetCode 上的 **[剑指 Offer 06. 从尾到头打印链表](https://leetcode.cn/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/solution/by-ac_oier-3qsk/)** ，难度为 **简单**。

Tag : 「链表」、「迭代」、「递归」、「双指针」



输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

示例 1：
```
输入：head = [1,3,2]

输出：[2,3,1]
```

限制：
* $0 <= 链表长度 <= 10000$

---

### 迭代

使用「迭代」方式求解是极其容易的。

在遍历链表 `head` 时，使用变长数组将节点值进行转存，随后再利用变长数组来「从后往前」构造定长数组并进行返回即可（其中 `TS`  代码无须额外构造定长数组，直接在原有数组基础上使用「双指针」翻转即可）。

Java 代码：
```Java 
class Solution {
    public int[] reversePrint(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int n = list.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) ans[i] = list.get(n - 1 - i);
        return ans;
    }
}
```
TypeScript 代码：
```TypeScript
function reversePrint(head: ListNode | null): number[] {
    const ans = new Array<number>()
    while (head != null) {
        ans.push(head.val)
        head = head.next
    }
    const n = ans.length
    for (let i = 0, j = n - 1; i < j; i++, j--) {
        const c = ans[i]
        ans[i] = ans[j]
        ans[j] = c
    }
    return ans
};
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 递归

使用「递归」求解只需要进行常规的 `DFS`  即可，每次先将当前节点的 `next` 指针进行递归处理，然后再将当前节点值加入数组，即可实现「从后往前」的顺序添加。

Java 代码：
```Java
class Solution {
    public int[] reversePrint(ListNode head) {
        List<Integer> list = new ArrayList<>();
        dfs(head, list);
        int n = list.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) ans[i] = list.get(i);
        return ans;
    }
    void dfs(ListNode head, List<Integer> list) {
        if (head == null) return ;
        dfs(head.next, list);
        list.add(head.val);
    }
}
```
TypeScript 代码：
```TypeScript
function reversePrint(head: ListNode | null): number[] {
    const ans: number[] = new Array<number>()
    dfs(head, ans)
    return ans
};
function dfs(head: ListNode | null, list: number[]) {
    if (head == null) return 
    dfs(head.next, list)
    list.push(head.val)
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer 06` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

