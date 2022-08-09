### 题目描述

这是 LeetCode 上的 **[剑指 Offer 44. 数字序列中某一位的数字](https://leetcode.cn/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/solution/by-ac_oier-wgr8/)** ，难度为 **中等**。

Tag : 「数学」、「模拟」



数字以 `0123456789101112131415…` 的格式序列化到一个字符序列中。在这个序列中，第 $5$ 位（从下标 $0$ 开始计数）是 $5$，第 $13$ 位是 $1$，第 $19$ 位是 $4$，等等。

请写一个函数，求任意第 $n$ 位对应的数字。

示例 1：
```
输入：n = 3

输出：3
```
示例 2：
```
输入：n = 11

输出：0
```

限制：
* $0 <= n < 2^31$

---

### 模拟

我们知道，对于长度为 $len$ 的数字的范围为 $[10^{len - 1}, 10^{len} - 1]$（共 $9 \times 10^{len - 1}$ 个），总长度为：

$$
L = len \times 9 \times 10^{len - 1}
$$

因此我们可以先对 $n$ 进行不断试减（更新 $n$），确定下来目标数字 $x$ 的长度为多少，假设为 $len$。

然后直接计算出长度 $len$ 的最小值为 $s = 10^{len - 1}$，由于范围内的数长度都是 $len$，因此我们可以直接定位到目标数字 $x$ 为何值。

根据目标值 $x$ 必然满足关系式：

$$
(x - s + 1) \times len \geq n
$$

变形可得：

$$
x \geq \left \lfloor \frac{n}{len} \right \rfloor - 1 + s
$$

对 $n$ 进行最后一次的试减（更新 $n$），若恰好有 $n = 0$，说明答案为 $x$ 的最后一位，可由 `x % 10` 取得；若大于 $0$，说明答案是 $x + 1$ 的第 $n$ 位（十进制表示，从左往右数），可由 `(x + 1) / (int) (Math.pow(10, len - n)) % 10` 取得。

代码：
```Java
class Solution {
    public int findNthDigit(int n) {
        int len = 1;
        while (len * 9 * Math.pow(10, len - 1) < n) {
            n -= len * 9 * Math.pow(10, len - 1);
            len++;
        }
        long s = (long) Math.pow(10, len - 1);
        long x = n / len - 1 + s;
        n -= (x - s + 1) * len;
        return n == 0 ? (int) (x % 10) : (int) ((x + 1) / Math.pow(10, len - n) % 10);
    }
}
```
* 时间复杂度：$O(\log{n})$
* 空间复杂度：$O(1)$

---

### 补充

上述讲解可能对于新手并不友好，尤其是对一些上来只做剑指 Offer 的面试向同学，下面使用更为通俗的方式进行讲解。

对于长度 `len` 的所有数，其最小值为 $10^{len - 1}$，最大值为 $10^{len} - 1$，每个数所能贡献的数的个数为 $len$ 个，因此对于长度为 `len` 的所有数，其所能贡献的数的个数为 $(10^{len} - 10^{len - 1}) \times len$。我们专门开一个函数 `getCnt` 来计算长度为 `len` 的数的个数有多少。

然后我们从 `len = 1` 开始，不断对 `n` 进行试减（`len` 递增），直到 `getCnt(len) <= n` 不再满足。

假设此时的递增到的长度为 `len`，可知长度为 `len` 的最小值 $start = 10^{len - 1}$，我们可以通过计算偏移量 $\left \lfloor \frac{n}{len} \right \rfloor$ 并将其累加到 $start$ 中去，同时对 `n` 进行 $\left \lfloor \frac{n}{len} \right \rfloor \times len$ 的消减。

然后根据 `n` 是否为 $0$ 进行分情况讨论：

* 当 `n` 为 $0$，说明答案落在 $start - 1$ 的最后一位，即 `(start - 1) % 10` ；
* 当 `n` 不为 $0$，说明答案为 `start` 数值中从左往右数的第 $n$ 位，可通过解法一的方式取得，也可以将其转字符串再通过下标获取。

代码：
```Java
class Solution {
    long getCnt(int len) {
        return (long)(Math.pow(10, len) - Math.pow(10, len - 1)) * len;
    }
    public int findNthDigit(int n) {
        int len = 1;
        while (getCnt(len) <= n) {
            n -= getCnt(len);
            len++;
        }
        long start = (long)Math.pow(10, len - 1);
        start += n / len;
        n -= n / len * len;
        if (n == 0) return (int)(start - 1) % 10;
        else return String.valueOf(start).toCharArray()[n - 1] - '0';
    }
}
```
* 时间复杂度：$O(\log{n})$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 44` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

