### 题目描述

这是 LeetCode 上的 **[剑指 Offer 32 - I. 从上到下打印二叉树](https://leetcode.cn/problems/cong-shang-dao-xia-da-yin-er-cha-shu-lcof/solution/by-ac_oier-a9n5/)** ，难度为 **中等**。

Tag : 「二叉树」、「DFS」、「BFS」、「递归」、「迭代」



从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。

例如:

给定二叉树: `[3,9,20,null,null,15,7]`,
```
    3
   / \
  9  20
    /  \
   15   7
```
返回：
```
[3,9,20,15,7]
```

提示：
* `节点总数 <= 1000`

---

### 迭代 - BFS

使用「迭代」进行求解是容易的，只需使用常规的 `BFS` 方法进行层序遍历即可。

Java 代码：
```Java
class Solution {
    public int[] levelOrder(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> d = new ArrayDeque<>();
        if (root != null) d.addLast(root);
        while (!d.isEmpty()) {
            TreeNode t = d.pollFirst();
            list.add(t.val);
            if (t.left != null) d.addLast(t.left);
            if (t.right != null) d.addLast(t.right);
        }
        int n = list.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) ans[i] = list.get(i);
        return ans;
    }
}
```
TypeScript 代码：
```TypeScript
function levelOrder(root: TreeNode | null): number[] {
    let he = 0, ta = 0
    const ans: number[] = new Array<number>()
    const d: TreeNode[] = new Array<TreeNode>()
    if (root != null) d[ta++] = root
    while (he < ta) {
        const t = d[he++]
        ans.push(t.val)
        if (t.left != null) d[ta++] = t.left
        if (t.right != null) d[ta++] = t.right
    }
    return ans
};
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 递归 - DFS

使用「递归」来进行「层序遍历」虽然不太符合直观印象，但也是可以的。

此时我们需要借助「哈希表」来存储起来每一层的节点情况。

首先我们按照「中序遍历」的方式进行 `DFS`，同时在 `DFS` 过程中传递节点所在的深度（`root` 节点默认在深度最小的第 $0$ 层），每次处理当前节点时，通过哈希表获取所在层的数组，并将当前节点值追加到数组尾部，同时维护一个最大深度 `max`，在 `DFS` 完成后，再使用深度范围 $[0, max]$ 从哈希表中进行构造答案。

Java 代码：
```Java
class Solution {
    Map<Integer, List<Integer>> map = new HashMap<>();
    int max = -1, cnt = 0;
    public int[] levelOrder(TreeNode root) {
        dfs(root, 0);
        int[] ans = new int[cnt];
        for (int i = 0, idx = 0; i <= max; i++) {
            for (int x : map.get(i)) ans[idx++] = x;
        }
        return ans;
    }
    void dfs(TreeNode root, int depth) {
        if (root == null) return ;
        max = Math.max(max, depth);
        cnt++;
        dfs(root.left, depth + 1);
        List<Integer> list = map.getOrDefault(depth, new ArrayList<Integer>());
        list.add(root.val);
        map.put(depth, list);
        dfs(root.right, depth + 1);
    }
}
```
TypeScript 代码：
```TypeScript
const map:Map<number, Array<number>> = new Map<number, Array<number>>()
let max = -1, cnt = 0
function levelOrder(root: TreeNode | null): number[] {
    map.clear()
    max = -1; cnt = 0;
    dfs(root, 0)
    const ans: Array<number> = new Array<number>()
    for (let i = 0; i <= max; i++) {
        for (const x of map.get(i)) ans.push(x)
    }
    return ans
};
function dfs(root: TreeNode | null, depth: number): void {
    if (root == null) return 
    max = Math.max(max, depth)
    cnt++
    dfs(root.left, depth + 1)
    if (!map.has(depth)) map.set(depth, new Array<number>())
    map.get(depth).push(root.val)
    dfs(root.right, depth + 1)
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 32 - I` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

