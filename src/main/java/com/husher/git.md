1、更新git (Linux / Unix：git update)
    <u><b>git update-git-for-windows</b></u>

2、git push报错：fatal: unable to access的解决方法：
<p>在执行 git pull 、 git push 等命令是会报错，可能是权限的问题，账号被修改了，可以执行如下命令：
    <><u><b>git config --global credential.helper store</b></u>
<p>然后执行：<u><b>git push</b></u>