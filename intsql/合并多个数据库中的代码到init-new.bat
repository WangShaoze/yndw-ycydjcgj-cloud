@echo off
setlocal enabledelayedexpansion

(
    rem 首先将init-new.sql的内容复制过来
    type init-new.sql

    rem 然后查找并复制xxzx-开头的sql文件内容
    for %%f in (xxzx-*.sql) do (
        type "%%f"
    )
) > temp.sql

rem 将临时文件重命名为init-new.sql
move /y temp.sql init-new.sql