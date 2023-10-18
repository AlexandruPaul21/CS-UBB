bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    A db 2,1,3,-3 ;definim sirul a
    l equ $-A     ;lungimea sirului a
    B db 4,5,-5,7 ;definim sirul b 
    r equ $-B     ;lungimea sirului b
    R times 10 db 0 ;rezervam spatiu pentru sirul destinatie

; our code starts here
segment code use32 class=code
    start:
        mov ECX,l ;pregatim registrul ECX pentru a parcurge sirul 
        mov ESI,0      ;Initializam registrul indice cu 0
        mov EDX,0
        jecxz sf       ;tratam cazul in care ECX  e 0
        start_loop:
            mov AL,[A+ESI] ;punem in AL, elementul curent
            
            test AL,00000001b    ;daca elemntul este impar ZF=0
            jz par         ;daca elemntul este par, adica ZF=1 atunci nu executam instructiunea 
            cmp AL,0
            jnge negat      ;daca elemntul este negativ, nu executam 
            
            mov [R+EDX],AL  ; daca elemntul indeplineste conditiile, il punem in sir
            inc EDX         ; incrementam lungimea sirului de facut
            
            negat:
            par:
            
            inc ESI        ;incrementam indicele la care ne aflam
        loop start_loop
        sf:
         
        mov ECX,r  ;pregatim registrul ECX pentru a parcurge sirul
        mov ESI,0  ;Initializam registrul indice cu 0
        jecxz sf2       ;tratam cazul in care ECX  e 0
        start_loop2:
            mov AL,[B+ESI]  ;punem in AL, elementul curent
            
            test AL,01h   ;daca elemntul este impar ZF=0
            jz par2       ;daca elemntul este par, adica ZF=1 atunci nu executam instructiunea
            cmp AL,0      ;seteaza OF si SF
            jnge negat2   ;daca elemntul este negativ, nu executam
            
            mov [R+EDX],AL  ; daca elemntul indeplineste conditiile, il punem in sir
            inc EDX         ; incrementam lungimea sirului de facut
            
            negat2:
            par2:
            
            inc ESI        ;incrementam indicele la care ne aflam
            
        loop start_loop2
        sf2:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
