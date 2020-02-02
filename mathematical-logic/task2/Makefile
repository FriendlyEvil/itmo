CFLAGS = -O2

ALEXFILE = Lex.x
SHALEX = Lex.sh

HAPPYFILE = Syntax.y
SHHAPPY = Syntax.sh

HSFILE = Main.hs Axioms.hs Helpers.hs HelpStructure.hs

all: compilelite remove

compile: ${SHALEX} ${SHHAPPY}
	ghc ${CFLAGS} ${HSFILE}

compilelite:
	ghc ${CFLAGS} ${HSFILE}

run:
	./Main

${SHALEX}: ${ALEXFILE}
	alex ${ALEXFILE}

${SHHAPPY}: ${HAPPYFILE}
	happy ${HAPPYFILE}

move_to_zip:
	zip C.zip -r Makefile ${HSFILE} ${ALEXFILE} ${HAPPYFILE} ${SHALEX} ${SHHAPPY}

remove:
	rm *.hi *.o
