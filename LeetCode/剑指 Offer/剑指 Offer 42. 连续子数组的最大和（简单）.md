### 题目描述

这是 LeetCode 上的 **[剑指 Offer 42. 连续子数组的最大和](https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/solution/gong-shui-san-xie-jian-dan-xian-xing-dp-mqk5v/)** ，难度为 **简单**。

Tag : 「线性 DP」




输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。

要求时间复杂度为$O(n)$。


示例1:
```
输入: nums = [-2,1,-3,4,-1,2,1,-5,4]

输出: 6

解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```

提示：
* 1 <= arr.length <= $10^5$
* -100 <= arr[i] <= 100

---

### 动态规划

这是一道简单线性 DP 题。

定义 $f[i]$ 为考虑以 $nums[i]$ 为结尾的子数组的最大值。

不失一般性的考虑 $f[i]$ 如何转移。

显然对于 $nums[i]$ 而言，以它为结尾的子数组分两种情况：

* $num[i]$ 自身作为独立子数组：$f[i] = nums[i]$ ；
* $num[i]$ 与之前的数值组成子数组，由于是子数组，其只能接在 $nums[i - 1]$，即有：$f[i] = f[i - 1] + nums[i]$。

最终 $f[i]$ 为上述两种情况取 $\max$ 即可：

$$
f[i] = \max(nums[i], f[i - 1] + nums[i])
$$

代码：
```Java
class Solution {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] f = new int[n];
        f[0] = nums[0];
        int ans = f[0];
        for (int i = 1; i < n; i++) {
            f[i] = Math.max(nums[i], f[i - 1] + nums[i]);
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(n)$

--- 


### 空间优化

观察状态转移方程，我们发现 $f[i]$ 明确值依赖于 $f[i - 1]$。

因此我们可以使用「有限变量」或者「滚动数组」的方式，将空间优化至 $O(1)$。

代码：
```Java
class Solution {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int max = nums[0], ans = max;
        for (int i = 1; i < n; i++) {
            max = Math.max(nums[i], max + nums[i]);
            ans = Math.max(ans, max);
        }
        return ans;
    }
}
```
```Java
class Solution {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] f = new int[2];
        f[0] = nums[0];
        int ans = f[0];
        for (int i = 1; i < n; i++) {
            int a = i & 1, b = (i - 1) & 1;
            f[a] = Math.max(nums[i], f[b] + nums[i]);
            ans = Math.max(ans, f[a]);
        }
        return ans;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$


---

### 拓展

一个有意思的拓展是，将 **加法** 替换成 **乘法**。

题目变成 [152. 乘积最大子数组（中等）](https://leetcode-cn.com/problems/maximum-product-subarray/)。

又该如何考虑呢？

一个朴素的想法，仍然是考虑定义 $f[i]$ 代表以 $nums[i]$ 为结尾的最大值，但存在「负负得正」取得最大值的情况，光维护一个前缀最大值显然是不够的，我们可以多引入一维 $g[i]$ 作为前缀最小值。

其余分析与本题同理。

代码：
```Java
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] g = new int[n + 1]; // 考虑前 i 个，结果最小值
        int[] f = new int[n + 1]; // 考虑前 i 个，结果最大值
        g[0] = 1;
        f[0] = 1;
        int ans = nums[0];
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            g[i] = Math.min(x, Math.min(g[i - 1] * x, f[i - 1] * x));
            f[i] = Math.max(x, Math.max(g[i - 1] * x, f[i - 1] * x));
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }
}
```
```Java
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int min = 1, max = 1;
        int ans = nums[0];
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            int nmin = Math.min(x, Math.min(min * x, max * x));
            int nmax = Math.max(x, Math.max(min * x, max * x));
            min = nmin;
            max = nmax;
            ans = Math.max(ans, max);
        }
        return ans;
    }
}
```

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 42` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

