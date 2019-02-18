SET pth=C:\Users\mi\IdeaProjects\java_4sem\java-advanced-2019\
SET test=%pth%artifacts
SET lib=%pth%lib
SET out=%pth%out

SET clp=%test%;%out%;%lib%

javac -d %out% %pth%java\ru\ifmo\rain\krivopaltsev\%1\*.java

java -cp %clp% -p %clp% -m info.kgeorgiy.java.advanced.%1 %2 ru.ifmo.rain.krivopaltsev.%1.%3