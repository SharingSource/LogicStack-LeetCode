### 题目描述

这是 LeetCode 上的 **[剑指 Offer II 115. 重建序列](https://leetcode.cn/problems/ur2n8P/solution/by-ac_oier-oqxs/)** ，难度为 **中等**。

Tag : 「图论」、「拓扑排序」、「建图」、「图论 BFS」



给定一个长度为 `n` 的整数数组 `nums` ，其中 `nums` 是范围为 $[1，n]$ 的整数的排列。还提供了一个 `2D` 整数数组 `sequences`，其中 `sequences[i]` 是 `nums` 的子序列。

检查 `nums` 是否是唯一的最短 超序列 。最短 超序列 是 长度最短 的序列，并且所有序列 `sequences[i]` 都是它的子序列。对于给定的数组 `sequences`，可能存在多个有效的 超序列 。

* 例如，对于 `sequences = [[1,2],[1,3]]` ，有两个最短的 超序列，`[1,2,3]` 和 `[1,3,2]` 。
* 而对于 `sequences = [[1,2],[1,3],[1,2,3]]`，唯一可能的最短 超序列 是 `[1,2,3]` 。`[1,2,3,4]` 是可能的超序列，但不是最短的。

如果 `nums` 是序列的唯一最短 超序列 ，则返回 `true` ，否则返回 `false` 。

子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺序的序列。

示例 1：
```
输入：nums = [1,2,3], sequences = [[1,2],[1,3]]

输出：false

解释：有两种可能的超序列：[1,2,3]和[1,3,2]。
序列 [1,2] 是[1,2,3]和[1,3,2]的子序列。
序列 [1,3] 是[1,2,3]和[1,3,2]的子序列。
因为 nums 不是唯一最短的超序列，所以返回false。
```
示例 2：
```
输入：nums = [1,2,3], sequences = [[1,2]]

输出：false

解释：最短可能的超序列为 [1,2]。
序列 [1,2] 是它的子序列：[1,2]。
因为 nums 不是最短的超序列，所以返回false。
```
示例 3：
```
输入：nums = [1,2,3], sequences = [[1,2],[1,3],[2,3]]

输出：true

解释：最短可能的超序列为[1,2,3]。
序列 [1,2] 是它的一个子序列：[1,2,3]。
序列 [1,3] 是它的一个子序列：[1,2,3]。
序列 [2,3] 是它的一个子序列：[1,2,3]。
因为 nums 是唯一最短的超序列，所以返回true。
```

提示：
* $n == nums.length$
* $1 <= n <= 104$
* `nums` 是 $[1, n]$ 范围内所有整数的排列
* $1 <= sequences.length <= 10^4$
* $1 <= sequences[i].length <= 104$
* $1 <= sum(sequences[i].length) <= 10^5$
* $1 <= sequences[i][j] <= n$
* `sequences` 的所有数组都是 唯一 的
* `sequences[i]` 是 `nums` 的一个子序列

---

### 拓扑排序 + 构造

为了方便，我们令 `sequences` 为 `ss`。

根据题意，如果我们能够利用所有的 $ss[i]$ 构造出一个唯一的序列，且该序列与 `nums` 相同，则返回 `True`，否则返回 `False`。

将每个 $ss[i]$ 看做对 $ss[i]$ 所包含点的前后关系约束，我们可以将问题转换为拓扑排序问题。

利用所有 $ss[i]$ 构造新图：对于 $ss[i] = [A_1, A_2, ..., A_k]$，我们将其转换为点 $A_1$ -> $A_2$ -> ... -> $A_k$ 的有向图，同时统计每个点的入度情况。

然后在新图上跑一遍拓扑排序，构造对应的拓扑序列，与 `nums` 进行对比。

实现上，由于拓扑排序过程中，出点的顺序即为拓扑序，因此我们并不需要完整保存整个拓扑序，只需使用一个变量 `loc` 来记录当前拓扑序的下标，将出点 $t$ 与 $nums[loc]$ 做比较即可。

在拓扑序过程中若有 $t$ 不等于 $nums[loc]$（构造出来的方案与 `nums` 不同） 或某次拓展过程中发现队列元素不止 $1$ 个（此时可能的原因有 :「起始入度为 $0$ 的点不止一个或存在某些点根本不在 $ss$ 中」或「单次拓展新产生的入度为 $0$ 的点不止一个，即拓扑序不唯一」），则直接返回 `False`，

Java 代码：
```Java 
class Solution {
    int N = 10010, M = N, idx;
    int[] he = new int[N], e = new int[M], ne = new int[M], in = new int[N];
    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = he[a];
        he[a] = idx++;
        in[b]++;
    }
    public boolean sequenceReconstruction(int[] nums, int[][] ss) {
        int n = nums.length;
        Arrays.fill(he, -1);
        for (int[] s : ss) {
            for (int i = 1; i < s.length; i++) add(s[i - 1], s[i]);
        }
        Deque<Integer> d = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) d.addLast(i);
        }
        int loc = 0;
        while (!d.isEmpty()) {
            if (d.size() != 1) return false;
            int t = d.pollFirst();
            if (nums[loc++] != t) return false;
            for (int i = he[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (--in[j] == 0) d.addLast(j);
            }
        }
        return true;
    }
}
```
TypeScript 代码：
```TypeScript
const N = 10010, M = N
const he: number[] = new Array<number>(N).fill(-1), e = new Array<number>(N).fill(0), ne = new Array<number>(N).fill(0), ind = new Array<number>(N).fill(0);
let idx = 0
function add(a: number, b: number): void {
    e[idx] = b
    ne[idx] = he[a]
    he[a] = idx++
    ind[b]++
}
function sequenceReconstruction(nums: number[], ss: number[][]): boolean {
    he.fill(-1); ind.fill(0)
    idx = 0
    const n = nums.length
    for (const s of ss) {
        for (let i = 1; i < s.length; i++) add(s[i - 1], s[i])
    }
    const stk: number[] = new Array<number>()
    let head = 0, tail = 0
    for (let i = 1; i <= n; i++) {
        if (ind[i] == 0) stk[tail++] = i
    }
    let loc = 0
    while (head < tail) {
        if (tail - head > 1) return false
        const t = stk[head++]
        if (nums[loc++] != t) return false
        for (let i = he[t]; i != -1; i = ne[i]) {
            const j = e[i]
            if (--ind[j] == 0) stk[tail++] = j
        }
    }
    return true
};
```
* 时间复杂度：建图复杂度为 $O(\sum_{i = 0}^{n - 1}ss[i].length)$；跑拓扑排序的复杂度为 $O(n + \sum_{i = 0}^{n - 1}ss[i].length)$。整体复杂度为 $O(n + \sum_{i = 0}^{n - 1}ss[i].length)$
* 空间复杂度： $O(n + \sum_{i = 0}^{n - 1}ss[i].length)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer II 115` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

