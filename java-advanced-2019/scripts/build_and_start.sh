pth="/home/friendlyevil/tmp/itmo/java-advanced-2019/"
test=${pth}artifacts
lib=${pth}lib
out=${pth}out
clp=${test}\;${out}\;${lib}

# echo $clp

start() {
	kgpac=$1
	startname=$2
	mypac=$3
	programname=$4
	javac -d ${out} -cp ${test}/info.kgeorgiy.java.advanced.${kgpac}.jar ${pth}java/ru/ifmo/rain/krivopaltsev/${mypac}/*.java
	java -Dfile.encoding=UTF-8 -cp ${clp} -p ${clp} -m info.kgeorgiy.java.advanced.${kgpac} ${startname} ru.ifmo.rain.krivopaltsev.${mypac}.${programname}

}

case $1 in
    1)      
        start walk RecursiveWalk walk RecursiveWalk
        ;;
    2)      
        start arrayset NavigableSet arrayset ArraySet
        ;;
    3)
        start student StudentGroupQuery student StudentDB
        ;; 
	4)
		start implementor class implementor Implementor
	  	;; 
	5)
		start implementor jar-class implementor Implementor
	  	;; 
	7)
		start concurrent list concurrent IterativeParallelism
	  	;; 
	8)
		start mapper list concurrent ParallelMapperImpl
	  	;; 
	9)
		start crawler hard crawler WebCrawler
	  	;; 
	10)
		start hello server-i18n hello HelloUDPServer
		start hello client-i18n hello HelloUDPClient
	  	;; 
esac