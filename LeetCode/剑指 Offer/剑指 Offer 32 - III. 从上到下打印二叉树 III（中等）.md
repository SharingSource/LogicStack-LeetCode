### 题目描述

这是 LeetCode 上的 **[剑指 Offer 32 - III. 从上到下打印二叉树 III](https://leetcode.cn/problems/cong-shang-dao-xia-da-yin-er-cha-shu-iii-lcof/solution/by-ac_oier-98od/)** ，难度为 **中等**。

Tag : 「二叉树」、「DFS」、「BFS」、「递归」、「迭代」



请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。

例如:

给定二叉树: `[3,9,20,null,null,15,7]`,
```
    3
   / \
  9  20
    /  \
   15   7
```
返回其层次遍历结果：
```
[
  [3],
  [20,9],
  [15,7]
]
```

提示：
* `节点总数 <= 1000`

---

### 迭代 - BFS

这题相比于前两道二叉树打印题目，增加了打印方向的要求。

在 `BFS` 过程中，入队我们可以仍然采用「左子节点优先入队」进行，而在出队构造答案时，我们则要根据当前所在层数来做判别：对于所在层数为偶数（`root` 节点在第 $0$ 层），我们按照「出队添加到尾部」的方式进行；对于所在层数为奇数，我们按照「出队添加到头部」的方式进行。

> 为支持「从尾部追加元素」和「从头部追加元素」操作，`Java` 可使用基于链表的 `LinkedList`，而 `TS` 可创建定长数组后通过下标赋值。

其中判断当前所在层数，无须引用额外变量，直接根据当前 `ans` 的元素大小即可。

Java 代码：
```Java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Deque<TreeNode> d = new ArrayDeque<>();
        if (root != null) d.addLast(root);
        while (!d.isEmpty()) {
            LinkedList<Integer> list = new LinkedList<>();
            int sz = d.size(), dirs = ans.size() % 2;
            while (sz-- > 0) {
                TreeNode t = d.pollFirst();
                if (dirs == 0) list.addLast(t.val);
                else list.addFirst(t.val); 
                if (t.left != null) d.addLast(t.left);
                if (t.right != null) d.addLast(t.right);
            }
            ans.add(list);
        }
        return ans;
    }
}
```
Typescript 代码：
```Typescript
function levelOrder(root: TreeNode | null): number[][] {
    const ans = new Array<Array<number>>()
    const stk = new Array<TreeNode>()
    let he = 0, ta = 0
    if (root != null) stk[ta++] = root
    while (he < ta) {
        const dirs = ans.length % 2 == 0
        let sz = ta - he, idx = dirs ? 0 : sz - 1
        const temp = new Array<number>(sz)
        while (sz-- > 0) {
            const t = stk[he++]
            temp[idx] = t.val
            idx += dirs ? 1 : -1
            if (t.left != null) stk[ta++] = t.left
            if (t.right != null) stk[ta++] = t.right
        }
        ans.push(temp)
    }
    return ans
};
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

---

### 递归 - DFS

递归的实现方式与前两题同理。

不过对于 `TS`  语言来说，由于 `DFS` 过程中无法知道当前层有多少节点，因此只能在使用「哈希表」记录每层「从左往右」的方向，然后在构造答案时，运用「双指针」来将奇数层的节点进行翻转。

Java 代码：
```Java
class Solution {
    Map<Integer, LinkedList<Integer>> map = new HashMap<>();
    int max = -1;
    public List<List<Integer>> levelOrder(TreeNode root) {
        dfs(root, 0);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i <= max; i++) ans.add(map.get(i));
        return ans;
    }
    void dfs(TreeNode root, int depth) {
        if (root == null) return ;
        max = Math.max(max, depth);
        dfs(root.left, depth + 1);
        LinkedList<Integer> list = map.getOrDefault(depth, new LinkedList<>());
        if (depth % 2 == 0) list.addLast(root.val);
        else list.addFirst(root.val);
        map.put(depth, list);
        dfs(root.right, depth + 1);
    }
}
```
TypeScript 代码：
```TypeScript
const map: Map<number, Array<number>> = new Map<number, Array<number>> ()
let max = -1
function levelOrder(root: TreeNode | null): number[][] {
    map.clear()
    max = -1
    dfs(root, 0)
    const ans = new Array<Array<number>>()
    for (let i = 0; i <= max; i++) {
        const temp = map.get(i)
        if (i % 2 == 1) {
            for (let p = 0, q = temp.length - 1; p < q; p++, q--) {
                const c = temp[p]
                temp[p] = temp[q]
                temp[q] = c
            } 
        }
        ans.push(temp)
    }
    return ans
};
function dfs(root: TreeNode | null, depth: number): void {
    if (root == null) return 
    max = Math.max(max, depth)
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

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 32 - III` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

