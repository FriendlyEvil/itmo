
while($str = <>) {
    $str =~ s/^(\s*)(.*?)(\s*)$/\2/;
    $str =~ s/(\s+)/ /g;
    $a = $a . "\n" . $str;
}

$a =~ s/^(\s*)//;
$a =~ s/(\s*)$//;
$a =~ s/(\s\s+)/\n\n/g;

print $a;


