use experimental 'smartmatch';

while($str = <>) {
    $str =~ s/^(\s*)(.*?)(\s*)$/\2/;
    $str =~ s/(\s+)/ /g;
    $a = $a . "\n" . $str;
}
$a = $a . "mycor";

$a =~ s/^(\s*)//;
$a =~ s/(\s*)$//;
$a =~ s/(\s+)/ /g;

$a =~ s/(.*?)(<\s*a.*?href\s*=\s*["']\s*)(.*?)(\s*["'].*?>)/\3@@@/g;
#
$a =~ s/(.*)@@@(.*)$/\1/;
$a =~ s/@@@/\n/g;
$a = $a . "\n";

my @b = split('\n', $a);

foreach my $str (@b) {
    $str =~ /((.*?([^:\/#?]+:)?)\/\/)?(.*?@.*?)?([^:\/#?]+)(:\d+)?.*?/i;
    if (length($3) == 0 && length($6) == 0) {
        $str = ".";
    } else {
        $str = $5;
    }
}

my @c = sort @b;
$lst = " ";
@array = ["#", "/", "//", ".", ".."];

sub prefix {
	my $s = shift;
    if (!($s =~ /^(\w+.)+$/)) {
        return true;
    }
    if (length($s) == 1) {
        return ($s ~~ @array);
    } else {
        return (((substr($s, 0, 1)) ~~ @array) || ((substr($s, 0, 2)) ~~ @array));
    }
}

foreach my $str (@c) {
    if (($str ne $lst) && !prefix($str)) {
        print $str;
        print "\n";
        $lst = $str;
    } 
}


