bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fprintf,fclose   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll   ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    nume_fisier db "output.txt",0
    acces_mode db "w",0
    dif db 0
    description_fis dd -1
    
    text db "Tata are 5/*-2 feciori",0
    l equ $-text-1
    nw_text times l+1 db 0

; our code starts here
segment code use32 class=code
    start:
        ;pentru modificarea textului vom folosi operatii pe siruri
        cld ;parcurgem in de la stanga la dreapta
        mov ESI,text ;in SOURCE INDEX punem adresa textului initial
        mov EDI,nw_text ; in DESTINATION INDEX punem adresa textului de construit
        mov ECX,l ;pune in ECX lungimea sirului 
        mov byte[dif],'A'-'a';
        st_loop:
            lodsb ;luam in AL caracterul din text
            ;verificam if('a'<=c && c<='z')
            cmp AL,'z'
            ja et1
            cmp AL,'a'
            jb et2
                add AL,[dif] ;in caz afirmativ adaugam cont
            et1:
            et2:
            stosb ;pune in sirul destinatie pe AL
        loop st_loop
        
        mov AL,0 ; adaugam 0-ul final pentru a putea afisa in fisier
        stosb
        
        ;apelam fopen pentru a deschide fisierul
        ;EAX=fopen(nume_fisier,acces_mode)
        push dword acces_mode
        push dword nume_fisier
        call [fopen]
        add ESP,4*2
        
        mov [description_fis],EAX ;salvam valoare returnata in EAX in description_fis
        
        cmp EAX,0
        je final
        
            ;scriem testul in fisier
            ;fprintf(description_fis,nw_text)
            push dword nw_text
            push dword [description_fis]
            call [fprintf]
            add ESP,4*2
            
            ;inchidem fisierul 
            ;fclose(description_fis)
            push dword [description_fis]
            call [fclose]
            add ESP,4
        
        final:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
