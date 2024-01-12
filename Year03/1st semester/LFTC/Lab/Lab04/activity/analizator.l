%{

#include "analizator.tab.h"

%}

%option noyywrap

DIGIT [0-9]
DIGIT_0 [1-9]

%%

0|{DIGIT_0}{DIGIT}* { return NUMBER; }
"(" { return LPR; }
")" { return RPR; }
"+" { return PLUS; }

[\n] ;

[ \t]+ ;

%%

