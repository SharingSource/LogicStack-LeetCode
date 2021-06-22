### 题目描述

这是 LeetCode 上的 **[剑指 Offer 38. 字符串的排列](https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof/solution/gong-shui-san-xie-tong-yong-shi-xian-qu-4jbkj/)** ，难度为 **中等**。

Tag : 「字符串」、「回溯算法」



输入一个字符串，打印出该字符串中字符的所有排列。

你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。

示例:
```
输入：s = "abc"
输出：["abc","acb","bac","bca","cab","cba"]
```

限制：
* 1 <= s 的长度 <= 8

---

### 基本分析

根据题意，我们需要返回所有不重复的排列字符串。

此类问题通常都是使用 `DFS` 来做：**决策当前字符串的某一位填入什么字符。**

设计如下 `DFS` 函数：

```Java []
/**
 *   cs : 原字符串
 *    u : 当前决策到目标字符串的哪一位
 *  cur : 当前目标字符串
 */
void dfs(char[] cs, int u, String cur);
```

而对于去重通常有两种方式：

1. 使用 `Set` 实现去重；
2. 先对原字符串进行排序，然后确保相同字符传入同一目标位置的动作只发生一次。

---

### 回溯（Set 去重）

使用 `HashSet` 实现去重十分简单，就是不用考虑去重问题。

直接决策目标字符串哪一位取哪个字符即可，同时使用布尔数组记录哪些字符已使用。

代码：
```Java []
class Solution {
    int N = 10;
    Set<String> set = new HashSet<>();
    boolean[] vis = new boolean[N];
    public String[] permutation(String s) {
        char[] cs = s.toCharArray();
        dfs(cs, 0, "");
        String[] ans = new String[set.size()];
        int idx = 0;
        for (String str : set) ans[idx++] = str;
        return ans;
    }
    void dfs(char[] cs, int u, String cur) {
        int n = cs.length;
        if (u == n) {
            set.add(cur);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                vis[i] = true;
                dfs(cs, u + 1, cur + String.valueOf(cs[i]));
                vis[i] = false;
            }
        }
    }
}
```
* 时间复杂度：$O(n * n!)$
* 空间复杂度：$O(n)$

---

### 回溯（排序去重）

想要通过预处理实现去重，需要「**先对原字符串进行排序，然后确保相同字符传入同一目标位置的动作只发生一次**」。

具体的，我们可以在「决策目标字符串的第 $u$ 个位置」时，对于 `cs[i-1] = cs[i]` 的情况进行跳过，同时为了确保这个动作不会影响到后面的位置决策，需要利用同一目标位置会进行“回溯”的特性，增加一个 `!vis[i - 1]` 的判断。

这样对于形如 `...xxx...` 的原字符串，使用字符 `x` 决策目标字符串的第 $u$ 个位置的动作只会发生一次，从而确保了一模一样的分支不会在「递归树」中展开多次。

此类去重方式在很多方式出现过，事实上这种去重方式不仅仅能应用于排列问题。

其本质是对递归树中「状态完全相同」的节点进行跳过，从而实现去重。

这里的「状态完全相同」是指：**当前形成部分结果为 $cur$ 相同，同时剩余字符集合也相同**。

代码：
```Java []
class Solution {
    int N = 10;
    List<String> list = new ArrayList<>();
    boolean[] vis = new boolean[N];
    public String[] permutation(String s) {
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        dfs(cs, 0, "");
        String[] ans = new String[list.size()];
        int idx = 0;
        for (String str : list) ans[idx++] = str;
        return ans;
    }
    void dfs(char[] cs, int u, String cur) {
        int n = cs.length;
        if (u == n) {
            list.add(cur);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (i > 0 && !vis[i - 1] && cs[i] == cs[i - 1]) continue;
            if (!vis[i]) {
                vis[i] = true;
                dfs(cs, u + 1, cur + String.valueOf(cs[i]));
                vis[i] = false;
            }
        }
    }
}
```
* 时间复杂度：$O(n * n!)$
* 空间复杂度：$O(n)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer 38` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先将所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

