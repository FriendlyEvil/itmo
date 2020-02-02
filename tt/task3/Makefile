CFLAGS = -O2

ALEXFILE = Lex.x
SHALEX = Lex.sh

HAPPYFILE = Syntax.y
SHHAPPY = Syntax.sh

HSFILE = Main.hs ImplExpression.hs

all: compile remove

compilelite:
	ghc ${CFLAGS} ${HSFILE}

compile: ${SHALEX} ${SHHAPPY}
	ghc ${CFLAGS} ${HSFILE}

run:
	./Main

${SHALEX}: ${ALEXFILE}
	alex ${ALEXFILE}

${SHHAPPY}: ${HAPPYFILE}
	happy ${HAPPYFILE}

move_to_zip:
	zip A.zip -r Makefile ${HSFILE} ${ALEXFILE} ${HAPPYFILE} ${SHALEX} ${SHHAPPY}

remove:
	rm *.hi *.o
