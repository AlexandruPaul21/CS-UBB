bison -d analizator.y
flex analizator.l
gcc analizator.tab.c lex.yy.c

