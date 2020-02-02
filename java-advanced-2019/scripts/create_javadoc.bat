SET link=https://docs.oracle.com/en/java/javase/11/docs/api/
SET pref=C:\Users\mi\IdeaProjects\java_4sem\java-advanced-2019\
SET out=%pref%out
SET pth=%pref%artifacts\info.kgeorgiy.java.advanced.implementor.jar
SET kgpath=%pref%modules\info.kgeorgiy.java.advanced.implementor\info\kgeorgiy\java\advanced\implementor\
SET mypath=%pref%java\ru\ifmo\rain\krivopaltsev\implementor\

javadoc -link %link% -d %out% -cp %pth% -private %mypath%Implementor.java %kgpath%JarImpler.java %kgpath%Impler.java
