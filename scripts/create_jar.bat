SET pref=C:\Users\mi\IdeaProjects\java_4sem\java-advanced-2019\
SET test=%pref%artifacts
SET impl=info\kgeorgiy\java\advanced\implementor\


jar xf %test%\info.kgeorgiy.java.advanced.implementor.jar %impl%Impler.class %impl%JarImpler.class %impl%ImplerException.class
jar cfm Implementor.jar %pref%java\META-INF\MANIFEST.MF ru\ifmo\rain\krivopaltsev\implementor\*.class %impl%*.class
