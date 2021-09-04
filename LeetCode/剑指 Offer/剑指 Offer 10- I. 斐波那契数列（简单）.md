### 题目描述

这是 LeetCode 上的 **[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/solution/gong-shui-san-xie-yi-ti-si-jie-dong-tai-9zip0/)** ，难度为 **简单**。

Tag : 「动态规划」、「线性 DP」、「记忆化搜索」、「打表」、「矩阵快速幂」



写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：

* F(0) = 0,   F(1) = 1
* F(N) = F(N - 1) + F(N - 2), 其中 N > 1.

斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

示例 1：
```
输入：n = 2

输出：1
```
示例 2：
```
输入：n = 5

输出：5
```

提示：
* 0 <= n <= 100

---

### 递推实现动态规划

既然转移方程都给出了，直接根据转移方程从头到尾递递推一遍即可。

代码：
```Java
class Solution {
    int mod = (int)1e9+7;
    public int fib(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            c %= mod;
            a = b;
            b = c;
        }
        return b;
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 递归实现动态规划

能以「递推」形式实现动态规划，自然也能以「递归」的形式实现。

为防止重复计算，我们需要加入「记忆化搜索」功能，同时利用某个值 $x$ 在不同的样例之间可能会作为“中间结果”被重复计算，并且计算结果 $fib(x)$ 固定，我们可以使用 `static` 修饰缓存器，以实现计算过的结果在所有测试样例中共享。

代码：
```Java
class Solution {
    static int mod = (int)1e9+7;
    static int N = 110;
    static int[] cache = new int[N];
    public int fib(int n) {
        if (n <= 1) return n;
        if (cache[n] != 0) return cache[n];
        cache[n] = fib(n - 1) + fib(n - 2);
        cache[n] %= mod;
        return cache[n];
    }
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 打表

经过「解法二」，我们进一步发现，可以利用数据范围只有 $100$ 进行打表预处理，然后直接返回。

代码：
```Java
class Solution {
    static int mod = (int)1e9+7;
    static int N = 110;
    static int[] cache = new int[N];
    static {
        cache[1] = 1;
        for (int i = 2; i < N; i++) {
            cache[i] = cache[i - 1] + cache[i - 2];
            cache[i] %= mod;
        }
    }
    public int fib(int n) {
        return cache[n];
    }
}
```
* 时间复杂度：将打表逻辑放到本地执行，复杂度为 $O(1)$；否则为 $O(C)$，$C$ 为常量，固定为 $110$
* 空间复杂度：$O(C)$

---

### 矩阵快速幂

**对于数列递推问题，可以使用矩阵快速幂进行加速，最完整的介绍在 [这里](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247488198&idx=1&sn=8272ca6b0ef6530413da4a270abb68bc&chksm=fd9cb9d9caeb30cf6c2defab0f5204adc158969d64418916e306f6bf50ae0c38518d4e4ba146&token=1067450240&lang=zh_CN#rd) 讲过。**

对于本题，某个 $f(n)$ 依赖于 $f(n - 1)$ 和 $f(n - 2)$，将其依赖的状态存成列向量：

$$
\begin{bmatrix}
f(n - 1)\\ 
f(n - 2)
\end{bmatrix}
$$

目标值 $f(n)$ 所在矩阵为：

$$
\begin{bmatrix}
f(n)\\ 
f(n - 1)
\end{bmatrix}
$$

根据矩阵乘法，不难发现：

$$
\begin{bmatrix}
f(n)\\ 
f(n - 1)
\end{bmatrix} 

= 

\begin{bmatrix}
1& 1\\ 
1& 0
\end{bmatrix}

*

\begin{bmatrix}
f(n - 1)\\ 
f(n - 2)
\end{bmatrix}
$$

我们令：

$$
mat = 
\begin{bmatrix}
1& 1\\ 
1& 0
\end{bmatrix}
$$

起始时，我们只有 $
\begin{bmatrix}
f(1)\\ 
f(0)
\end{bmatrix}
$，根据递推式得：

$$
\begin{bmatrix}
f(n)\\ 
f(n - 1)
\end{bmatrix}

=

mat * mat * ... * mat * 

\begin{bmatrix}
f(1)\\ 
f(0)
\end{bmatrix}
$$

再根据矩阵乘法具有「结合律」，最终可得：

$$
\begin{bmatrix}
f(n)\\ 
f(n - 1)
\end{bmatrix}

=

mat^{n - 1} 

*

\begin{bmatrix}
f(1)\\ 
f(0)
\end{bmatrix}
$$

计算 $mat^{n - 1}$ 可以套用「快速幂」进行求解。

代码：
```Java
class Solution {
    int mod = (int)1e9+7;
    long[][] mul(long[][] a, long[][] b) {
        int r = a.length, c = b[0].length, z = b.length;
        long[][] ans = new long[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                for (int k = 0; k < z; k++) {
                    ans[i][j] += a[i][k] * b[k][j];
                    ans[i][j] %= mod;
                }
            }
        }
        return ans;
    }
    public int fib(int n) {
        if (n <= 1) return n;
        long[][] mat = new long[][]{
            {1, 1},
            {1, 0}
        };
        long[][] ans = new long[][]{
            {1},
            {0}
        };
        int x = n - 1;
        while (x != 0) {
            if ((x & 1) != 0) ans = mul(mat, ans);
            mat = mul(mat, mat);
            x >>= 1;
        }
        return (int)(ans[0][0] % mod);
    }
}
```
* 时间复杂度：$O(\log{n})$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer 10- I` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

