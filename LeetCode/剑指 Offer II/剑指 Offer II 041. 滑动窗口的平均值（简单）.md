### 题目描述

这是 LeetCode 上的 **[剑指 Offer II 041. 滑动窗口的平均值](https://leetcode.cn/problems/qIsx9U/solution/by-ac_oier-g5ha/)** ，难度为 **简单**。

Tag : 「模拟」、「队列」



给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算滑动窗口里所有数字的平均值。

实现 `MovingAverage` 类：

* `MovingAverage(int size)` 用窗口大小 `size` 初始化对象。
* `double next(int val)` 成员函数 `next` 每次调用的时候都会往滑动窗口增加一个整数，请计算并返回数据流中最后 `size` 个值的移动平均值，即滑动窗口里所有数字的平均值。

示例：
```
输入：
inputs = ["MovingAverage", "next", "next", "next", "next"]
inputs = [[3], [1], [10], [3], [5]]

输出：
[null, 1.0, 5.5, 4.66667, 6.0]

解释：
MovingAverage movingAverage = new MovingAverage(3);
movingAverage.next(1); // 返回 1.0 = 1 / 1
movingAverage.next(10); // 返回 5.5 = (1 + 10) / 2
movingAverage.next(3); // 返回 4.66667 = (1 + 10 + 3) / 3
movingAverage.next(5); // 返回 6.0 = (10 + 3 + 5) / 3
```

提示：
* $1 <= size <= 1000$
* $-10^5 <= val <= 10^5$
* 最多调用 `next` 方法 $10^4$ 次

---

### 双端队列

根据题意，我们可以使用变量 `n` 将初始化传入的 `size` 进行转存，同时使用「双端队列」来存储 `next` 所追加的值（添加到队列尾部），当双端队列所包含元素超过规定数量 `n` 时，我们从队列头部进行 `pop` 操作，整个维护过程使用变量 `sum` 记录当前包含的元素和。

利用 `next` 操作最多被调用 $10^4$ 次，我们可以使用直接开个 $10^4$ 数组来充当双端队列，使用两指针 `j` 和 `i` 分别指向队列的头部和尾部。

Java 代码：
```Java
class MovingAverage {
    int[] arr = new int[10010];
    int n, sum, j, i;
    public MovingAverage(int size) {
        n = size;
    }
    public double next(int val) {
        sum += arr[i++] = val;
        if (i - j > n) sum -= arr[j++];
        return sum * 1.0 / (i - j);
    }
}
```
Typescript 代码：
```Typescript
class MovingAverage {
    arr: number[] = new Array<number>(10010).fill(0)
    n: number = 0; sum: number = 0; i: number = 0; j: number = 0;
    constructor(size: number) {
        this.n = size
    }
    next(val: number): number {
        this.sum += this.arr[this.i++] = val
        if (this.i - this.j > this.n) this.sum -= this.arr[this.j++]
        return this.sum / (this.i - this.j)
    }
}
```
* 时间复杂度：$O(m)$，其中 $m$ 为 `next` 操作的调用次数
* 空间复杂度：$O(n)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer II 041` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

