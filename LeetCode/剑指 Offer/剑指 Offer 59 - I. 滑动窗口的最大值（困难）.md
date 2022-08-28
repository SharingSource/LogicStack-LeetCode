### 题目描述

这是 LeetCode 上的 **[剑指 Offer 59 - I. 滑动窗口的最大值](https://leetcode.cn/problems/hua-dong-chuang-kou-de-zui-da-zhi-lcof/solution/by-ac_oier-sjym/)** ，难度为 **困难**。

Tag : 「优先队列（堆）」、「线段树」、「分块」、「单调队列」、「RMQ」



给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值 。

示例 1：
```
输入：nums = [1,3,-1,-3,5,3,6,7], k = 3

输出：[3,3,5,5,6,7]

解释：
滑动窗口的位置                最大值
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```
示例 2：
```
输入：nums = [1], k = 1

输出：[1]
```

提示：
* $1 <= nums.length <= 10^5$
* $-10^4 <= nums[i] <= 10^4$
* $1 <= k <= nums.length$

---

### 优先队列（堆）

根据题意，容易想到优先队列（大根堆），同时为了确保滑动窗口的大小合法性，我们以二元组 $(idx, nums[idx])$ 的形式进行入队。

当下标达到首个滑动窗口的右端点后，每次尝试从优先队列（大根堆）中取出最大值（若堆顶元素的下标小于当前滑动窗口左端点时，则丢弃该元素）。

代码：
```Java 
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a,b)->b[1]-a[1]);
        int n = nums.length, m = n - k + 1, idx = 0;
        int[] ans = new int[m];
        for (int i = 0; i < n; i++) {
            q.add(new int[]{i, nums[i]});
            if (i >= k - 1) {
                while (q.peek()[0] <= i - k) q.poll();
                ans[idx++] = q.peek()[1];
            }
        }
        return ans;
    }
}
```
* 时间复杂度：$O(n\log{n})$
* 空间复杂度：$O(n)$

---

### 线段树 

容易将问题转换为「区间求和」问题：使用原始的 `nums` 构建线段树等价于在位置 $i$ 插入 $nums[i]$，即单点操作，而查询每个滑动窗口最大值，则对应的区间查询。

由于只涉及单点修改，无须实现懒标记 `pushdown` 操作，再结合 $n$ 的数据范围为 $10^5$，无须进行动态开点。

直接写 `build` 四倍空间的线段树数组实现即可。

代码：
```Java 
class Solution {
    class Node {
        int l, r, val;
        Node (int _l, int _r) {
            l = _l; r = _r; val = Integer.MIN_VALUE;
        }
    }
    Node[] tr = new Node[100010 * 4];
    void build(int u, int l, int r) {
        tr[u] = new Node(l, r);
        if (l == r) return ;
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }
    void update(int u, int x, int v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].val = Math.max(tr[u].val, v);
            return ;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (x <= mid) update(u << 1, x, v);
        else update(u << 1 | 1, x, v);
        pushup(u);
    }
    int query(int u, int l, int r) {
        if (l <= tr[u].l && tr[u].r <= r) return tr[u].val;
        int mid = tr[u].l + tr[u].r >> 1, ans = Integer.MIN_VALUE;
        if (l <= mid) ans = query(u << 1, l, r);
        if (r > mid) ans = Math.max(ans, query(u << 1 | 1, l, r));
        return ans;
    }
    void pushup(int u) {
        tr[u].val = Math.max(tr[u << 1].val, tr[u << 1 | 1].val);
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length, m = n - k + 1;
        int[] ans = new int[m];
        build(1, 1, n);
        for (int i = 0; i < n; i++) update(1, i + 1, nums[i]);
        for (int i = k; i <= n; i++) ans[i - k] = query(1, i - k + 1, i);
        return ans;
    }
}
```
* 时间复杂度：建立线段树复杂度为 $O(n\log{n})$；构建答案复杂度为 $O(n\log{n})$。整体复杂度为 $O(n\log{n})$
* 空间复杂度：$O(n)$

---

### 分块

另外一个做法是分块，又名「优雅的暴力」，也是莫队算法的基础。

具体的，除了给定的 `nums` 以外，我们构建一个分块数组 `region`，其中 `region[idx] = x` 含义为块编号为 `idx` 的最大值为 `x`，其中一个块对应一个原始区间 $[l, r]$。

如何定义块大小是实现分块算法的关键。

对于本题，本质是求若干个大小为 $k$ 的区间最大值。

我们可以设定块大小为 $\sqrt{k}$，这样所需创建的分块数组大小为 $\frac{n}{\sqrt{k}}$。分块数组的更新操作为 $O(1)$，而查询则为 $\sqrt{k}$。

容易证明查询操作的复杂度：对于每个长度为 $k$ 的 $[l, r]$ 查询操作而言，最多遍历两个（左右端点对应的块）的块内元素，复杂度为 $O(\sqrt{k})$，同时最多遍历 $\sqrt{k}$ 个块，复杂度同为 $O(\sqrt{k})$。

因此最多两步复杂度为 $O(\sqrt{k})$ 的块内操作，最多 $\sqrt{k}$ 步复杂度为 $O(1)$ 的块间操作，整体复杂度为 $O(\sqrt{k})$。

因此使用分块算法总的计算量为 $n\times\sqrt{k} = 10^6$，可以过。

分块算法的几个操作函数：

* `int getIdx(int x)` ：计算原始下标对应的块编号；
* `void add(int x, int v)` ：计算原始下标 `x` 对应的下标 `idx`，并将 `region[idx]` 和 `v` 取 `max` 来更新 `region[idx]`；
* `int query(int l, int r)` ：查询 $[l, r]$ 中的最大值，如果 $l$ 和 $r$ 所在块相同，直接遍历 $[l, r]$ 进行取值；若 $l$ 和 $r$ 不同块，则处理 $l$ 和 $r$ 对应的块内元素后，对块编号在 $(getIdx(l), getIdx(r))$ 之间的块进行遍历。

代码：
```Java 
class Solution {
    int n, m, len;
    int[] nums, region;
    int getIdx(int x) {
        return x / len;
    }
    void add(int x, int v) {
        region[getIdx(x)] = Math.max(region[getIdx(x)], v);
    }
    int query(int l, int r) {
        int ans = Integer.MIN_VALUE;
        if (getIdx(l) == getIdx(r)) {
            for (int i = l; i <= r; i++) ans = Math.max(ans, nums[i]);
        } else {
            int i = l, j = r;
            while (getIdx(i) == getIdx(l)) ans = Math.max(ans, nums[i++]);
            while (getIdx(j) == getIdx(r)) ans = Math.max(ans, nums[j--]);
            for (int k = getIdx(i); k <= getIdx(j); k++) ans = Math.max(ans, region[k]);
        }
        return ans;
    }
    public int[] maxSlidingWindow(int[] _nums, int k) {
        nums = _nums;
        n = nums.length; len = (int) Math.sqrt(k); m = n / len + 10;
        region = new int[m];
        Arrays.fill(region, Integer.MIN_VALUE);
        for (int i = 0; i < n; i++) add(i, nums[i]);
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) ans[i] = query(i, i + k - 1);
        return ans;
    }
}
```
* 时间复杂度：数组大小为 $n$，块大小为 $\sqrt{k}$，分块数组大小为 $\frac{n}{\sqrt{k}}$。预处理分块数组复杂度为 $O(n)$（即 `add` 操作复杂度为 $O(1)$ ）；构造答案复杂度为 $O(n\times\sqrt{k})$（即 `query` 操作复杂度为 $O(\sqrt{k})$，最多有 $n$ 次查询）
* 空间复杂度：$\frac{n}{\sqrt{k}}$

---

### 单调队列

关于 `RMQ` 的另外一个优秀做法通常是使用「单调队列/单调栈」。

当然，我们也能不依靠经验，而从问题的本身出发，逐步分析出该做法。

假设我们当前处理到某个长度为 $k$ 的窗口，此时窗口往后滑动一格，会导致后一个数（新窗口的右端点）添加进来，同时会导致前一个数（旧窗口的左端点）移出窗口。

随着窗口的不断平移，该过程会一直发生。**若同一时刻存在两个数 $nums[j]$ 和 $nums[i]$（$j < i$）所在一个窗口内，下标更大的数会被更晚移出窗口，此时如果有 $nums[j] <= nums[i]$ 的话，可以完全确定 $nums[j]$ 将不会成为后续任何一个窗口的最大值，此时可以将必然不会是答案的 $nums[j]$ 从候选中进行移除**。

不难发现，当我们将所有必然不可能作为答案的元素（即所有满足的小于等于 $nums[i]$ ）移除后，候选集合满足「单调递减」特性，即集合首位元素为当前窗口中的最大值（为了满足窗口长度为 $k$ 的要求，在从集合头部取答案时需要先将下标小于的等于的 $i - k$ 的元素移除）。

为方便从尾部添加元素，从头部获取答案，我们可使用「双端队列」存储所有候选元素。

代码：
```Java 
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> d = new ArrayDeque<>();
        int n = nums.length, m = n - k + 1;
        int[] ans = new int[m];
        for (int i = 0; i < n; i++) {
            while (!d.isEmpty() && nums[d.peekLast()] <= nums[i]) d.pollLast();
            d.addLast(i);
            if (i >= k - 1) {
                while (!d.isEmpty() && d.peekFirst() <= i - k) d.pollFirst();
                ans[i - k + 1] = nums[d.peekFirst()];
            }
        }
        return ans;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 59 - I` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

