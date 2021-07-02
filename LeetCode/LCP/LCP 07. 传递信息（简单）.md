### 题目描述

这是 LeetCode 上的 **[LCP 07. 传递信息](https://leetcode-cn.com/problems/chuan-di-xin-xi/solution/gong-shui-san-xie-tu-lun-sou-suo-yu-dong-cyxo/)** ，难度为 **简单**。

Tag : 「图论搜索」、「图论 BFS」、「图论 DFS」、「线性 DP」



小朋友 A 在和 ta 的小伙伴们玩传信息游戏，游戏规则如下：

1. 有 n 名玩家，所有玩家编号分别为 0 ～ n-1，其中小朋友 A 的编号为 0
2. 每个玩家都有固定的若干个可传信息的其他玩家（也可能没有）。传信息的关系是单向的（比如 A 可以向 B 传信息，但 B 不能向 A 传信息）。
3. 每轮信息必须需要传递给另一个人，且信息可重复经过同一个人

给定总玩家数 n，以及按 [玩家编号,对应可传递玩家编号] 关系组成的二维数组 relation。返回信息从小 A (编号 0 ) 经过 k 轮传递到编号为 n-1 的小伙伴处的方案数；若不能到达，返回 0。

示例 1：
```
输入：n = 5, relation = [[0,2],[2,1],[3,4],[2,3],[1,4],[2,0],[0,4]], k = 3

输出：3

解释：信息从小 A 编号 0 处开始，经 3 轮传递，到达编号 4。

共有 3 种方案，分别是 0->2->0->4， 0->2->1->4， 0->2->3->4。
```
示例 2：
```
输入：n = 3, relation = [[0,2],[2,1]], k = 2

输出：0

解释：信息不能从小 A 处经过 2 轮传递到编号 2
```

限制：
* 2 <= n <= 10
* 1 <= k <= 5
* 1 <= relation.length <= 90, 且 relation[i].length == 2
* 0 <= relation[i][0],relation[i][1] < n 且 relation[i][0] != relation[i][1]

---

### 基本分析

`n` 和 `k` 的数据范围都很小，并且根据题目对 `relation` 的定义可以知道这是一个边权相等的图。

对于边权相等的图，统计有限步数的到达某个节点的方案数，最常见的方式是使用 `BFS` 或 `DFS`。

---

### BFS

一个朴素的做法是使用 `BFS` 进行求解。

起始时，将起点入队，按照常规的 `BFS` 方式进行拓展，直到拓展完第 $k$ 层。

然后统计队列中编号为 `n-1` 的节点的出现次数。

一些细节：为了方便找到某个点 $i$ 所能到达的节点，我们需要先预处理出所有的边。数据量较少，直接使用 `Map` 套 `Set` 即可。

代码：
```Java []
class Solution {
    public int numWays(int n, int[][] rs, int k) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] r : rs) {
            int a = r[0], b = r[1];
            Set<Integer> s = map.getOrDefault(a, new HashSet<>());
            s.add(b);
            map.put(a, s);
        }
        Deque<Integer> d = new ArrayDeque<>();
        d.addLast(0);
        while (!d.isEmpty() && k-- > 0) {
            int size = d.size();
            while (size-- > 0) {
                int poll = d.pollFirst();
                Set<Integer> es = map.get(poll);
                if (es == null) continue;
                for (int next : es) {
                    d.addLast(next);
                }
            }
        }
        int ans = 0;
        while (!d.isEmpty()) {
            if (d.pollFirst() == n - 1) ans++;
        }
        return ans;
    }
}
```
* 时间复杂度：最多搜索 $k$ 层，最坏情况下每层的每个节点都能拓展出 $n - 1$ 个节点，复杂度为 $O(n^k)$
* 空间复杂度：最坏情况下，所有节点都相互连通。复杂度为 $O(n^k + n^2)$

---

### DFS

同理，我们可以使用 `DFS` 进行求解。

在 `DFS` 过程中限制深度最多为 $k$，然后检查所达节点为 `n-1` 的次数即可。

代码：
```Java []
class Solution {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    int n, k, ans;
    public int numWays(int _n, int[][] rs, int _k) {
        n = _n; k = _k;
        for (int[] r : rs) {
            int a = r[0], b = r[1];
            Set<Integer> s = map.getOrDefault(a, new HashSet<>());
            s.add(b);
            map.put(a, s);
        }
        dfs(0, 0);
        return ans;
    }
    void dfs(int u, int sum) {
        if (sum == k) {
            if (u == n - 1) ans++;
            return;
        }
        Set<Integer> es = map.get(u);
        if (es == null) return;
        for (int next : es) {
            dfs(next, sum + 1);
        }
    }
}
```
* 时间复杂度：最多搜索 $k$ 层，最坏情况下每层的每个节点都能拓展出 $n - 1$ 个节点，复杂度为 $O(n^k)$
* 空间复杂度：最坏情况下，所有节点都相互连通。忽略递归带来的额外空间消耗，复杂度为 $O(n^2)$

---

### 动态规划

假设当前我们已经走了 $i$ 步，所在位置为 $j$，那么剩余的 $k - i$ 步，能否到达位置 $n - 1$，仅取决于「剩余步数 $k - i$」和「边权关系 `relation`」，而与我是如何到达位置 $i$ 无关。

而对于方案数而言，如果已经走了 $i$ 步，所在位置为 $j$，到达位置 $n - 1$ 的方案数仅取决于「剩余步数 $i - k$」、「边权关系 `relation`」和「花费 $i$ 步到达位置 $j$ 的方案数」。

**以上分析，归纳到边界「已经走了 $0$ 步，所在位置为 $0$」同样成立。**

**这是一个「无后效性」的问题，可以使用动态规划进行求解。**

定义 $f[i][j]$ 为当前已经走了 $i$ 步，所在位置为 $j$ 的方案数。

那么 $f[k][n - 1]$ 为最终答案，$f[0][0] = 1$ 为显而易见的初始化条件。

不失一般性的考虑，$f[i][j]$ 该如何转移，$f[i][j]$ 应该为所有能够到达位置 $j$ 的点 $p$ 的 $f[i - 1][p]$ 的总和：

$$
f[i][j] = \sum_{i = 0}^{relation.length - 1}f[i - 1][p], relation[i][0]=p,relation[i][1]=j
$$

代码：
```Java []
class Solution {
    public int numWays(int n, int[][] rs, int k) {
        int[][] f = new int[k + 1][n];
        f[0][0] = 1;
        for (int i = 1; i <= k; i++) {
            for (int[] r : rs) {
                int a = r[0], b = r[1];
                f[i][b] += f[i - 1][a];
            }
        }
        return f[k][n - 1];
    }
}
```
* 时间复杂度：$O(relation.length * k)$
* 空间复杂度：$O(n * k)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `LCP 07` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先将所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

