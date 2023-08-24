### 题目描述

这是 LeetCode 上的 **[剑指 Offer 20. 表示数值的字符串](https://leetcode.cn/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/solution/gong-shui-san-xie-chang-gui-zi-fu-chuan-sduvz/)** ，难度为 **中等**。

Tag : 「模拟」



请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。

**数值**（按顺序）可以分成以下几个部分：

1. 若干空格
2. 一个 小数 或者 整数
3. （可选）一个 `'e'` 或 `'E'` ，后面跟着一个 整数
4. 若干空格

**小数**（按顺序）可以分成以下几个部分：

1. （可选）一个符号字符（`'+'` 或 `'-'`）
2. 下述格式之一：
	1. 至少一位数字，后面跟着一个点 `'.'`
	2. 至少一位数字，后面跟着一个点 `'.'` ，后面再跟着至少一位数字
	3. 一个点 `'.'` ，后面跟着至少一位数字

**整数**（按顺序）可以分成以下几个部分：

1. （可选）一个符号字符（`'+'` 或 `'-'`）
2. 至少一位数字

部分数值列举如下：
* `["+100", "5e2", "-123", "3.1416", "-1E-16", "0123"]`

部分非数值列举如下：
* `["12e", "1a3.14", "1.2.3", "+-5", "12e+5.4"]`

示例 1：
```
输入：s = "0"

输出：true
```
示例 2：
```
输入：s = "e"

输出：false
```
示例 3：
```
输入：s = "."

输出：false
```
示例 4：
```
输入：s = "    .1  "

输出：true
```

提示：
* $1 <= s.length <= 20$
* `s` 仅含英文字母（大写和小写），数字（`0-9`），加号 `'+'`，减号 `'-'` ，空格 `' '` 或者点 `'.'` 。

---

### 模拟

我们对题面中的「数值」、「小数」和「整数」进行重新定义。

* 整数：可以有 `'+'` 或 `'-'`，但不能同时存在；且至少有一个数字
* 小数：可以有 `'+'` 或 `'-'`，但不能同时存在；有 `.`，且 `.` 的两边至少有一个数字
* 数值：前后可以有连续段的空格，其余位置则不能有；可以有 `E/e`，但最多只能有一个，同时 `E/e` 分割的左边可以是「整数」或「小数」，右边则只能是「整数」

根据上面的重新定义，再来设计我们的基本处理流程：

* 将 `s` 两端的连续段空格进行去除，得到不该含有空格的核心串，若核心串为空，则返回 `False`；
* 将所有的小写 `e` 切换为 `E`
* 从前往后，找到第一个 `E` 的所在位置 `idx`，根据是否有 `E` 进行分情况讨论：
  * 若没有 `E`（即 `idx = n`），判断整个核心串是否为「整数」或「小数」
  * 若有 `E`（即 `idx < n`），判断以 `E` 为分割的左半部分是否为「整数」或「小数」，判断以 `E` 为分割的右半部分是否「整数」

Java 代码：

```Java
class Solution {
    public boolean isNumber(String s) {
        int n = s.length(), l = 0, r = n - 1;
        while (l < n && s.charAt(l) == ' ') l++;
        while (r >= 0 && s.charAt(r) == ' ') r--;
        if (l > r) return false;
        s = s.substring(l, r + 1).replace('e', 'E');
        n = s.length();
        int idx = 0;
        while (idx < n && s.charAt(idx) != 'E') idx++;
        if (idx == n) {
            return check(s, true);
        } else {
            return check(s.substring(0, idx), true) && check(s.substring(idx + 1), false);
        }
    }
    boolean check(String s, boolean isDecimal) {
        if (s.equals(".") || s.equals("")) return false;
        int n = s.length();
        for (int i = 0, cnt = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') {
                if (i != 0 || i == n - 1) return false;
            } else if (c == '.') {
                if (!isDecimal) return false;
                if (cnt != 0) return false;
                boolean a = i - 1 >= 0 && Character.isDigit(s.charAt(i - 1));
                boolean b = i + 1 < n && Character.isDigit(s.charAt(i + 1));
                if (!(a || b)) return false;
                cnt++;
            } else if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
```
C++ 代码：
```C++
class Solution {
public:
    bool isNumber(string s) {
        int n = s.length(), l = 0, r = n - 1;
        while (l < n && s[l] == ' ') l++;
        while (r >= 0 && s[r] == ' ') r--;
        if (l > r) return false;
        s = s.substr(l, r - l + 1);
        for (char& c : s) {
            if (c == 'e') c = 'E';
        }
        n = s.length();
        int idx = 0;
        while (idx < n && s[idx] != 'E') idx++;
        if (idx == n) {
            return check(s, true);
        } else {
            return check(s.substr(0, idx), true) && check(s.substr(idx + 1), false);
        }
    }
    bool check(string s, bool isDecimal) {
        if (s == "." || s == "") return false;
        int n = s.length();
        for (int i = 0, cnt = 0; i < n; i++) {
            char c = s[i];
            if (c == '+' || c == '-') {
                if (i != 0 || i == n - 1) return false;
            } else if (c == '.') {
                if (!isDecimal) return false;
                if (cnt != 0) return false;
                bool a = i - 1 >= 0 && isdigit(s[i - 1]);
                bool b = i + 1 < n && isdigit(s[i + 1]);
                if (!(a || b)) return false;
                cnt++;
            } else if (!isdigit(c)) return false;
        }
        return true;
    }
};
```
Python 代码：
```Python
class Solution:
    def isNumber(self, s: str) -> bool:
        n, l, r = len(s), 0, len(s) - 1
        while l < n and s[l] == ' ':
            l += 1
        while r >= 0 and s[r] == ' ':
            r -= 1
        if l > r:
            return False
        s = s[l:r + 1].replace('e', 'E')
        n, idx = len(s), 0
        while idx < n and s[idx] != 'E':
            idx += 1
        if idx == n:
            return self.check(s, True)
        else:
            return self.check(s[:idx], True) and self.check(s[idx + 1:], False)
            
    def check(self, s: str, is_decimal: bool) -> bool:
        if s == "." or s == "":
            return False
        n, cnt = len(s), 0
        for i in range(n):
            c = s[i]
            if c == '+' or c == '-':
                if i != 0 or i == n - 1:
                    return False
            elif c == '.':
                if not is_decimal:
                    return False
                if cnt != 0:
                    return False
                a = i - 1 >= 0 and s[i - 1].isdigit()
                b = i + 1 < n and s[i + 1].isdigit()
                if not (a or b):
                    return False
                cnt += 1
            elif not c.isdigit():
                return False
        return True
```
TypeScript 代码：
```TypeScript
function isNumber(s: string): boolean {
    let n = s.length, l = 0, r = n - 1;
    while (l < n && s.charAt(l) === ' ') l++;
    while (r >= 0 && s.charAt(r) === ' ') r--;
    if (l > r) return false;
    s = s.substring(l, r + 1).replace(/e/g, 'E');
    n = s.length;
    let idx = 0;
    while (idx < n && s.charAt(idx) !== 'E') idx++;
    if (idx === n) {
        return check(s, true);
    } else {
        return check(s.substring(0, idx), true) && check(s.substring(idx + 1), false);
    }
}

function check(s: string, isDecimal: boolean): boolean {
    if (s === '.' || s === '') return false;
    const n = s.length;
    for (let i = 0, cnt = 0; i < n; i++) {
        const c = s.charAt(i);
        if (c === '+' || c === '-') {
            if (i !== 0 || i === n - 1) return false;
        } else if (c === '.') {
            if (!isDecimal) return false;
            if (cnt !== 0) return false;
            const a = i - 1 >= 0 && /\d/.test(s.charAt(i - 1));
            const b = i + 1 < n && /\d/.test(s.charAt(i + 1));
            if (!(a || b)) return false;
            cnt++;
        } else if (!/\d/.test(c)) return false;
    }
    return true;
}
```
* 时间复杂度：$O(n)$
* 空间复杂度：$O(1)$

---

### 最后

这是我们「刷穿 LeetCode」系列文章的第 `剑指 Offer 20` 篇，系列开始于 2021/01/01，截止于起始日 LeetCode 上共有 1916 道题目，部分是有锁题，我们将先把所有不带锁的题目刷完。

在这个系列文章里面，除了讲解解题思路以外，还会尽可能给出最为简洁的代码。如果涉及通解还会相应的代码模板。

为了方便各位同学能够电脑上进行调试和提交代码，我建立了相关的仓库：https://github.com/SharingSource/LogicStack-LeetCode 。

在仓库地址里，你可以看到系列文章的题解链接、系列文章的相应代码、LeetCode 原题链接和其他优选题解。

