while(<>) {
    s/\((.+?)\)/\(\)/g;
    print;
}