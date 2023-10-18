bits 32 

global start        

extern exit,fscanf,printf,fopen,fclose
import exit msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

extern construct

segment data use32 class=data public
    file_name db "numere.txt",0
    r db "r",0
    description_fis resd 1
    format db "%d ",0
    afisare_poz db "Numere pozitive:",0
    afisare_neg db "Numere negative:",0
    nr dw 0,0
    s resw 15
    n resw 15
    p resw 15
    l db 0
    ln db 0
    lp db 0

; our code starts here
segment code use32 class=code public
    start:
        ; EAX=fopen("numere.txt","r") deschidem fisierul
        push dword r
        push dword file_name
        call [fopen]
        add ESP,4*2
        
        cmp EAX,0
        je sf
        
        mov [description_fis],EAX
        mov EDI,s
        
        strt:
        ;fscanf([description_fis],format,nr)
        push dword nr
        push dword format
        push dword [description_fis]
        call [fscanf]
        add ESP,4*3
        
        ;conditia de oprire, cand functia fscanf pune in EAX -1
        cmp EAX,0FFFFFFFFh
        je sf
        ;stocam elementul in sir
        mov AX,[nr]
        stosw
        inc byte[l]
        
        jmp strt
        sf:
    
        ;fclose([description_fis])-inchidem fisierul
        push dword[description_fis] 
        call [fclose]
        add ESP,4
        
        ;construct(s,l,n,p) - se apeleaza functia construct cu parametrii s, lungimea lui si cele doua siruri de construit
        push dword p
        push dword n
        push dword [l]
        push dword s
        call construct
        add ESP,4*4
        
        mov byte[ln],BL
        mov byte[lp],DL
        
        ;printf("Numere negative:")
        push dword afisare_neg
        call [printf]
        add ESP,4
        
        mov ESI,n
        mov ECX,0
        mov CL,[ln]
        af:
            lodsw
            cwde
            push ECX
            ;printf("%d",EAX)
            push dword EAX
            push dword format
            call [printf]
            add ESP,4*2
            pop ECX
        loop af
        
        ;printf("Numere pozitive:")
        push dword afisare_poz
        call [printf]
        add ESP,4
        
        mov ESI,p
        mov ECX,0
        mov CL,[lp]
        af1:
            lodsw
            cwde
            push ECX
            ;printf("%d",EAX)
            push dword EAX
            push dword format
            call [printf]
            add ESP,4*2
            pop ECX
        loop af1
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
