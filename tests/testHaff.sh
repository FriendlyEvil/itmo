countPassed=0
count=0

RED='\033[0;31m'
GREEN='\033[0;92m'
BLUE='\033[0;96m'
PURP='\033[0;95m'
NC='\033[0m'

function compare()
{
    if cmp "$1" "$2"; then
    	echo "Equal"
    else
    	echo "Not Equal"
    fi
}

function test()
{
    FILESIZE1=$(stat -c%s Data/"$1"."$2")
    printf "${GREEN}TESTING:${NC}"
    printf "${PURP}$1 test: ${NC} \n"
    let "count++"
    ./Huffman e Data/"$1"."$2" Data/"$1".haf
    FILESIZE=$(stat -c%s Data/"$1".haf)
    ./Huffman d Data/"$1".haf Data/"$1"1."$2"
    res=$(compare Data/"$1"."$2" Data/"$1"1."$2")
    if [ "$res" == "Equal" ] ; then
        printf "Initially size:  $FILESIZE1 \nCompressed size: $FILESIZE \n"
        printf "${GREEN}[ OK ] ${BLUE}$1 test${NC} \n"
        let "countPassed+=1"
    else
        printf "${RED}[ Fail ]${NC} \n"
    fi
}

echo "==========Testing==========="

time=$(date +%s%N | cut -b1-13)

test a txt
test empty txt
test abrakadabra txt
test aaaaaa txt
test ababab txt
test many_a txt
test picture jpg
test Gamlet txt
test pdf pdf

echo "============================"
echo "Tests passed in $(($(date +%s%N | cut -b1-13) - $time)) miliseconds"
echo "Test passed: ""$countPassed"" from: ""$count"
