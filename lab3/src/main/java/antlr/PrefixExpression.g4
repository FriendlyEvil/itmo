grammar PrefixExpression;

expr returns[String val]: ex=s_expression[0] {$val = $ex.val;};

s_expression[int tab] returns[String val]: expression[tab] {$val = $expression.val;}
                                         | LEFT f=big_expression[tab] RIGHT {$val = $f.val;};

big_expression[int tab] returns[String val]: expression[tab] {$val = $expression.val;}
                                         | f=expression[tab] s=big_expression[tab] {$val = $f.val + $s.val;};



expression[int tab] returns[String val]: ex=if_rule[tab] {$val = $ex.val + "\n";}
                              | e=print[tab] {$val = $e.val + "\n";}
                              | exx=define[tab] {$val = $exx.val + "\n";}
                              ;

if_rule[int tab] returns[String val]: IF c=logic ex=s_expression[tab+1] el=else_rule[tab] {$val = String.format("%sif %s:\n%s%s", "    ".repeat($tab), $c.val, $ex.val, $el.val);};

else_rule[int tab] returns[String val]: ex=s_expression[tab+1] {$val = String.format("%selse:\n%s", "    ".repeat($tab), $ex.val);}
                        | {$val = "";};

print[int tab] returns[String val]: PRINT v=right_variable {$val = String.format("%sprint(%s)", "    ".repeat($tab), $v.val);};

define[int tab] returns[String val]: DEF var=VARIABLE v=right_variable {$val = String.format("%s%s = %s", "    ".repeat($tab), $var.text, $v.val);};

right_variable returns[String val]: v=arithmetic {$val = $v.val;}
                                  | v1=logic {$val = $v1.val;}
                                  | var=VARIABLE {$val = $var.text;};

arithmetic returns[String val]: op=arithmetic_operation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                              | num=NUMER {$val = $num.text;}
                              | var=VARIABLE {$val = $var.text;};

arithmetic_operation returns[String op]: PLUS {$op = "+";}
                           | MINUS {$op = "-";}
                           | MUL {$op = "*";}
                           | DIV {$op = "/";};

logic_operation returns[String op]: AND {$op = "and";}
                                  | OR {$op = "or";};

logic returns[String val]: op=compare_operation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                           | NOT value=logic {$val = String.format("not %s", $value.val);}
                           | TRUE {$val = "True";}
                           | FALSE {$val = "False";}
                           | op1=logic_operation left1=logic right1=logic {$val = String.format("(%s %s %s)", $left1.val, $op1.op, $right1.val);};

compare_operation returns[String op]: EQUALS {$op = "==";}
                          | NOT_EQUAL {$op = "!=";}
                          | LOWER {$op = "<";}
                          | LOWER_EQUAL {$op = "<=";}
                          | HIGHER {$op = ">";}
                          | HIGHER_EQUAL {$op = ">=";};


WHITESPACE: [ \t\r\n]+ -> skip;
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
NUMER: ('-'? [1-9] DIGIT*) | '0';

PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';

DEF: '=';

EQUALS: '==';
NOT_EQUAL: '!=';
LOWER: '<';
LOWER_EQUAL: '<=';
HIGHER: '>';
HIGHER_EQUAL: '>=';

LEFT: '{';
RIGHT: '}';

AND: '&&';
OR: '||';
NOT: '!';
TRUE: 'true';
FALSE: 'false';

IF: 'if';

PRINT: 'print';

VARIABLE: LETTER (LETTER | DIGIT | ['])*;