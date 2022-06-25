# 拉取components子模块

```text
// 初始化本地子项目配置
git submodule init

// 拉取子仓库文件
git submodule update
```

组合命令
`git submodule update --init --recursive`

# 添加子模块

先进入父仓库目录下，然后执行命令：

```shell
git submodule add <仓库地址> <本地路径>
```

<本地路径>是可选项，默认情况下，子模块会在当前目录下面，将子项目放到一个与仓库同名的目录中。如果指定了本地路径，则会把子项目放在指定的本地路径下。
执行上面命令后，在父仓库根目录增加了.gitmodule文件。

```text
[submodule  仓库名称]
    path = 仓库名称（或指定的本地路径）
    url = 仓库地址
```

# 更新子仓库

更新已存在的仓库代码

```shell
git submodule foreach git pull origin master
```

更新新增的子仓库代码

```shell
git submodule update
```

# 删除子模块

1. git rm --cached [path]
   根据路径删除子模块的记录
2. 编辑 .gitmodules 文件，将子模块的相关配置节点删除掉
   清理子模块配置
3. 编辑 .git/config 文件，将子模块的相关配置节点删除掉
   清理子模块配置
4. 手动删除子模块残留的目录
   清理脏文件
