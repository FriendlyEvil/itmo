while(<>) {
    s/\((.+?)\)/\(\)/g;
    print;
}
