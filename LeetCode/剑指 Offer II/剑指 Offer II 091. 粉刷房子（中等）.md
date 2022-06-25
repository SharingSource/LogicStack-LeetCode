### 题目描述

这是 LeetCode 上的 **[剑指 Offer II 091. 粉刷房子](https://leetcode.cn/problems/JEj789/solution/by-ac_oier-6v2v/)** ，难度为 **中等**。

Tag : 「状态机 DP」、「动态规划」



假如有一排房子，共 `n` 个，每个房子可以被粉刷成红色、蓝色或者绿色这三种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。

当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。每个房子粉刷成不同颜色的花费是以一个 `n x 3` 的正整数矩阵 `costs` 来表示的。

例如，`costs[0][0]` 表示第 $0$ 号房子粉刷成红色的成本花费；`costs[1][2]` 表示第 $1$ 号房子粉刷成绿色的花费，以此类推。

请计算出粉刷完所有房子最少的花费成本。

示例 1：
```
输入: costs = [[17,2,17],[16,16,5],[14,3,19]]

输出: 10

解释: 将 0 号房子粉刷成蓝色，1 号房子粉刷成绿色，2 号房子粉刷成蓝色。
最少花费: 2 + 5 + 3 = 10。
```
示例 2：
```
输入: costs = [[7,6,2]]

输出: 2
```

提示:
* $costs.length == n$
* $costs[i].length == 3$
* $1 <= n <= 100$
* $1 <= costs[i][j] <= 20$

---

### 状态机 DP

为了方便，我们记 `costs` 为 `cs`。

根据题意，当我们从前往后决策每间房子的颜色时，当前房子所能刷的颜色，取决于上一间房子的颜色。

我们可以定义 $f[i][j]$ 为考虑下标不超过 $i$ 的房子，且最后一间房子颜色为 $j$ 时的最小成本。

起始我们有 $f[0][i] = cs[0][i]$，代表只有第一间房子时，对应成本为第一间房子的上色成本。

然后不失一般性考虑，$f[i][j]$ 该如何计算：$f[i][j]$ 为所有 $f[i - 1][prev]$（其中 $prev \neq j$）中的最小值加上 $cs[i][j]$。

本质上这是一道「状态机 DP」问题：某些状态只能由规则限定的状态所转移，通常我们可以从 $f[i][j]$ 能够更新哪些目标状态（后继状态）进行转移，也能够从 $f[i][j]$ 依赖哪些前置状态（前驱状态）来转移。

一些细节：考虑到我们 $f[i][X]$ 的计算只依赖于 $f[i - 1][X]$，因此我们可以使用三个变量来代替我们的动规数组。

代码：
```Java
class Solution {
    public int minCost(int[][] cs) {
        int n = cs.length;
        int a = cs[0][0], b = cs[0][1], c = cs[0][2];
        for (int i = 1; i < n; i++) {
            int d = Math.min(b, c) + cs[i][0];
            int e = Math.min(a, c) + cs[i][1];
            int f = Math.min(a, b) + cs[i][2];
            a = d; b = e; c = f;
        }
        return Math.min(a, Math.min(b, c));
    }
}
```

-

```Java
class Solution {
    public int minCost(int[][] cs) {
        int n = cs.length;
        int a = cs[0][0], b = cs[0][1], c = cs[0][2];
        for (int i = 0; i < n - 1; i++) {
            int d = Math.min(b, c) + cs[i + 1][0];
            int e = Math.min(a, c) + cs[i + 1][1];
            int f = Math.min(a, b) + cs[i + 1][2];
            a = d; b = e; c = f;
        }
        return Math.min(a, Math.min(b, c));
    }
}
```
* 时间复杂度：$O(n \times C)$，其中 $C = 3$ 为颜色数量
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer II 091` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

