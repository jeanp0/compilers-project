package com.g6.project.jflex;
import static com.g6.project.jflex.Tokens.*;

%%

%class Lexer
%type Tokens
L = [a-zA-ZnÑ]+
N = [0-9]+
AN = [a-zA-Z0-9]*
space = [ ,\t,\r,\n]+
%{
    public String lexeme;
%}

%%

{L} {lexeme=yytext(); return LETTER;}
{N} {lexeme=yytext(); return NUMBER;}
{AN} {lexeme=yytext(); return ALPHANUM;}
{space} {}
. {return ERROR;}
