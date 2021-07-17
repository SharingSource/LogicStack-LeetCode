### 题目描述

这是 LeetCode 上的 **[剑指 Offer 53 - I. 在排序数组中查找数字 I](https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/solution/gong-shui-san-xie-liang-chong-er-fen-ton-3epx/)** ，难度为 **简单**。

Tag : 「二分」



统计一个数字在排序数组中出现的次数。

示例 1:
```
输入: nums = [5,7,7,8,8,10], target = 8

输出: 2
```
示例 2:
```
输入: nums = [5,7,7,8,8,10], target = 6

输出: 0
```

限制：
* 0 <= 数组长度 <= 50000

---

### 二分单边 + 线性扫描

一个朴素的想法是，找到目标值 $target$ 「首次」出现或者「最后」出现的下标，然后「往后」或者「往前」进行数量统计。

代码：
```Java
// 找到目标值「最后」出现的分割点，并「往前」进行统计
class Solution {
    public int search(int[] nums, int t) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] <= t) l = mid;
            else r = mid - 1;
        }
        int ans = 0;
        while (r >= 0 && nums[r] == t && r-- >= 0) ans++;
        return ans;
    }
}
```
```Java
// 找到目标值「首次」出现的分割点，并「往后」进行统计
class Solution {
    public int search(int[] nums, int t) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= t) r = mid;
            else l = mid + 1;
        }
        int ans = 0;
        while (l < n && nums[l] == t && l++ >= 0) ans++;
        return ans;
    }
}
```
* 时间复杂度：二分找到分割点之后，需要往前或者往后进行扫描。复杂度为 $O(n)$
* 空间复杂度：$O(1)$

---

### 二分两边

进一步，我们可以直接经过两次「二分」找到左右边界，计算总长度即是 $target$ 的数量。

代码：
```Java
class Solution {
    public int search(int[] nums, int t) {
        int n = nums.length;
        if (n == 0) return 0;
        int a = -1, b = -1;

        // 二分出左边界
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= t) r = mid;
            else l = mid + 1;
        }
        if (nums[r] != t) return 0;
        a = r;

        // 二分出右边界
        l = 0; r = n - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] <= t) l = mid;
            else r = mid - 1;
        }
        if (nums[r] != t) return 0;
        b = r;

        return b - a + 1;
    }
}
```
* 时间复杂度：$O(\log{n})$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `No.剑指 Offer 53 - I` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

