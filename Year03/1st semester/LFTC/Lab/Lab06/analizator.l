%{
#include <stdio.h>
#include <string.h>
#include "analizator.tab.h"

#define YY_DECL int yylex()

int currentLine = 1;

%}

%option noyywrap

DIGIT_0 [1-9]
DIGIT   [0-9]
ID      [a-zA-Z_][a-zA-Z0-9_]*

%%

"#include<iostream>" { return INCLUDE; }
"using namespace std;" {  return NAMESPACE; }
cin { return ISTREAM; }
cout { return OSTREAM; }
int { return DATA_TYPE; }
"+" { yylval.value = strdup(yytext); return PLUS; }
"-" { yylval.value = strdup(yytext); return MINUS; }
"*" { yylval.value = strdup(yytext); return OR; }
"/" { yylval.value = strdup(yytext); return DIVIDE; }
"=" { return ASSIGN; }
"," { return COMMA; }
";" { return SEMICOLON; }
"{" { return LAQ; }
"}" { return RAQ; }
">>" { return SIN; }
"<<" { return SOUT; }
"(" { return LPR; }
")" { return RPR; }
"main()" { return MAIN; }
"return" { return RETURN; }

{ID} { yylval.value = strdup(yytext); return ID; }

0|{DIGIT_0}{DIGIT}* { yylval.value = strdup(yytext); return CONST; }

[\n] { currentLine++; }

[ \t]+ ;

%%
