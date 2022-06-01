### 题目描述

这是 LeetCode 上的 **[剑指 Offer II 114. 外星文字典](https://leetcode.cn/problems/Jf1JuT/solution/by-ac_oier-4xmv/)** ，难度为 **困难**。

Tag : 「图论」、「拓扑排序」、「建图」、「图论 BFS」



现有一种使用英语字母的外星文语言，这门语言的字母顺序与英语顺序不同。

给定一个字符串列表 `words` ，作为这门语言的词典，`words` 中的字符串已经 按这门新语言的字母顺序进行了排序 。

请你根据该词典还原出此语言中已知的字母顺序，并 按字母递增顺序 排列。若不存在合法字母顺序，返回 `""` 。若存在多种可能的合法字母顺序，返回其中 任意一种 顺序即可。

字符串 `s` 字典顺序小于 字符串 `t` 有两种情况：

在第一个不同字母处，如果 `s` 中的字母在这门外星语言的字母顺序中位于 `t` 中字母之前，那么 `s` 的字典顺序小于 `t` 。
如果前面 `min(s.length, t.length)` 字母都相同，那么 `s.length < t.length` 时，`s` 的字典顺序也小于 `t` 。

示例 1：
```
输入：words = ["wrt","wrf","er","ett","rftt"]

输出："wertf"
```
示例 2：
```
输入：words = ["z","x"]

输出："zx"
```
示例 3：
```
输入：words = ["z","x","z"]

输出：""

解释：不存在合法字母顺序，因此返回 "" 。
```

提示：
* $1 <= words.length <= 100$
* $1 <= words[i].length <= 100$
* `words[i]` 仅由小写英文字母组成

---

### 建图 + 拓扑排序

为了方便，我们称 `words` 为 `ws`，同时将两个字符串 `a` 和 `b` 之间的字典序关系简称为「关系」。

由于数组长度和每个 $ws[i]$ 的最大长度均为 $100$，我们可以实现复杂度为 $O(n^3)$ 的算法。

首先容易想到，我们从前往后处理每个 $ws[i]$，利用 `ws` 数组本身已按字典序排序，然后通过 $ws[i]$ 与 $ws[j]$ 的关系（其中 $j$ 的范围为 $[0, i - 1]$），来构建字符之间的关系。

具体的，当我们明确字符 $c1$ 比 $c2$ 字典序要小，可以建立从 $c1$ 到 $c2$ 的有向边，同时统计增加 $c1$ 的出度，增加 $c2$ 的入度。

当建图完成后，我们从所有入度为 $0$ 的点开始（含义为没有比这些字符字典序更小的字符），跑一遍拓扑排序：在 `BFS` 过程中，不断的删点（出队的点可以看做从图中移除）和更新删除点的出边点的入度，若有新的入度为 $0$ 的点，则将其进行入队操作。

> **不了解拓扑排序的同学可以看前置 🧀 : [图论拓扑排序入门](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247489706&idx=1&sn=771cd807f39d1ca545640c0ef7e5baec)**

若最终出队节点数量与总数量 $cnt$ 相等，说明这是一张拓扑图（无环，字符之间不存在字典序冲突），出队的顺序即是字典序，否则存在冲突，返回空串。

代码：
```Java
class Solution {
    int N = 26, M = N * N, idx = 0, cnt = 0;
    int[] he = new int[N], e = new int[M], ne = new int[M];
    int[] in = new int[N], out = new int[N];
    boolean[] vis = new boolean[N];
    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = he[a];
        he[a] = idx++;
        out[a]++; in[b]++;
    }
    public String alienOrder(String[] ws) {
        int n = ws.length;
        Arrays.fill(he, -1);
        for (int i = 0; i < n; i++) {
            for (char c : ws[i].toCharArray()) {
                if (!vis[c - 'a'] && ++cnt >= 0) vis[c - 'a'] = true;
            }
            for (int j = 0; j < i; j++) {
                if (!build(ws[j], ws[i])) return "";
            }
        }
        Deque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 26; i++) {
            if (vis[i] && in[i] == 0) d.addLast(i);
        }
        StringBuilder sb = new StringBuilder();
        while (!d.isEmpty()) {
            int u = d.pollFirst();
            sb.append((char)(u + 'a'));
            for (int i = he[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (--in[j] == 0) d.addLast(j);
            }
        }
        return sb.length() == cnt ? sb.toString() : "";
    }
    boolean build(String a, String b) {
        int n = a.length(), m = b.length(), len = Math.min(n, m);
        for (int i = 0; i < len; i++) {
            int c1 = a.charAt(i) - 'a', c2 = b.charAt(i) - 'a';
            if (c1 != c2) {
                add(c1, c2);
                return true;
            }
        }
        return n <= m;
    }
}
```
* 时间复杂度：令 $n$ 为数组 `ws` 的长度，统计字符数的复杂度为 $O(\sum_{i}^{n - 1} len(ws[i]))$，建图复杂度为 $O(n^3)$；跑拓扑序构建答案复杂度 $O(n^2)$。整体复杂度为 $O(n^3)$
* 空间复杂度：$O(C^2)$，其中 $C = 26$ 为字符集大小

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer II 114` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

