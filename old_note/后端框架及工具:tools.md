rebase
应用场景: 特征分支同步develop 的更新的情况
用法: (当前在feature分支),
执行 git rebase develop. 表示develop 中之后的更新部分内容 在 feature 分支上重画一遍.
这时 feature中就更新了develop中的更新部分.
应用原则: 不能再 公共分支(master,develop,release)上执行rebase操作, 即在"当前分支在master的情况下,执行 git rebase feature" 操作