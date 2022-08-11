### 题目描述

这是 LeetCode 上的 **[剑指 Offer 53 - II. 0～n-1中缺失的数字](https://leetcode.cn/problems/que-shi-de-shu-zi-lcof/solution/by-ac_oier-gs6q/)** ，难度为 **简单**。

Tag : 「二分」



一个长度为 $n-1$ 的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围 $[0, n-1]$ 之内。

在范围 $[0, n-1]$ 内的 $n$ 个数字中有且只有一个数字不在该数组中，请找出这个数字。

示例 1:
```
输入: [0,1,3]

输出: 2
```
示例 2:
```
输入: [0,1,2,3,4,5,6,7,9]

输出: 8
```

限制：
* $1 <= 数组长度 <= 10000$

---

### 二分

强调过无数次，二分的本质是「二段性」。

我们可以考虑对于一个给定数组 `nums` 中，对于缺失元素左右数值而言，有何性质。

假设我们缺失的元素数值为 `x`，那么对于 `x` 左边的元素（若有）必然是完整且连续的，也就是其必然满足 `nums[i] = i`，而其右边的元素（若有）必然由于 `x` 的缺失，导致必然不满足 `nums[i] = i`，因此在以缺失元素为分割点的数轴上，具有二段性，我们可以使用「二分」来找该分割点。

同时由于缺失元素可能是 $[0, n - 1]$ 范围内的最大值，因此我们需要二分结束后再 `check` 一次，若不满足，说明缺失的元素值为 $n - 1$。

Java 代码：
```Java
class Solution {
    public int missingNumber(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] == mid) l = mid;
            else r = mid - 1;
        }
        return nums[r] != r ? r : r + 1;
    }
}
```
Typescript 代码：
```Typescript
function missingNumber(nums: number[]): number {
    let l = 0, r = nums.length - 1
    while (l < r) {
        const mid = l + r + 1 >> 1
        if (nums[mid] == mid) l = mid
        else r = mid - 1
    }
    return nums[r] != r ? r : r + 1
};
```
* 时间复杂度：$O(\log{n})$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 53 - II` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

