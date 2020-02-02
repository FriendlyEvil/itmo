grammar Grammer;

@header {
import java.util.Arrays;
import utils.Pair;
}

grammer returns[ParserInput val]: h=header? s=start e=expressions {$val = new ParserInput($h.t, $s.t, $e.t);};

expressions returns[List<Term> t]: f=expression s=expressions {$t = $s.t; $t.add($f.t);}
                                   | ex=expression {$t = new ArrayList<>(Arrays.asList($ex.t));};

expression returns[Term t]: ter=non_terminal {$t = $ter.t;}
                      | non=terminal {$t = $non.t;};

terminal returns[Terminal t]: name=TERMINAL EQALS reg=REG_EXPR END {$t = new Terminal($name.text, $reg.text);};

non_terminal returns[NonTerminal t]: name=NON_TERMINAL typ=return_type EQALS f_list=token_list code=JAVA_CODE s_list=next_token_list END {$t = new NonTerminal($name.text, $typ.t, $f_list.t, $s_list.t, $code.text);};

return_type returns[ReturnType t]: RETURNS '[' type=TYPE ']' {$t = new ReturnType($type.text);};

next_token_list returns[List<Pair<List<String>, String>> t]: OR tok=token_list code=JAVA_CODE next=next_token_list {$t = $next.t; $t.add(new Pair<>($tok.t, $code.text));}
                            | {$t = new ArrayList<>();};

token_list returns[List<String> t]: f=token s=token_list {$t = $s.t; $t.add($f.t);}
                | tok=token {$t = new ArrayList<>(Arrays.asList($tok.t));};

token returns[String t]: ter=TERMINAL {$t = $ter.text;}
                | non=NON_TERMINAL {$t = $non.text;};


header returns[String t]: HEADER code=JAVA_CODE {$t = $code.text;}
                             | {$t = "";};

start returns[NonTerminal t]: f=non_terminal  {$t = $f.t;};




WHITESPACE: [ \t\r\n]+ -> skip;

HEADER: '@header';
RETURNS: 'returns';
OR: '|';
EQALS: ':';
END: ';';
TERMINAL: [A-Z0-9_]+;
NON_TERMINAL: [a-z0-9_]+;
JAVA_CODE: '{' (~[{}:]+ JAVA_CODE?)* '}';
TYPE: [A-Z][a-zA-Z0-9<>]*;
REG_EXPR: '"' (~[\\"] | '\\' [\\"])* '"';
