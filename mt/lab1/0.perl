
while($str = <>) {
    $str =~ s/^(\s*)(.*?)(\s*)$/\2/;
    $str =~ s/(\s+)/ /g;
    $a = $a . "\n" . $str;
}
$a = $a . "mycor";

$a =~ s/^(\s*)//;
$a =~ s/(\s*)$//;
$a =~ s/(\s+)/ /g;

$a =~ s/(.*?)(<\s*a.*?href\s*=\s*"\s*)(.*?)(\s*".*?>)/\3@/g;
#
$a =~ s/(.*)@(.*)$/\1/;
$a =~ s/@/\n/g;
$a = $a . "\n";

$a =~ s/(.*?)\/\/+(.+)[\/:](.*?)\n/\2\n/g; #http://domen:80/next
$a =~ s/(.*?)\/\/+(.+)\n/\2\n/g;           #http://domen
$a =~ s/(.+)[\/:](.*?)\n/\1\n/g;           #domen:80/next
$a =~ s/(.+)\n/\1\n/g;                     #domen

my @b = split('\n', $a);
my @c = sort @b;
$lst = " ";

foreach my $str (@c) {
    if ($str ne $lst) {
        print $str;
        print "\n";
        $lst = $str;
    } 
}


