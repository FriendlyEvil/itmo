while(<>) {
    s/\b(.+?)\b(.+?)\b(.+?)\b/\3\2\1/;
    print;
}
