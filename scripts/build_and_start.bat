SET pth=C:\Users\mi\IdeaProjects\java_4sem\java-advanced-2019\
SET test=%pth%artifacts
SET lib=%pth%lib
SET out=%pth%out

SET clp=%test%;%out%;%lib%


goto hm%1
:hm1
call :setargs walk RecursiveWalk walk RecursiveWalk
goto start
:hm2
call :setargs arrayset NavigableSet arrayset ArraySet
goto start
:hm3
call :setargs student StudentGroupQuery student StudentDB
goto start
:hm4
call :setargs implementor class implementor Implementor
goto start
:hm5
call :setargs implementor jar-class implementor Implementor
goto start
:hm7
call :setargs concurrent list concurrent IterativeParallelism
goto start
:hm8
call :setargs mapper list concurrent ParallelMapperImpl
goto start
:hm9
call :setargs crawler hard crawler WebCrawler
goto start

:setargs
SET kgpac=%1
SET startname=%2
SET mypac=%3
SET programname=%4

exit /b 0

:start
if -%2==--c (
call :compile %1
) else (
    if -%2==--r (
        call :run %1
    ) else (
    call :compile %1
    call :run %1
    )
)
goto end

:compile78
javac -cp %test%\info.kgeorgiy.java.advanced.mapper.jar;%test%\info.kgeorgiy.java.advanced.concurrent.jar -d %out% %pth%java\ru\ifmo\rain\krivopaltsev\%mypac%\*.java
exit /b 0

:run78
java -Dfile.encoding=UTF-8 -cp %clp% -p %clp% -m info.kgeorgiy.java.advanced.%kgpac% %startname% ru.ifmo.rain.krivopaltsev.%mypac%.%programname%,ru.ifmo.rain.krivopaltsev.%mypac%.IterativeParallelism
exit /b 0

:compile
if %1 EQU 8 (
    call :compile78
) else (
if %1 EQU 7 (
    call :compile78	
) else (
    javac -d %out% -cp %test%\info.kgeorgiy.java.advanced.%kgpac%.jar %pth%java\ru\ifmo\rain\krivopaltsev\%mypac%\*.java
))
exit /b 0

:run
if %1 EQU 8 (
    call :run78
) else (
if %1 EQU 7 (
java -Dfile.encoding=UTF-8 -cp %clp%;%test%\info.kgeorgiy.java.advanced.mapper.jar;%test%\info.kgeorgiy.java.advanced.concurrent.jar -p %clp% -m info.kgeorgiy.java.advanced.%kgpac% %startname% ru.ifmo.rain.krivopaltsev.%mypac%.IterativeParallelism
) else (
java -Dfile.encoding=UTF-8 -cp %clp% -p %clp% -m info.kgeorgiy.java.advanced.%kgpac% %startname% ru.ifmo.rain.krivopaltsev.%mypac%.%programname%
))
exit /b 0

:end