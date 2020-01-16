grammar PrefixExpression;

expr returns[String val]: ex=expression[0] {$val = $ex.val;};

expression[int tab] returns[String val]: ex=one_expression[tab] {$val = $ex.val;}
                                         | LEFT big=big_expression[tab] RIGHT {$val = $big.val;};

big_expression[int tab] returns[String val]: ex=one_expression[tab] {$val = $ex.val;}
                                         | ex=one_expression[tab] big=big_expression[tab] {$val = $ex.val + $big.val;};



one_expression[int tab] returns[String val]: ex=if_rule[tab] {$val = $ex.val + "\n";}
                              | pr=print[tab] {$val = $pr.val + "\n";}
                              | def=define[tab] {$val = $def.val + "\n";}
                              | wh=whil[tab] {$val = $wh.val;}
                              | d=do_whil[tab] {$val = $d.val + "\n";}
                              ;

do_whil[int tab] returns[String val]: DO l=logic ex=expression[tab+1] {$val = String.format("%sdo\n%swhile %s:", "    ".repeat($tab), $ex.val, $l.val);};

whil[int tab] returns[String val]: WHILE l=logic ex=expression[tab+1] {$val = String.format("%swhile %s:\n%s", "    ".repeat($tab), $l.val, $ex.val);};

if_rule[int tab] returns[String val]: IF l=logic ex=expression[tab+1] el=else_rule[tab] {$val = String.format("%sif %s:\n%s%s", "    ".repeat($tab), $l.val, $ex.val, $el.val);};

else_rule[int tab] returns[String val]: ex=expression[tab+1] {$val = String.format("%selse:\n%s", "    ".repeat($tab), $ex.val);}
                                      | {$val = "";};

print[int tab] returns[String val]: PRINT v=right_variable {$val = String.format("%sprint(%s)", "    ".repeat($tab), $v.val);};

define[int tab] returns[String val]: DEF var=VARIABLE v=right_variable {$val = String.format("%s%s = %s", "    ".repeat($tab), $var.text, $v.val);};

right_variable returns[String val]: ar=arithmetic {$val = $ar.val;}
                                  | l=logic {$val = $l.val;}
                                  | var=VARIABLE {$val = $var.text;};


logic returns[String val]: op=compare_operation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                           | NOT value=logic {$val = String.format("not %s", $value.val);}
                           | TRUE {$val = "True";}
                           | FALSE {$val = "False";}
                           | op_l=logic_operation left_l=logic right_l=logic {$val = String.format("(%s %s %s)", $left_l.val, $op_l.op, $right_l.val);};

compare_operation returns[String op]: EQUALS {$op = "==";}
                          | NOT_EQUAL {$op = "!=";}
                          | LOWER {$op = "<";}
                          | LOWER_EQUAL {$op = "<=";}
                          | HIGHER {$op = ">";}
                          | HIGHER_EQUAL {$op = ">=";};

logic_operation returns[String op]: AND {$op = "and";}
                                  | OR {$op = "or";};

arithmetic returns[String val]: op=arithmetic_operation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                              | num=NUMER {$val = $num.text;}
                              | var=VARIABLE {$val = $var.text;};

arithmetic_operation returns[String op]: PLUS {$op = "+";}
                           | MINUS {$op = "-";}
                           | MUL {$op = "*";}
                           | DIV {$op = "/";};


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
WHILE: 'while';
DO: 'do';

PRINT: 'print';

VARIABLE: LETTER (LETTER | DIGIT | ['])*;